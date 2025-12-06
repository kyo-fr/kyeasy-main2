package com.ares.cloud.pay.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;

/**
 * 账户流水实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("payment_account_flow")
public class AccountFlowEntity extends BaseEntity {
    
    /**
     * 账户ID
     */
    private String accountId;
    
    /**
     * 交易ID
     */
    private String transactionId;
    
    /**
     * 流水类型
     */
    private String flowType;
    
    /**
     * 交易类型（记录资金用途）
     */
    private String transactionType;
    
    /**
     * 变动金额
     */
    private Long amount;
    
    /**
     * 手续费金额
     */
    private Long feeAmount;
    
    /**
     * 手续费百分比（以万分比为单位，如100表示1%）
     */
    private Integer feeRate;
    
    /**
     * 实际到账金额（扣除手续费后的金额）
     */
    private Long actualAmount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 币种精度
     */
    private Integer scale;
    
    /**
     * 变动前余额
     */
    private Long balanceBefore;
    
    /**
     * 变动后余额
     */
    private Long balanceAfter;
    
    /**
     * 创建时间
     */
    private Long createTime;
} 