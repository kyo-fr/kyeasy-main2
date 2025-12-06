package com.ares.cloud.pay.infrastructure.persistence.entity;

import lombok.Data;

/**
 * 按交易类型分组的统计查询结果
 */
@Data
public class TransactionTypeStatisticsResult {
    
    /**
     * 交易类型
     */
    private String transactionType;
    
    /**
     * 流水类型：IN-收入，OUT-支出
     */
    private String flowType;
    
    /**
     * 交易金额（以最小货币单位为单位）
     */
    private Long amount;
    
    /**
     * 交易数量
     */
    private Integer transactionCount;
    
    /**
     * 手续费金额（以最小货币单位为单位）
     */
    private Long feeAmount;
    
    /**
     * 手续费总和（用于计算平均费率）
     */
    private Long totalFeeRate;
}

