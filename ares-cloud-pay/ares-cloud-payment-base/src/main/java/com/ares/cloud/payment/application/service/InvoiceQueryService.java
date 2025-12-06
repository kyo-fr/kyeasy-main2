package com.ares.cloud.payment.application.service;

import com.ares.cloud.base.dto.SysPaymentChannelDto;
import com.ares.cloud.base.service.SysPaymentChannelService;
import com.ares.cloud.payment.application.dto.*;
import com.ares.cloud.payment.application.query.InvoiceQuery;
import com.ares.cloud.payment.domain.enums.InvoiceError;
import com.ares.cloud.payment.infrastructure.converter.InvoiceConverter;
import com.ares.cloud.payment.infrastructure.converter.InvoiceItemConverter;
import com.ares.cloud.payment.infrastructure.converter.PartyConverter;
import com.ares.cloud.payment.infrastructure.converter.PayItemConverter;
import com.ares.cloud.payment.infrastructure.persistence.entity.InvoiceEntity;
import com.ares.cloud.payment.infrastructure.persistence.entity.InvoiceItemEntity;
import com.ares.cloud.payment.infrastructure.persistence.entity.PartyEntity;
import com.ares.cloud.payment.infrastructure.persistence.entity.PayItemEntity;
import com.ares.cloud.payment.infrastructure.persistence.mapper.InvoiceItemMapper;
import com.ares.cloud.payment.infrastructure.persistence.mapper.InvoiceMapper;
import com.ares.cloud.payment.infrastructure.persistence.mapper.PartyMapper;
import com.ares.cloud.payment.infrastructure.persistence.mapper.PayItemMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 发票查询服务类
 * 实现CQRS模式中的查询部分
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceQueryService {

    private final InvoiceMapper invoiceMapper;
    private final InvoiceItemMapper invoiceItemMapper;
    private final PartyMapper partyMapper;
    private final PayItemMapper payItemMapper;
    private final InvoiceConverter invoiceConverter;
    private final PartyConverter partyConverter;
    private final PayItemConverter payItemConverter;
    private final InvoiceItemConverter invoiceItemConverter;
    private final SysPaymentChannelService sysPaymentChannelService;

    /**
     * 获取发票详情
     *
     * @param invoiceId 发票ID
     * @return 发票详情DTO，如果发票不存在则返回null
     * @throws RequestBadException 当发票ID为空时抛出
     */
    public InvoiceDTO getInvoiceDetails(String invoiceId) {
        if (!StringUtils.hasText(invoiceId)) {
            throw new RequestBadException(InvoiceError.INVOICE_NOT_FOUND);
        }

        try {
            // 查询发票基本信息
            InvoiceEntity invoiceEntity = invoiceMapper.selectById(invoiceId);
            if (invoiceEntity == null) {
                return null;
            }

            // 并行查询相关数据
            CompletableFuture<List<InvoiceItemEntity>> itemsFuture = CompletableFuture
                .supplyAsync(() -> {
                    LambdaQueryWrapper<InvoiceItemEntity> itemWrapper = Wrappers.lambdaQuery();
                    itemWrapper.eq(InvoiceItemEntity::getInvoiceId, invoiceId);
                    return invoiceItemMapper.selectList(itemWrapper);
                });

            // 查询所有关联的交易方
            CompletableFuture<List<PartyEntity>> partiesFuture = CompletableFuture
                .supplyAsync(() -> {
                    LambdaQueryWrapper<PartyEntity> partyWrapper = Wrappers.lambdaQuery();
                    partyWrapper.eq(PartyEntity::getInvoiceId, invoiceId);
                    return partyMapper.selectList(partyWrapper);
                });

            CompletableFuture<List<PayItemEntity>> payItemsFuture = CompletableFuture
                .supplyAsync(() -> {
                    LambdaQueryWrapper<PayItemEntity> payItemWrapper = Wrappers.lambdaQuery();
                    payItemWrapper.eq(PayItemEntity::getInvoiceId, invoiceId);
                    return payItemMapper.selectList(payItemWrapper);
                });

            // 等待所有查询完成
            CompletableFuture.allOf(itemsFuture, partiesFuture, payItemsFuture).join();

            // 获取交易方列表
            List<PartyEntity> parties = partiesFuture.get();
            
            // 根据partyId匹配付款方和收款方
            PartyEntity payer = parties.stream()
                .filter(party -> party.getPartyId().equals(invoiceEntity.getPayerId()))
                .findFirst()
                .orElse(null);
                
            PartyEntity payee = parties.stream()
                .filter(party -> party.getPartyId().equals(invoiceEntity.getPayeeId()))
                .findFirst()
                .orElse(null);

            // 转换为DTO
            return assembleInvoiceDetailDTO(
                invoiceEntity,
                itemsFuture.get(),
                payer,
                payee,
                payItemsFuture.get()
            );
        } catch (Exception e) {
            log.error("获取发票详情失败, invoiceId: {}", invoiceId, e);
            throw new RequestBadException(InvoiceError.INVOICE_CHECK_FAILED);
        }
    }

    /**
     * 组装发票详情DTO
     * 使用各个转换器完成子对象的转换，然后组装成完整的发票DTO
     *
     * @param invoice 发票实体
     * @param items 发票明细项列表
     * @param payer 付款方实体
     * @param payee 收款方实体
     * @param payItems 支付项列表
     * @return 发票详情DTO
     */
    private InvoiceDTO assembleInvoiceDetailDTO(
            InvoiceEntity invoice,
            List<InvoiceItemEntity> items,
            PartyEntity payer,
            PartyEntity payee,
            List<PayItemEntity> payItems
    ) {
        try {
            // 转换发票基本信息
            InvoiceDTO invoiceDTO = invoiceConverter.toBaseDTO(invoice);
            
            // 转换发票明细项
            List<InvoiceItemDTO> itemDTOs = items.stream()
                .map(item-> invoiceItemConverter.toDTO(item, invoice.getCurrency(), invoice.getScale()))
                .collect(Collectors.toList());
            invoiceDTO.setItems(itemDTOs);
            
            // 转换交易方信息
            PartyDTO payerDTO = partyConverter.toDTO(payer);
            PartyDTO payeeDTO = partyConverter.toDTO(payee);
            invoiceDTO.setPayer(payerDTO);
            invoiceDTO.setPayee(payeeDTO);
            
            // 转换支付项信息
            List<PayItemDTO> payItemDTOs = payItems.stream()
                .map(item -> payItemConverter.toDTO(item, invoice.getCurrency(), invoice.getScale(),loadPaymentChannelMap()))
                .toList();
            //  线上线下分开
            invoiceDTO.setOnlinePayItems(payItemDTOs.stream()
                .filter(item -> item.getPayType() == 1)
                .collect(Collectors.toList()));
            invoiceDTO.setOfflinePayItems(payItemDTOs.stream()
                .filter(item -> item.getPayType() == 2)
                .collect(Collectors.toList()));
            
            return invoiceDTO;
        } catch (Exception e) {
            log.error("组装发票详情DTO失败, invoiceId: {}", invoice.getId(), e);
            throw new RequestBadException(InvoiceError.INVOICE_CHECK_FAILED);
        }
    }

    /**
     * 商户查询发票列表
     */
    public PageResult<MerchantInvoiceDTO> getMerchantInvoices(InvoiceQuery query) {
        validateQuery(query);
        String merchantId = ApplicationContext.getTenantId();
        if (!StringUtils.hasText(merchantId)) {
            merchantId = query.getMerchantId();
        }
        if (!StringUtils.hasText(merchantId)) {
            throw new RequestBadException(InvoiceError.MERCHANT_ID_REQUIRED);
        }

        try {
            // 构建查询条件
            LambdaQueryWrapper<InvoiceEntity> wrapper = buildBaseWrapper(query);
            
            // 根据交易类型添加条件
            if (query.getTransactionType() != null) {
                switch (query.getTransactionType()) {
                    case INCOME:
                        wrapper.eq(InvoiceEntity::getPayeeId, merchantId);
                        break;
                    case EXPENSE:
                        wrapper.eq(InvoiceEntity::getPayerId, merchantId);
                        break;
                    case ALL:
                        String finalMerchantId = merchantId;
                        wrapper.and(w -> w
                            .eq(InvoiceEntity::getPayerId, finalMerchantId)
                            .or()
                            .eq(InvoiceEntity::getPayeeId, finalMerchantId)
                        );
                        break;
                }
            }

            // 执行分页查询
            Page<InvoiceEntity> page = new Page<>(query.getPage(), query.getLimit());
            Page<InvoiceEntity> invoicePage = invoiceMapper.selectPage(page, wrapper);

            if (invoicePage.getRecords().isEmpty()) {
                return new PageResult<>(Collections.emptyList(), 0L);
            }

            // 获取所有发票ID
            List<String> invoiceIds = invoicePage.getRecords().stream()
                .map(InvoiceEntity::getId)
                .collect(Collectors.toList());

            // 查询所有关联的交易方
            LambdaQueryWrapper<PartyEntity> partyWrapper = Wrappers.lambdaQuery();
            partyWrapper.in(PartyEntity::getInvoiceId, invoiceIds);
            List<PartyEntity> parties = partyMapper.selectList(partyWrapper);

            // 转换为DTO列表
            String finalMerchantId1 = merchantId;
            List<MerchantInvoiceDTO> dtos = invoicePage.getRecords().stream()
                .map(entity -> {
                    // 根据partyId匹配交易对手方
                    PartyEntity counterparty = parties.stream()
                        .filter(party -> {
                            if (finalMerchantId1.equals(entity.getPayerId())) {
                                return party.getPartyId().equals(entity.getPayeeId());
                            } else {
                                return party.getPartyId().equals(entity.getPayerId());
                            }
                        })
                        .findFirst()
                        .orElse(null);
                    
                    return invoiceConverter.toMerchantDTO(entity, counterparty);
                })
                .collect(Collectors.toList());

            return new PageResult<>(dtos, invoicePage.getTotal());
        } catch (Exception e) {
            log.error("查询商户发票列表失败, merchantId: {}", merchantId, e);
            throw new RequestBadException(InvoiceError.INVOICE_CHECK_FAILED);
        }
    }

    /**
     * 用户查询发票列表
     */
    public PageResult<UserInvoiceDTO> getUserInvoices(InvoiceQuery query) {
        validateQuery(query);
        String userId = ApplicationContext.getUserId();
        if (!StringUtils.hasText(userId)) {
            userId = query.getUserId();
        }
        if (!StringUtils.hasText(userId)) {
            throw new RequestBadException(InvoiceError.USER_ID_REQUIRED);
        }

        try {
            // 构建查询条件
            LambdaQueryWrapper<InvoiceEntity> wrapper = buildBaseWrapper(query);
            
            // 根据交易类型添加条件
            if (query.getTransactionType() != null) {
                switch (query.getTransactionType()) {
                    case INCOME:
                        wrapper.eq(InvoiceEntity::getPayeeId, userId);
                        break;
                    case EXPENSE:
                        wrapper.eq(InvoiceEntity::getPayerId, userId);
                        break;
                    case ALL:
                        String finalUserId = userId;
                        wrapper.and(w -> w
                            .eq(InvoiceEntity::getPayerId, finalUserId)
                            .or()
                            .eq(InvoiceEntity::getPayeeId, finalUserId)
                        );
                        break;
                }
            }

            // 执行分页查询
            Page<InvoiceEntity> page = new Page<>(query.getPage(), query.getLimit());
            Page<InvoiceEntity> invoicePage = invoiceMapper.selectPage(page, wrapper);

            if (invoicePage.getRecords().isEmpty()) {
                return new PageResult<>(Collections.emptyList(), 0L);
            }

            // 获取所有发票ID
            List<String> invoiceIds = invoicePage.getRecords().stream()
                .map(InvoiceEntity::getId)
                .collect(Collectors.toList());

            // 查询所有关联的交易方
            LambdaQueryWrapper<PartyEntity> partyWrapper = Wrappers.lambdaQuery();
            partyWrapper.in(PartyEntity::getInvoiceId, invoiceIds);
            List<PartyEntity> parties = partyMapper.selectList(partyWrapper);

            // 转换为DTO列表
            String finalUserId1 = userId;
            List<UserInvoiceDTO> dtos = invoicePage.getRecords().stream()
                .map(entity -> {
                    // 根据partyId匹配交易对手方
                    PartyEntity counterparty = parties.stream()
                        .filter(party -> {
                            if (finalUserId1.equals(entity.getPayerId())) {
                                return party.getPartyId().equals(entity.getPayeeId());
                            } else {
                                return party.getPartyId().equals(entity.getPayerId());
                            }
                        })
                        .findFirst()
                        .orElse(null);
                    
                    return invoiceConverter.toUserDTO(entity, counterparty);
                })
                .collect(Collectors.toList());

            return new PageResult<>(dtos, invoicePage.getTotal());
        } catch (Exception e) {
            log.error("查询用户发票列表失败, userId: {}", userId, e);
            throw new RequestBadException(InvoiceError.INVOICE_CHECK_FAILED);
        }
    }

    /**
     * 根据订单ID查询发票列表
     *
     * @param orderId 订单ID
     * @return 发票DTO列表，如果没有相关发票则返回空列表
     * @throws RequestBadException 当订单ID为空时抛出
     */
    public List<InvoiceDTO> getInvoicesByOrderId(String orderId) {
        if (!StringUtils.hasText(orderId)) {
            throw new RequestBadException(InvoiceError.ORDER_ID_REQUIRED);
        }

        try {
            // 构建查询条件
            LambdaQueryWrapper<InvoiceEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(InvoiceEntity::getOrderId, orderId)
                  .orderByDesc(InvoiceEntity::getCreateTime);

        // 查询发票列表
            List<InvoiceEntity> invoiceEntities = invoiceMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(invoiceEntities)) {
                return Collections.emptyList();
            }

            // 并行查询相关数据
            Map<String, List<InvoiceItemEntity>> itemsMap = queryInvoiceItems(invoiceEntities);
            Map<String, PartyEntity> partyMap = queryParties(invoiceEntities);
            Map<String, List<PayItemEntity>> payItemsMap = queryPayItems(invoiceEntities);

            // 转换为DTO列表
            return invoiceEntities.stream()
                .map(entity -> assembleInvoiceDTO(entity, itemsMap, partyMap, payItemsMap))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("根据订单ID查询发票列表失败, orderId: {}", orderId, e);
            throw new RequestBadException(InvoiceError.INVOICE_CHECK_FAILED);
        }
    }

    /**
     * 组装发票DTO
     *
     * @param entity 发票实体
     * @param itemsMap 发票明细映射
     * @param partyMap 交易方映射
     * @param payItemsMap 支付项映射
     * @return 发票DTO
     */
    private InvoiceDTO assembleInvoiceDTO(
            InvoiceEntity entity,
            Map<String, List<InvoiceItemEntity>> itemsMap,
            Map<String, PartyEntity> partyMap,
            Map<String, List<PayItemEntity>> payItemsMap
    ) {
        try {
            // 转换发票基本信息
            InvoiceDTO invoiceDTO = invoiceConverter.toBaseDTO(entity);
            
            // 转换发票明细项
            List<InvoiceItemEntity> items = itemsMap.getOrDefault(entity.getId(), Collections.emptyList());
            List<InvoiceItemDTO> itemDTOs = items.stream()
                .map(item -> invoiceItemConverter.toDTO(item, entity.getCurrency(), entity.getScale()))
                .collect(Collectors.toList());
            invoiceDTO.setItems(itemDTOs);
            
            // 转换交易方信息
            PartyEntity payer = partyMap.get(entity.getPayerId());
            PartyEntity payee = partyMap.get(entity.getPayeeId());
            if (payer != null && payee != null) {
                PartyDTO payerDTO = partyConverter.toDTO(payer);
                PartyDTO payeeDTO = partyConverter.toDTO(payee);
                invoiceDTO.setPayer(payerDTO);
                invoiceDTO.setPayee(payeeDTO);
            }
            
            // 转换支付项信息
            List<PayItemEntity> payItems = payItemsMap.getOrDefault(entity.getId(), Collections.emptyList());
            List<PayItemDTO> payItemDTOs = payItems.stream()
                .map(item -> payItemConverter.toDTO(item, entity.getCurrency(), entity.getScale(),loadPaymentChannelMap()))
                .toList();
                
            // 区分线上线下支付项
            invoiceDTO.setOnlinePayItems(payItemDTOs.stream()
                .filter(item -> item.getPayType() == 1)
                .collect(Collectors.toList()));
            invoiceDTO.setOfflinePayItems(payItemDTOs.stream()
                .filter(item -> item.getPayType() == 2)
                .collect(Collectors.toList()));
            
            return invoiceDTO;
        } catch (Exception e) {
            log.error("组装发票DTO失败, invoiceId: {}", entity.getId(), e);
            return null;
        }
    }

    // 私有辅助方法

    /**
     * 验证查询参数的有效性
     *
     * @param query 查询参数对象
     * @throws RequestBadException 当参数无效时抛出
     */
    private void validateQuery(InvoiceQuery query) {
        if (query == null) {
            throw new RequestBadException(InvoiceError.QUERY_PARAMS_REQUIRED);
        }
        if (query.getPage() < 1) {
            throw new RequestBadException(InvoiceError.PAGE_NUMBER_INVALID);
        }
        if (query.getLimit() < 1) {
            throw new RequestBadException(InvoiceError.PAGE_SIZE_INVALID);
        }
    }

    /**
     * 查找交易对手方信息
     *
     * @param entity 发票实体
     * @param partyMap 交易方信息映射
     * @param currentUserId 当前用户ID
     * @return 交易对手方实体
     */
    private PartyEntity findCounterparty(InvoiceEntity entity, Map<String, PartyEntity> partyMap, String currentUserId) {
        // 如果当前用户是付款方，则交易对手是收款方
        if (currentUserId.equals(entity.getPayerId())) {
            return partyMap.get(entity.getPayeeId());
        }
        // 如果当前用户是收款方，则交易对手是付款方
        else if (currentUserId.equals(entity.getPayeeId())) {
            return partyMap.get(entity.getPayerId());
        }
        return null;
    }

    /**
     * 将发票实体列表转换为商户发票DTO列表
     *
     * @param entities 发票实体列表
     * @return 商户发票DTO列表
     */
    private List<MerchantInvoiceDTO> convertToMerchantDTOs(List<InvoiceEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        String currentUserId = ApplicationContext.getTenantId();
        Map<String, PartyEntity> partyMap = queryParties(entities);
        
        return entities.stream()
            .map(entity -> {
                PartyEntity counterparty = findCounterparty(entity, partyMap, currentUserId);
                return invoiceConverter.toMerchantDTO(entity, counterparty);
            })
            .collect(Collectors.toList());
    }

    /**
     * 将发票实体列表转换为用户发票DTO列表
     *
     * @param entities 发票实体列表
     * @return 用户发票DTO列表
     */
    private List<UserInvoiceDTO> convertToUserDTOs(List<InvoiceEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        String currentUserId = ApplicationContext.getUserId();
        Map<String, PartyEntity> partyMap = queryParties(entities);
        
        return entities.stream()
            .map(entity -> {
                PartyEntity counterparty = findCounterparty(entity, partyMap, currentUserId);
                return invoiceConverter.toUserDTO(entity, counterparty);
            })
            .collect(Collectors.toList());
    }

    /**
     * 批量查询发票明细项
     *
     * @param invoices 发票实体列表
     * @return 发票ID到明细项列表的映射
     */
    private Map<String, List<InvoiceItemEntity>> queryInvoiceItems(List<InvoiceEntity> invoices) {
        List<String> invoiceIds = invoices.stream()
            .map(InvoiceEntity::getId)
            .collect(Collectors.toList());

        LambdaQueryWrapper<InvoiceItemEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(InvoiceItemEntity::getInvoiceId, invoiceIds);

        return invoiceItemMapper.selectList(wrapper).stream()
            .collect(Collectors.groupingBy(InvoiceItemEntity::getInvoiceId));
    }

    /**
     * 批量查询交易方信息
     *
     * @param invoices 发票实体列表
     * @return 交易方ID到交易方实体的映射
     */
    private Map<String, PartyEntity> queryParties(List<InvoiceEntity> invoices) {
        Set<String> partyIds = invoices.stream()
            .flatMap(entity -> Stream.of(entity.getPayerId(), entity.getPayeeId()))
            .collect(Collectors.toSet());

        // 发票ID
        Set<String> invoiceIds = invoices.stream()
                .map(InvoiceEntity::getId)                .collect(Collectors.toSet());
        LambdaQueryWrapper<PartyEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(PartyEntity::getPartyId, partyIds);
        wrapper.in(PartyEntity::getInvoiceId, invoiceIds);

        List<PartyEntity> partyEntities = partyMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(partyEntities)) {
            return Collections.emptyMap();
        }

        return partyEntities.stream()
            .collect(Collectors.toMap(PartyEntity::getPartyId, Function.identity()));
    }

    /**
     * 批量查询支付项信息
     *
     * @param invoices 发票实体列表
     * @return 发票ID到支付项列表的映射
     */
    private Map<String, List<PayItemEntity>> queryPayItems(List<InvoiceEntity> invoices) {
        List<String> invoiceIds = invoices.stream()
            .map(InvoiceEntity::getId)
            .collect(Collectors.toList());

        LambdaQueryWrapper<PayItemEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(PayItemEntity::getInvoiceId, invoiceIds);

        return payItemMapper.selectList(wrapper).stream()
            .collect(Collectors.groupingBy(PayItemEntity::getInvoiceId));
    }

    /**
     * 构建基础查询条件
     *
     * @param query 查询参数
     * @return 查询条件包装器
     */
    private LambdaQueryWrapper<InvoiceEntity> buildBaseWrapper(InvoiceQuery query) {
        LambdaQueryWrapper<InvoiceEntity> wrapper = Wrappers.lambdaQuery();
        
        // 添加时间范围条件
        if (query.getStartTime() != null && query.getEndTime() != null) {
            wrapper.between(InvoiceEntity::getCreateTime, query.getStartTime(), query.getEndTime());
        }
        if (query.getStartTime() != null) {
            wrapper.ge(InvoiceEntity::getCreateTime, query.getStartTime());
        }
        if (query.getEndTime() != null) {
            wrapper.le(InvoiceEntity::getCreateTime, query.getEndTime());
        }
        
        // 添加状态条件
        if (StringUtils.hasText(query.getStatus())) {
            wrapper.eq(InvoiceEntity::getStatus, query.getStatus());
        }
        
        // 添加订单ID条件
        if (StringUtils.hasText(query.getOrderId())) {
            wrapper.eq(InvoiceEntity::getOrderId, query.getOrderId());
        }
        
        // 添加合同ID条件
        if (StringUtils.hasText(query.getContractId())) {
            wrapper.eq(InvoiceEntity::getContractId, query.getContractId());
        }
        
        // 默认按创建时间降序
        wrapper.orderByDesc(InvoiceEntity::getCreateTime);
        
        return wrapper;
    }

    /**
     * 获取支付渠道信息
     * @return 支付渠道信息
     */
    private Map<String,SysPaymentChannelDto> loadPaymentChannelMap() {
        List<SysPaymentChannelDto> dos = sysPaymentChannelService.loadAll();
        if (CollectionUtils.isEmpty(dos)) {
            return Collections.emptyMap();
        }
        return dos.stream().collect(Collectors.toMap(SysPaymentChannelDto::getId, Function.identity()));
    }
}