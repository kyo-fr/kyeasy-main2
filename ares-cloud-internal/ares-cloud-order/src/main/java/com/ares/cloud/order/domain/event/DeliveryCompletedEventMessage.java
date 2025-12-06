package com.ares.cloud.order.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 配送完成事件消息
 * 领域层消息，表示配送完成这一业务事件
 *
 * @author ares-cloud
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCompletedEventMessage {

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
     * 完成时间
     */
    private LocalDateTime completedTime;

    /**
     * 实际配送地址
     */
    private String actualDeliveryAddress;

    /**
     * 收货人确认
     */
    private Boolean receiverConfirmed;

    /**
     * 配送备注
     */
    private String deliveryNote;

    /**
     * 配送评分
     */
    private Integer deliveryRating;

    /**
     * 事件类型
     */
    @Builder.Default
    private String eventType = "DELIVERY_COMPLETED";
}
