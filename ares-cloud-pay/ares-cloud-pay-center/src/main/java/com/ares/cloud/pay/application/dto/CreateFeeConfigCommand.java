package com.ares.cloud.pay.application.dto;

import lombok.Data;

/**
 * 创建手续费配置命令
 */
@Data
public class CreateFeeConfigCommand {
    
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
} 