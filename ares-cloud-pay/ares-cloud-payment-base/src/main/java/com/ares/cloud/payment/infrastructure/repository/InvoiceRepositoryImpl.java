package com.ares.cloud.payment.infrastructure.repository;

import com.ares.cloud.payment.domain.model.Invoice;
import com.ares.cloud.payment.domain.model.InvoiceItem;
import com.ares.cloud.payment.domain.model.Party;
import com.ares.cloud.payment.domain.model.PayItem;
import com.ares.cloud.payment.domain.repository.InvoiceRepository;
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
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 发票仓储实现类
 */
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Resource
    private InvoiceMapper invoiceMapper;
    
    @Resource
    private InvoiceItemMapper invoiceItemMapper;
    
    @Resource
    private PartyMapper partyMapper;

    @Resource
    private PayItemMapper payItemMapper;
    
    @Resource
    private InvoiceConverter invoiceConverter;
    
    @Resource
    private InvoiceItemConverter invoiceItemConverter;
    
    @Resource
    private PartyConverter partyConverter;

    @Resource
    private PayItemConverter payItemConverter;

    @Override
    @Transactional
    public String save(Invoice invoice) {
        // 1. 保存发票主信息
        InvoiceEntity entity = invoiceConverter.toEntity(invoice);
        invoiceMapper.insert(entity);

        // 2. 保存付款方信息
        Party payer = invoice.getPayer();
        PartyEntity payerEntity = partyConverter.toEntity(payer);
        payerEntity.setInvoiceId(entity.getId());
        partyMapper.insert(payerEntity);
        
        // 3. 保存收款方信息
        Party payee = invoice.getPayee();
        PartyEntity payeeEntity = partyConverter.toEntity(payee);
        payeeEntity.setInvoiceId(entity.getId());
        partyMapper.insert(payeeEntity);

        // 4. 保存发票明细
        List<InvoiceItem> items = invoice.getItems();
        if (!CollectionUtils.isEmpty(items)) {
            for (InvoiceItem item : items) {
                InvoiceItemEntity itemEntity = invoiceItemConverter.toEntity(item, entity.getId());
                invoiceItemMapper.insert(itemEntity);
            }
        }
        
        // 5. 保存支付项
        List<PayItem> payItems = invoice.getPayItems();
        if (!CollectionUtils.isEmpty(payItems)) {
            for (PayItem payItem : payItems) {
                PayItemEntity payItemEntity = payItemConverter.toEntity(payItem, entity.getId());
                payItemMapper.insert(payItemEntity);
            }
        }
        
        return entity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findById(String invoiceId) {
        // 1. 查询发票主信息
        InvoiceEntity entity = invoiceMapper.selectById(invoiceId);
        if (entity == null) {
            return Optional.empty();
        }
        
        // 2. 查询付款方信息
        PartyEntity payerEntity = partyMapper.selectById(entity.getPayerId());
        Party payer = partyConverter.toDomain(payerEntity);
        
        // 3. 查询收款方信息
        PartyEntity payeeEntity = partyMapper.selectById(entity.getPayeeId());
        Party payee = partyConverter.toDomain(payeeEntity);
        
        // 4. 查询发票明细
        LambdaQueryWrapper<InvoiceItemEntity> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(InvoiceItemEntity::getInvoiceId, invoiceId);
        List<InvoiceItemEntity> itemEntities = invoiceItemMapper.selectList(itemWrapper);
        List<InvoiceItem> items = itemEntities.stream()
                .map(itemEntity -> invoiceItemConverter.toDomain(itemEntity, entity.getCurrency(), entity.getScale()))
                .collect(Collectors.toList());
        
        // 5. 查询支付项
        LambdaQueryWrapper<PayItemEntity> payItemWrapper = new LambdaQueryWrapper<>();
        payItemWrapper.eq(PayItemEntity::getInvoiceId, invoiceId);
        List<PayItemEntity> payItemEntities = payItemMapper.selectList(payItemWrapper);
        List<PayItem> payItems = payItemEntities.stream()
                .map(payItemEntity -> payItemConverter.toDomain(payItemEntity, entity.getCurrency(), entity.getScale()))
                .collect(Collectors.toList());
        
        // 6. 组装发票聚合根
        Invoice invoice = invoiceConverter.toDomain(entity);
        invoice.setPayer(payer);
        invoice.setPayee(payee);
        invoice.setItems(items);
        invoice.setPayItems(payItems);
        
        return Optional.of(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findByOrderId(String orderId) {
        // 1. 查询发票主信息列表
        LambdaQueryWrapper<InvoiceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InvoiceEntity::getOrderId, orderId);
        List<InvoiceEntity> entities = invoiceMapper.selectList(wrapper);
        
        // 2. 转换为发票聚合根列表
        return entities.stream()
                .map(entity -> {
                    // 查询付款方信息
                    PartyEntity payerEntity = partyMapper.selectById(entity.getPayerId());
                    Party payer = partyConverter.toDomain(payerEntity);
                    
                    // 查询收款方信息
                    PartyEntity payeeEntity = partyMapper.selectById(entity.getPayeeId());
                    Party payee = partyConverter.toDomain(payeeEntity);
                    
                    // 查询发票明细
                    LambdaQueryWrapper<InvoiceItemEntity> itemWrapper = new LambdaQueryWrapper<>();
                    itemWrapper.eq(InvoiceItemEntity::getInvoiceId, entity.getId());
                    List<InvoiceItemEntity> itemEntities = invoiceItemMapper.selectList(itemWrapper);
                    List<InvoiceItem> items = itemEntities.stream()
                            .map(itemEntity -> invoiceItemConverter.toDomain(itemEntity, entity.getCurrency(), entity.getScale()))
                            .collect(Collectors.toList());
                    
                    // 组装发票聚合根
                    Invoice invoice = invoiceConverter.toDomain(entity);
                    invoice.setPayer(payer);
                    invoice.setPayee(payee);
                    invoice.setItems(items);
                    
                    return invoice;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean update(Invoice invoice) {
        // 1. 更新付款方信息
        Party payer = invoice.getPayer();
        PartyEntity payerEntity = partyConverter.toEntity(payer);
        partyMapper.updateById(payerEntity);
        
        // 2. 更新收款方信息
        Party payee = invoice.getPayee();
        PartyEntity payeeEntity = partyConverter.toEntity(payee);
        partyMapper.updateById(payeeEntity);
        
        // 3. 更新发票主信息
        InvoiceEntity entity = invoiceConverter.toEntity(invoice);
        entity.setPayerId(payerEntity.getPartyId());
        entity.setPayeeId(payeeEntity.getPartyId());
        int rows = invoiceMapper.updateById(entity);
        
        // 4. 更新发票明细
        List<InvoiceItem> items = invoice.getItems();
        if (!CollectionUtils.isEmpty(items)) {
            // 先删除原有明细
            LambdaQueryWrapper<InvoiceItemEntity> itemWrapper = new LambdaQueryWrapper<>();
            itemWrapper.eq(InvoiceItemEntity::getInvoiceId, entity.getId());
            invoiceItemMapper.delete(itemWrapper);
            
            // 插入新的明细
            for (InvoiceItem item : items) {
                InvoiceItemEntity itemEntity = invoiceItemConverter.toEntity(item, entity.getId());
                invoiceItemMapper.insert(itemEntity);
            }
        }
        
        // 5. 更新支付项
        List<PayItem> payItems = invoice.getPayItems();
        if (!CollectionUtils.isEmpty(payItems)) {
            // 先删除原有支付项
            LambdaQueryWrapper<PayItemEntity> payItemWrapper = new LambdaQueryWrapper<>();
            payItemWrapper.eq(PayItemEntity::getInvoiceId, entity.getId());
            payItemMapper.delete(payItemWrapper);
            
            // 插入新的支付项
            for (PayItem payItem : payItems) {
                PayItemEntity payItemEntity = payItemConverter.toEntity(payItem, entity.getId());
                payItemMapper.insert(payItemEntity);
            }
        }
        
        return rows > 0;
    }

    @Override
    @Transactional
    public boolean delete(String invoiceId) {
        // 1. 删除发票明细
        LambdaQueryWrapper<InvoiceItemEntity> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(InvoiceItemEntity::getInvoiceId, invoiceId);
        invoiceItemMapper.delete(itemWrapper);
        
        // 2. 删除支付项
        LambdaQueryWrapper<PayItemEntity> payItemWrapper = new LambdaQueryWrapper<>();
        payItemWrapper.eq(PayItemEntity::getInvoiceId, invoiceId);
        payItemMapper.delete(payItemWrapper);
        
        // 3. 删除发票主信息
        InvoiceEntity entity = invoiceMapper.selectById(invoiceId);
        if (entity != null) {
            // 4. 删除付款方信息
            partyMapper.deleteById(entity.getPayerId());
            
            // 5. 删除收款方信息
            partyMapper.deleteById(entity.getPayeeId());
            
            // 6. 删除发票主信息
            int rows = invoiceMapper.deleteById(invoiceId);
            return rows > 0;
        }
        
        return false;
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

}