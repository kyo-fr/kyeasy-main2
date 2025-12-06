package com.ares.cloud.pay.application.handlers;

import com.ares.cloud.pay.application.dto.MerchantDetailDTO;
import com.ares.cloud.pay.application.dto.MerchantSummaryDTO;
import com.ares.cloud.pay.application.dto.MerchantStatistics;
import com.ares.cloud.pay.application.dto.TransactionTypeStatistics;
import com.ares.cloud.pay.application.dto.WalletDTO;
import com.ares.cloud.pay.application.queries.EntityQuery;
import com.ares.cloud.pay.application.queries.MerchantStatisticsQuery;
import com.ares.cloud.pay.domain.enums.FlowType;
import com.ares.cloud.pay.domain.enums.TransactionType;
import com.ares.cloud.pay.domain.model.Merchant;
import com.ares.cloud.pay.domain.model.Wallet;
import com.ares.cloud.pay.domain.repository.MerchantRepository;
import com.ares.cloud.pay.domain.repository.WalletRepository;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.infrastructure.persistence.entity.MerchantEntity;
import com.ares.cloud.pay.infrastructure.persistence.entity.MerchantStatisticsResult;
import com.ares.cloud.pay.infrastructure.persistence.entity.TransactionTypeStatisticsResult;
import com.ares.cloud.pay.infrastructure.persistence.mapper.MerchantMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商户查询处理器
 * 处理所有商户相关的查询操作
 */
@Component
@Slf4j
public class MerchantQueryHandler {

    @Resource
    private MerchantRepository merchantRepository;
    
    @Resource
    private WalletRepository walletRepository;
    
    @Resource
    private MerchantMapper merchantMapper;

