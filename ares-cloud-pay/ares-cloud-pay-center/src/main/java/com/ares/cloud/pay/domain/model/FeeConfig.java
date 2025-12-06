package com.ares.cloud.pay.domain.model;

import lombok.Data;

/**
 * 手续费配置领域模型
 */
@Data
public class FeeConfig {
    
    /**
     * 配置ID
     */
    private String id;
    
    /**
     * 交易类型
     */
    private String transactionType;
    
    /**
     * 支付区域
     */
    private String paymentRegion;
    
    /**
     * 手续费百分比（以万分比为单位，如100表示1%）
     */
    private Integer feeRate;
    
    /**
     * 最低手续费金额（以分为单位）
     */
    private Long minFeeAmount;
    
    /**
     * 最高手续费金额（以分为单位）
     */
    private Long maxFeeAmount;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 创建手续费配置
     */
    public static FeeConfig create(String transactionType, String paymentRegion, 
                                 Integer feeRate, Long minFeeAmount, Long maxFeeAmount) {
        FeeConfig config = new FeeConfig();
        config.setId(org.ares.cloud.common.utils.IdUtils.fastSimpleUUID());
        config.setTransactionType(transactionType);
        config.setPaymentRegion(paymentRegion);
        config.setFeeRate(feeRate);
        config.setMinFeeAmount(minFeeAmount);
        config.setMaxFeeAmount(maxFeeAmount);
        config.setEnabled(true);
        config.setCreateTime(System.currentTimeMillis());
        config.setUpdateTime(System.currentTimeMillis());
        return config;
    }
    
    /**
     * 计算手续费金额
     */
    public Long calculateFeeAmount(Long transactionAmount) {
        if (!enabled || feeRate == null || feeRate <= 0) {
            return 0L;
        }
        
        // 计算手续费 = 交易金额 * 手续费比例
        Long feeAmount = (transactionAmount * feeRate) / 10000;
        
        // 应用最低和最高限制
        if (minFeeAmount != null && feeAmount < minFeeAmount) {
            feeAmount = minFeeAmount;
        }
        if (maxFeeAmount != null && feeAmount > maxFeeAmount) {
            feeAmount = maxFeeAmount;
        }
        
        return feeAmount;
    }
    
    /**
     * 创建默认的免手续费配置
     */
    public static FeeConfig createDefaultNoFeeConfig(String transactionType, String paymentRegion) {
        FeeConfig feeConfig = new FeeConfig();
        feeConfig.setId("DEFAULT_NO_FEE_" + transactionType + "_" + paymentRegion);
        feeConfig.setTransactionType(transactionType);
        feeConfig.setPaymentRegion(paymentRegion);
        feeConfig.setFeeRate(0);
        feeConfig.setMinFeeAmount(0L);
        feeConfig.setMaxFeeAmount(0L);
        feeConfig.setEnabled(true);
        feeConfig.setCreateTime(System.currentTimeMillis());
        feeConfig.setUpdateTime(System.currentTimeMillis());
        return feeConfig;
    }
    
    /**
     * 检查是否为免手续费配置
     */
    public boolean isNoFee() {
        return feeRate == null || feeRate <= 0;
    }
    
    /**
     * 检查配置是否有效
     */
    public boolean isValid() {
        return enabled != null && enabled && feeRate != null && feeRate >= 0;
    }
} 