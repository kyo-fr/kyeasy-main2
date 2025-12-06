package com.ares.cloud.order.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 配送开始事件消息
 * 领域层消息，表示配送开始这一业务事件
 *
 * @author ares-cloud
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStartedEventMessage {

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
     * 配送员ID
     */
    private String riderId;

    /**
     * 配送类型
     */
    private String deliveryType;

    /**
     * 配送地址
     */
    private String deliveryAddress;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 预计配送时间
     */
    private LocalDateTime estimatedDeliveryTime;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 事件类型
     */
    @Builder.Default
    private String eventType = "DELIVERY_STARTED";
}
