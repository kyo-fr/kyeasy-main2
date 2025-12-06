package com.ares.cloud.order.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单退款事件消息
 * 领域层消息，表示订单退款这一业务事件
 *
 * @author ares-cloud
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRefundedEventMessage {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款方式
     */
    private String refundMode;

    /**
     * 退款交易ID
     */
    private String refundTransactionId;

    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 事件类型
     */
    @Builder.Default
    private String eventType = "ORDER_REFUNDED";
}
