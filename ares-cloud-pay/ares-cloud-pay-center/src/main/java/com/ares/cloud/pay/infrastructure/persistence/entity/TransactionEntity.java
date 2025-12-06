package com.ares.cloud.pay.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
 * 交易实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("payment_transactions")
public class TransactionEntity extends BaseEntity {
    
    /**
     * 来源账户ID
     */
    private String fromAccountId;
    
    /**
     * 目标账户ID
     */
    private String toAccountId;
    
    /**
     * 支付订单ID
     */
    private String orderId;
    
    /**
     * 交易金额（以分为单位）
     */
    private Long amount;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    private String paymentRegion;
    
    /**
     * 手续费金额（以分为单位）
     */
    private Long feeAmount;
    
    /**
     * 手续费百分比（以万分比为单位，如100表示1%）
     */
    private Integer feeRate;
    
    /**
     * 实际到账金额（扣除手续费后的金额，以分为单位）
     */
    private Long actualAmount;
    
    /**
     * 交易类型
     */
    private String type;
    
    /**
     * 交易状态
     */
    private String status;
    
    /**
     * 交易描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
} 