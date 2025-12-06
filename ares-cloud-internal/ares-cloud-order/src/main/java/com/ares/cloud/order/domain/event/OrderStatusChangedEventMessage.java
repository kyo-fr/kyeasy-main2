package com.ares.cloud.order.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单状态变更事件消息
 * 领域层消息，表示订单状态变更这一业务事件
 *
 * @author ares-cloud
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangedEventMessage {

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
     * 原状态
     */
    private String fromStatus;

    /**
     * 新状态
     */
    private String toStatus;

    /**
     * 变更原因
     */
    private String reason;

    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 变更时间
     */
    private LocalDateTime changeTime;

    /**
     * 事件类型
     */
    @Builder.Default
    private String eventType = "ORDER_STATUS_CHANGED";
}
