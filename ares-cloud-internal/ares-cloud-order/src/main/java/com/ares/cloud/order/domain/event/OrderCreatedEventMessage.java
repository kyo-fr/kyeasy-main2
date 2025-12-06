package com.ares.cloud.order.domain.event;

import com.ares.cloud.order.domain.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单创建事件消息
 * 领域层消息，表示订单创建这一业务事件
 *
 * @author ares-cloud
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEventMessage {

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
     * 订单类型
     */
    private OrderType orderType;

    /**
     * 订单总金额
     */
    private String totalAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 事件类型
     */
    @Builder.Default
    private String eventType = "ORDER_CREATED";
}
