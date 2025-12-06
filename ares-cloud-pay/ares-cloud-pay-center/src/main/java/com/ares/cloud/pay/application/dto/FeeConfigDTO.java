package com.ares.cloud.pay.application.dto;

import lombok.Data;

/**
 * 手续费配置DTO
 */
@Data
public class FeeConfigDTO {
    
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
} 