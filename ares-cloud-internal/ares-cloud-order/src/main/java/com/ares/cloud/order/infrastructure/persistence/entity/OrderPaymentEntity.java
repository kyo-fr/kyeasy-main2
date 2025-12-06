package com.ares.cloud.order.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;

/**
 * 订单支付记录实体
 */
@Data
@TableName("order_payments")
@EqualsAndHashCode(callSuper = true)
public class OrderPaymentEntity extends BaseEntity {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 支付渠道ID
     */
    private String channelId;

    /**
     * 支付流水号
     */
    private String tradeNo;

    /**
     * 支付金额
     */
    private Long amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 币种精度
     */
    private Integer currencyScale;

    /**
     * 支付用户ID
     */
    private String userId;

    /**
     * 支付状态(1:成功,0:失败)
     */
    private Integer status;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 支付完成时间
     */
    private Long payTime;
} 