    /**
     * 根据商户ID查询商户
     */
    public MerchantDetailDTO getMerchantById(String merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId);
        if (merchant == null) {
            return null;
        }
        return convertToDetailDTO(merchant);
    }

    /**
     * 根据用户ID查询商户
     */
    public MerchantDetailDTO getMerchantByUserId(String id) {
        Merchant merchant = merchantRepository.findById(id);
        if (merchant != null) {
            return convertToDetailDTO(merchant);
        }
        return null;
    }

    /**
     * 根据商户号查询商户
     */
    public MerchantDetailDTO getMerchantByMerchantNo(String merchantNo) {
        Merchant merchant = merchantRepository.findByMerchantNo(merchantNo);
        if (merchant == null) {
            return null;
        }
        return convertToDetailDTO(merchant);
    }

    /**
     * 根据国家编号和手机号查询商户
     */
    public MerchantDetailDTO getMerchantByCountryCodeAndPhone(String countryCode, String phone) {
        // 注意：这里需要根据实际的仓储接口实现
        // 目前仓储接口中没有这个方法，需要扩展仓储接口
        // 暂时返回null，实际使用时需要实现对应的方法
        return null;
    }

    /**
     * 查询商户列表
     */
    public PageResult<MerchantSummaryDTO> getMerchantList(EntityQuery query) {
        validateQuery(query);
        
        try {
            // 构建查询条件
            LambdaQueryWrapper<MerchantEntity> wrapper = buildBaseWrapper(query);
            
            // 执行分页查询
            Page<MerchantEntity> page = new Page<>(query.getPage(), query.getLimit());
            Page<MerchantEntity> merchantPage = merchantMapper.selectPage(page, wrapper);

            if (merchantPage.getRecords().isEmpty()) {
                return new PageResult<>(Collections.emptyList(), 0L);
            }

            // 转换为DTO列表
            List<MerchantSummaryDTO> dtos = merchantPage.getRecords().stream()
                .map(entity -> {
                    Merchant merchant = merchantRepository.findById(entity.getId());
                    return convertToSummaryDTO(merchant);
                })
                .collect(Collectors.toList());

            return new PageResult<>(dtos, merchantPage.getTotal());
        } catch (Exception e) {
            log.error("查询商户列表失败", e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }

    /**
     * 验证查询参数的有效性
     *
     * @param query 查询参数对象
     * @throws BusinessException 当参数无效时抛出
     */
    private void validateQuery(EntityQuery query) {
        if (query == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (query.getPage() < 1) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (query.getLimit() < 1) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }

    /**
     * 构建基础查询条件
     *
     * @param query 查询参数
     * @return 查询条件包装器
     */
    private LambdaQueryWrapper<MerchantEntity> buildBaseWrapper(EntityQuery query) {
        LambdaQueryWrapper<MerchantEntity> wrapper = Wrappers.lambdaQuery();
        
        // 添加时间范围条件
        if (query.getStartTime() != null && query.getEndTime() != null) {
            wrapper.between(MerchantEntity::getCreateTime, query.getStartTime(), query.getEndTime());
        } else if (query.getStartTime() != null) {
            wrapper.ge(MerchantEntity::getCreateTime, query.getStartTime());
        } else if (query.getEndTime() != null) {
            wrapper.le(MerchantEntity::getCreateTime, query.getEndTime());
        }
        
        // 添加状态条件
        if (StringUtils.hasText(query.getStatus())) {
            wrapper.eq(MerchantEntity::getStatus, query.getStatus());
        }
        
        // 添加手机号条件
        if (StringUtils.hasText(query.getPhone())) {
            wrapper.eq(MerchantEntity::getContactPhone, query.getPhone());
        }
        
        // 添加国家代码条件（这里可以根据实际业务需求调整）
        if (StringUtils.hasText(query.getCountryCode())) {
            // 如果商户实体中有国家代码字段，可以添加相应条件
            // wrapper.eq(MerchantEntity::getCountryCode, query.getCountryCode());
        }
        
        // 添加关键字搜索
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w
                .like(MerchantEntity::getMerchantName, query.getKeyword())
                .or()
                .like(MerchantEntity::getMerchantNo, query.getKeyword())
                .or()
                .like(MerchantEntity::getContactPerson, query.getKeyword())
                .or()
                .like(MerchantEntity::getContactPhone, query.getKeyword())
            );
        }
        
        // 默认按创建时间降序
        wrapper.orderByDesc(MerchantEntity::getCreateTime);
        
        return wrapper;
    }

    /**
     * 转换为商户详情DTO
     */
    private MerchantDetailDTO convertToDetailDTO(Merchant merchant) {
        MerchantDetailDTO dto = new MerchantDetailDTO();
        dto.setId(merchant.getId());
        dto.setMerchantNo(merchant.getMerchantNo());
        dto.setMerchantName(merchant.getMerchantName());
        dto.setMerchantType(merchant.getMerchantType());
        dto.setStatus(merchant.getStatus());
        dto.setContactPerson(merchant.getContactPerson());
        dto.setContactPhone(merchant.getContactPhone());
        dto.setContactEmail(merchant.getContactEmail());
        dto.setAddress(merchant.getAddress());
        dto.setBusinessLicense(merchant.getBusinessLicense());
        dto.setLegalRepresentative(merchant.getLegalRepresentative());
        dto.setSupportedRegions(merchant.getSupportedRegions());
        dto.setCreateTime(merchant.getCreateTime());
        dto.setUpdateTime(merchant.getUpdateTime());
        
        // 查询商户的钱包信息
        List<Wallet> wallets = walletRepository.findByOwnerId(merchant.getId());
        List<WalletDTO> walletDTOs = wallets.stream()
                .map(WalletDTO::from)
                .collect(Collectors.toList());
        dto.setWallets(walletDTOs);
        
        return dto;
    }
    
    /**
     * 转换为商户摘要DTO
     */
    private MerchantSummaryDTO convertToSummaryDTO(Merchant merchant) {
        MerchantSummaryDTO dto = new MerchantSummaryDTO();
        dto.setId(merchant.getId());
        dto.setMerchantNo(merchant.getMerchantNo());
        dto.setMerchantName(merchant.getMerchantName());
        dto.setMerchantType(merchant.getMerchantType());
        dto.setStatus(merchant.getStatus());
        dto.setContactPerson(merchant.getContactPerson());
        dto.setContactPhone(merchant.getContactPhone());
        dto.setCreateTime(merchant.getCreateTime());
        return dto;
    }
    
    /**
     * 查询商户统计信息
     */
    public MerchantStatistics getMerchantStatistics(MerchantStatisticsQuery query) {
        try {
            // 验证查询参数
            validateStatisticsQuery(query);
            
            // 计算时间范围
            Long startTime = null;
            Long endTime = null;
            
            if ("DAY".equals(query.getQueryType())) {
                // 按天查询，使用开始时间和结束时间
                // 如果没有设置时间，则使用当天的开始和结束时间
                if (query.getStartTime() == null || query.getEndTime() == null) {
                    startTime = getTodayStartTime();
                    endTime = getTodayEndTime();
                } else {
                    startTime = query.getStartTime();
                    endTime = query.getEndTime();
                }
            } else if ("MONTH".equals(query.getQueryType())) {
                // 按月查询，解析查询值
                // 如果没有提供queryValue，则使用当前月份
                if (StringUtils.hasText(query.getQueryValue())) {
                    String[] parts = query.getQueryValue().split("-");
                    if (parts.length == 2) {
                        int year = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        startTime = getMonthStartTime(year, month);
                        endTime = getMonthEndTime(year, month);
                    }
                } else {
                    // 使用当前月份
                    java.time.LocalDate now = java.time.LocalDate.now();
                    startTime = getMonthStartTime(now.getYear(), now.getMonthValue());
                    endTime = getMonthEndTime(now.getYear(), now.getMonthValue());
                }
            } else if ("YEAR".equals(query.getQueryType())) {
                // 按年查询，解析查询值
                // 如果没有提供queryValue，则使用当前年份
                if (StringUtils.hasText(query.getQueryValue())) {
                    int year = Integer.parseInt(query.getQueryValue());
                    startTime = getYearStartTime(year);
                    endTime = getYearEndTime(year);
                } else {
                    // 使用当前年份
                    int currentYear = java.time.LocalDate.now().getYear();
                    startTime = getYearStartTime(currentYear);
                    endTime = getYearEndTime(currentYear);
                }
            }
            
            // 获取当前商户
            Merchant merchant = merchantRepository.findById(ApplicationContext.getTenantId());
            if (merchant == null) {
                // 如果商户不存在，返回空统计数据
                return convertToStatisticsDTO(null, query.getPaymentRegion(), startTime, endTime);
            }
            
            // 查询统计结果
            MerchantStatisticsResult result = merchantMapper.getMerchantStatistics(
                merchant.getId(), startTime, endTime, query.getPaymentRegion());
            
            // 查询按交易类型分组的统计结果
            List<TransactionTypeStatisticsResult> typeResults = merchantMapper.getMerchantTransactionTypeStatistics(
                merchant.getId(), startTime, endTime, query.getPaymentRegion());
            
            // 转换为DTO
            return convertToStatisticsDTO(result, typeResults, query.getPaymentRegion(), startTime, endTime);
            
        } catch (Exception e) {
            log.error("查询商户统计信息失败", e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 验证统计查询参数
     */
    private void validateStatisticsQuery(MerchantStatisticsQuery query) {
        if (query == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (!StringUtils.hasText(query.getQueryType())) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (!"DAY".equals(query.getQueryType()) && !"MONTH".equals(query.getQueryType()) && !"YEAR".equals(query.getQueryType())) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 获取月份开始时间戳
     */
    private Long getMonthStartTime(int year, int month) {
        java.time.LocalDateTime start = java.time.LocalDateTime.of(year, month, 1, 0, 0, 0);
        return start.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取月份结束时间戳
     */
    private Long getMonthEndTime(int year, int month) {
        java.time.LocalDateTime end = java.time.LocalDateTime.of(year, month, 1, 0, 0, 0)
            .plusMonths(1).minusSeconds(1);
        return end.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取年份开始时间戳
     */
    private Long getYearStartTime(int year) {
        java.time.LocalDateTime start = java.time.LocalDateTime.of(year, 1, 1, 0, 0, 0);
        return start.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取年份结束时间戳
     */
    private Long getYearEndTime(int year) {
        java.time.LocalDateTime end = java.time.LocalDateTime.of(year, 12, 31, 23, 59, 59);
        return end.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取当天开始时间戳
     */
    private Long getTodayStartTime() {
        java.time.LocalDateTime start = java.time.LocalDate.now().atStartOfDay();
        return start.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取当天结束时间戳
     */
    private Long getTodayEndTime() {
        java.time.LocalDateTime end = java.time.LocalDate.now().atTime(23, 59, 59);
        return end.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 转换为统计DTO
     */
    private MerchantStatistics convertToStatisticsDTO(MerchantStatisticsResult result, 
                                                       List<TransactionTypeStatisticsResult> typeResults,
                                                       String paymentRegion, 
                                                       Long startTime, 
                                                       Long endTime) {
        MerchantStatistics dto = new MerchantStatistics();
        
        // 设置时间范围
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setPaymentRegion(paymentRegion);
        
        // 格式化时间字符串
        if (startTime != null) {
            dto.setStartTimeStr(formatTime(startTime));
        }
        if (endTime != null) {
            dto.setEndTimeStr(formatTime(endTime));
        }
        
        String currency = paymentRegion != null ? paymentRegion : "CNY";
        
        if (result == null) {
            // 如果没有数据，返回默认值
            dto.setTotalFeeExpenditure(Money.zero(currency, 2));
            dto.setTotalFeeTax(Money.zero(currency, 2));
            dto.setTotalFeeTaxRate(BigDecimal.valueOf(20.00));
            dto.setTotalFeeTransactionCount(0);
            
            dto.setMerchantFee(Money.zero(currency, 2));
            dto.setMerchantFeeTax(Money.zero(currency, 2));
            dto.setMerchantFeeTaxRate(BigDecimal.valueOf(20.00));
            dto.setMerchantFeeTransactionCount(0);
            
            // 收入类
            dto.setMerchantCollectionIncome(Money.zero(currency, 2));
            dto.setMerchantCollectionTransactionCount(0);
            dto.setMerchantCollectionFee(Money.zero(currency, 2));
            dto.setMerchantCollectionFeeRate(BigDecimal.valueOf(0.50));
            
            dto.setMerchantPurchaseIncome(Money.zero(currency, 2));
            dto.setMerchantPurchaseTransactionCount(0);
            dto.setMerchantPurchaseFee(Money.zero(currency, 2));
            dto.setMerchantPurchaseFeeRate(BigDecimal.ZERO);
            
            // 支出类
            dto.setActivityGiftExpenditure(Money.zero(currency, 2));
            dto.setActivityGiftTransactionCount(0);
            dto.setActivityGiftFee(Money.zero(currency, 2));
            dto.setActivityGiftFeeRate(BigDecimal.ZERO);
            
            dto.setDiscountGrantExpenditure(Money.zero(currency, 2));
            dto.setDiscountGrantTransactionCount(0);
            dto.setDiscountGrantFee(Money.zero(currency, 2));
            dto.setDiscountGrantFeeRate(BigDecimal.ZERO);
            
            dto.setPriceGrantExpenditure(Money.zero(currency, 2));
            dto.setPriceGrantTransactionCount(0);
            dto.setPriceGrantFee(Money.zero(currency, 2));
            dto.setPriceGrantFeeRate(BigDecimal.ZERO);
            
            dto.setMerchantSellExpenditure(Money.zero(currency, 2));
            dto.setMerchantSellTransactionCount(0);
            dto.setMerchantSellFee(Money.zero(currency, 2));
            dto.setMerchantSellFeeRate(BigDecimal.valueOf(1.50));
            
            return dto;
        }
        
        // 设置顶部摘要统计
        dto.setTotalFeeExpenditure(Money.create(result.getTotalFeeExpenditure() != null ? result.getTotalFeeExpenditure() : 0L, currency, 2));
        dto.setTotalFeeTax(Money.create((result.getTotalFeeExpenditure() != null ? result.getTotalFeeExpenditure() : 0L) * 20 / 100, currency, 2));
        dto.setTotalFeeTaxRate(BigDecimal.valueOf(20.00));
        dto.setTotalFeeTransactionCount(result.getTotalFeeTransactionCount() != null ? result.getTotalFeeTransactionCount() : 0);
        
        dto.setMerchantFee(Money.create(result.getMerchantFee() != null ? result.getMerchantFee() : 0L, currency, 2));
        dto.setMerchantFeeTax(Money.create((result.getMerchantFee() != null ? result.getMerchantFee() : 0L) * 20 / 100, currency, 2));
        dto.setMerchantFeeTaxRate(BigDecimal.valueOf(20.00));
        dto.setMerchantFeeTransactionCount(result.getMerchantFeeTransactionCount() != null ? result.getMerchantFeeTransactionCount() : 0);
        
        // 设置详细统计 - 收入类
        dto.setMerchantCollectionIncome(Money.create(result.getMerchantCollectionIncome() != null ? result.getMerchantCollectionIncome() : 0L, currency, 2));
        dto.setMerchantCollectionTransactionCount(result.getMerchantCollectionTransactionCount() != null ? result.getMerchantCollectionTransactionCount() : 0);
        dto.setMerchantCollectionFee(Money.create(result.getMerchantCollectionFee() != null ? result.getMerchantCollectionFee() : 0L, currency, 2));
        dto.setMerchantCollectionFeeRate(BigDecimal.valueOf(0.50));
        
        dto.setMerchantPurchaseIncome(Money.create(result.getMerchantPurchaseIncome() != null ? result.getMerchantPurchaseIncome() : 0L, currency, 2));
        dto.setMerchantPurchaseTransactionCount(result.getMerchantPurchaseTransactionCount() != null ? result.getMerchantPurchaseTransactionCount() : 0);
        dto.setMerchantPurchaseFee(Money.create(result.getMerchantPurchaseFee() != null ? result.getMerchantPurchaseFee() : 0L, currency, 2));
        dto.setMerchantPurchaseFeeRate(BigDecimal.ZERO);
        
        // 设置详细统计 - 支出类
        dto.setActivityGiftExpenditure(Money.create(result.getActivityGiftExpenditure() != null ? result.getActivityGiftExpenditure() : 0L, currency, 2));
        dto.setActivityGiftTransactionCount(result.getActivityGiftTransactionCount() != null ? result.getActivityGiftTransactionCount() : 0);
        dto.setActivityGiftFee(Money.create(result.getActivityGiftFee() != null ? result.getActivityGiftFee() : 0L, currency, 2));
        dto.setActivityGiftFeeRate(BigDecimal.ZERO);
        
        dto.setDiscountGrantExpenditure(Money.create(result.getDiscountGrantExpenditure() != null ? result.getDiscountGrantExpenditure() : 0L, currency, 2));
        dto.setDiscountGrantTransactionCount(result.getDiscountGrantTransactionCount() != null ? result.getDiscountGrantTransactionCount() : 0);
        dto.setDiscountGrantFee(Money.create(result.getDiscountGrantFee() != null ? result.getDiscountGrantFee() : 0L, currency, 2));
        dto.setDiscountGrantFeeRate(BigDecimal.ZERO);
        
        dto.setPriceGrantExpenditure(Money.create(result.getPriceGrantExpenditure() != null ? result.getPriceGrantExpenditure() : 0L, currency, 2));
        dto.setPriceGrantTransactionCount(result.getPriceGrantTransactionCount() != null ? result.getPriceGrantTransactionCount() : 0);
        dto.setPriceGrantFee(Money.create(result.getPriceGrantFee() != null ? result.getPriceGrantFee() : 0L, currency, 2));
        dto.setPriceGrantFeeRate(BigDecimal.ZERO);
        
        dto.setMerchantSellExpenditure(Money.create(result.getMerchantSellExpenditure() != null ? result.getMerchantSellExpenditure() : 0L, currency, 2));
        dto.setMerchantSellTransactionCount(result.getMerchantSellTransactionCount() != null ? result.getMerchantSellTransactionCount() : 0);
        dto.setMerchantSellFee(Money.create(result.getMerchantSellFee() != null ? result.getMerchantSellFee() : 0L, currency, 2));
        dto.setMerchantSellFeeRate(BigDecimal.valueOf(1.50));
        
        // 转换按交易类型分组的统计
        if (typeResults != null && !typeResults.isEmpty()) {
            List<TransactionTypeStatistics> typeStatsList = typeResults.stream()
                .map(typeResult -> convertToTransactionTypeStatistics(typeResult, currency))
                .collect(Collectors.toList());
            dto.setTransactionTypeStatistics(typeStatsList);
        } else {
            dto.setTransactionTypeStatistics(new ArrayList<>());
        }
        
        return dto;
    }
    
    /**
     * 转换为统计DTO（重载方法，用于无数据时）
     */
    private MerchantStatistics convertToStatisticsDTO(MerchantStatisticsResult result, String paymentRegion, Long startTime, Long endTime) {
        return convertToStatisticsDTO(result, null, paymentRegion, startTime, endTime);
    }
    
    /**
     * 转换交易类型统计结果
     */
    private TransactionTypeStatistics convertToTransactionTypeStatistics(TransactionTypeStatisticsResult result, String currency) {
        TransactionTypeStatistics dto = new TransactionTypeStatistics();
        
        // 设置交易类型信息
        dto.setTransactionType(result.getTransactionType());
        TransactionType transactionType = TransactionType.fromCode(result.getTransactionType());
        dto.setTransactionTypeDesc(transactionType != null ? transactionType.getDescription() : result.getTransactionType());
        
        // 设置流水类型信息
        dto.setFlowType(result.getFlowType());
        FlowType flowType = FlowType.fromCode(result.getFlowType());
        dto.setFlowTypeDesc(flowType != null ? flowType.getDesc() : result.getFlowType());
        
        // 设置金额和数量
        dto.setAmount(Money.create(result.getAmount() != null ? result.getAmount() : 0L, currency, 2));
        dto.setTransactionCount(result.getTransactionCount() != null ? result.getTransactionCount() : 0);
        dto.setFeeAmount(Money.create(result.getFeeAmount() != null ? result.getFeeAmount() : 0L, currency, 2));
        
        // 计算平均费率（百分比）
        if (result.getTotalFeeRate() != null && result.getTransactionCount() != null && result.getTransactionCount() > 0) {
            // fee_rate 是以万分比为单位，如100表示1%
            BigDecimal avgFeeRate = BigDecimal.valueOf(result.getTotalFeeRate())
                .divide(BigDecimal.valueOf(result.getTransactionCount()), 2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            dto.setAvgFeeRate(avgFeeRate);
        } else {
            dto.setAvgFeeRate(BigDecimal.ZERO);
        }
        
        return dto;
    }
    
    /**
     * 格式化时间为 dd/MM/yyyy 格式
     */
    private String formatTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        java.time.Instant instant = java.time.Instant.ofEpochMilli(timestamp);
        java.time.LocalDate date = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

} 