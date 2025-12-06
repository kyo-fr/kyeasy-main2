package com.ares.cloud.order.infrastructure.messaging;

import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.model.command.PayCommand;
import com.ares.cloud.order.domain.service.DomainEventPublisher;
import com.ares.cloud.order.domain.model.valueobject.PayItem;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.DeliveryType;
import com.ares.cloud.order.domain.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 领域事件发布器实现
 * 基础设施层实现，负责将领域事件转换为MQ消息并发送
 * 实现领域层定义的DomainEventPublisher接口
 * 
 * 使用 StreamBridge 动态发送消息到 RabbitMQ
 * 
 * 配置文件对应关系（application.yml）：
 * 【订单领域】order-events-exchange (direct)
 *   - routing key: order.created → orderCreatedConsumer（订单创建、通知商户）
 *   - routing key: order.paid → orderPaidConsumer（订单支付、发票、积分、礼物）
 *   - routing key: order.refunded → orderRefundedConsumer（订单退款、通知、恢复积分）
 *   - routing key: order.status → orderStatusConsumer（其他状态变更、通用处理）
 * 
 * 【配送领域】delivery-events-exchange (direct)
 *   - routing key: delivery.started → deliveryStartedConsumer（配送开始、通知）
 *   - routing key: delivery.completed → deliveryCompletedConsumer（配送完成、评价、结算）
 * 
 * 【库存处理】不发送到 MQ，直接同步调用 InventoryReservationService
 *   - 订单创建时：inventoryReservationService.reserveInventory()（同步预留）
 *   - 订单支付时：inventoryReservationService.confirmInventoryReservation()（同步扣减）
 *   - 订单退款时：inventoryReservationService.restoreInventory()（同步恢复）
 *
 * @author ares-cloud
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventPublisherImpl implements DomainEventPublisher {
    
    private final StreamBridge streamBridge;
    
    // 生产者 Binding 名称常量
    private static final String ORDER_EVENTS_BINDING = "orderEvents-out-0";
    private static final String DELIVERY_EVENTS_BINDING = "deliveryEvents-out-0";


    /**
     * 发布订单创建事件
     * Binding: orderEvents-out-0 -> order-events-exchange
     * Routing Key: order.created
     * 消费者: orderEventConsumer-in-0
     */
    @Override
    public void publishOrderCreated(String orderId, String merchantId, String userId, 
                                  String orderType, String totalAmount, LocalDateTime createTime) {
        try {
            OrderCreatedEventMessage payload = OrderCreatedEventMessage.builder()
                    .orderId(orderId)
                    .merchantId(merchantId)
                    .userId(userId)
                    .orderType(com.ares.cloud.order.domain.enums.OrderType.valueOf(orderType))
                    .totalAmount(totalAmount)
                    .createTime(createTime)
                    .build();

            // 构建消息，添加 routingKey 头用于路由
            Message<OrderCreatedEventMessage> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader("routingKey", "order.created")
                    .build();
            
            boolean sent = streamBridge.send(ORDER_EVENTS_BINDING, message);
            if (sent) {
                log.info("订单创建事件消息发布成功，订单ID: {}, routingKey: order.created", orderId);
            } else {
                log.warn("订单创建事件消息发布返回false，订单ID: {}", orderId);
            }
        } catch (Exception e) {
            // 消息发布失败不应该影响主业务流程，只记录日志
            log.error("发布订单创建事件消息失败，订单ID: {}, 错误信息: {}", orderId, e.getMessage(), e);
        }
    }

    /**
     * 发布订单支付事件
     * Binding: orderEvents-out-0 -> order-events-exchange
     * Routing Key: order.paid
     * 消费者: orderPaidInvoiceConsumer-in-0, orderPaidGiftConsumer-in-0
     * 
     * 只负责发布消息，具体业务逻辑（发票生成、礼物点赠送）由消费者处理
     */
    @Override
    public void publishOrderPaid(Order order, PayCommand command) {
        try {
            OrderPaidEventMessage payload = command.toEventMessage(order.getUserId());

            // 构建消息，添加 routingKey 头用于路由到订单支付消费者
            Message<OrderPaidEventMessage> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader("routingKey", "order.paid")
                    .build();
            
            boolean sent = streamBridge.send(ORDER_EVENTS_BINDING, message);
            if (sent) {
                log.info("订单支付成功事件消息发布成功，订单ID: {}, routingKey: order.paid", command.getOrderId());
            } else {
                log.warn("订单支付成功事件消息发布返回false，订单ID: {}", command.getOrderId());
            }
            
        } catch (Exception e) {
            // 消息发布失败记录日志，但不影响支付流程
            log.error("发布订单支付成功事件消息失败，订单ID: {}, 错误信息: {}", command.getOrderId(), e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    /**
     * 发布订单状态变更事件
     * Binding: orderEvents-out-0 -> order-events-exchange
     * Routing Key: order.status（其他状态变更统一处理）
     * 消费者: orderStatusConsumer-in-0
     * 
     * 适用场景：订单确认、取消、完成等状态变更
     */
    @Override
    public void publishOrderStatusChanged(String orderId, String merchantId, String userId,
                                        OrderStatus fromStatus, OrderStatus toStatus, String reason,
                                        String operatorId, LocalDateTime changeTime) {
        try {
            OrderStatusChangedEventMessage payload = OrderStatusChangedEventMessage.builder()
                    .orderId(orderId)
                    .merchantId(merchantId)
                    .userId(userId)
                    .fromStatus(fromStatus.name())
                    .toStatus(toStatus.name())
                    .reason(reason)
                    .operatorId(operatorId)
                    .changeTime(changeTime)
                    .build();

            // 构建消息，路由到订单状态变更消费者
            Message<OrderStatusChangedEventMessage> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader("routingKey", "order.status")
                    .build();
            
            boolean sent = streamBridge.send(ORDER_EVENTS_BINDING, message);
            if (sent) {
                log.info("订单状态变更事件消息发布成功，订单ID: {}, 状态: {} -> {}, routingKey: order.status", 
                        orderId, fromStatus, toStatus);
            } else {
                log.warn("订单状态变更事件消息发布返回false，订单ID: {}, 状态: {} -> {}", 
                        orderId, fromStatus, toStatus);
            }
        } catch (Exception e) {
            log.error("发布订单状态变更事件消息失败，订单ID: {}, 错误信息: {}", orderId, e.getMessage(), e);
        }
    }

    /**
     * 发布订单退款事件
     * Binding: orderEvents-out-0 -> order-events-exchange
     * Routing Key: order.refunded
     * 消费者: orderRefundedConsumer-in-0
     * 
     * 业务处理：发送退款通知、记录日志、扣减已赠送的积分
     */
    @Override
    public void publishOrderRefunded(String orderId, String merchantId, String userId,
                                   BigDecimal refundAmount, String refundReason, String refundMode,
                                   String refundTransactionId, String operatorId, LocalDateTime refundTime) {
        try {
            OrderRefundedEventMessage payload = OrderRefundedEventMessage.builder()
                    .orderId(orderId)
                    .merchantId(merchantId)
                    .userId(userId)
                    .refundAmount(refundAmount)
                    .refundReason(refundReason)
                    .refundMode(refundMode)
                    .refundTransactionId(refundTransactionId)
                    .operatorId(operatorId)
                    .refundTime(refundTime)
                    .build();

            // 构建消息，路由到订单退款消费者
            Message<OrderRefundedEventMessage> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader("routingKey", "order.refunded")
                    .build();
            
            boolean sent = streamBridge.send(ORDER_EVENTS_BINDING, message);
            if (sent) {
                log.info("订单退款事件消息发布成功，订单ID: {}, 退款金额: {}, routingKey: order.refunded", 
                        orderId, refundAmount);
            } else {
                log.warn("订单退款事件消息发布返回false，订单ID: {}, 退款金额: {}", orderId, refundAmount);
            }
        } catch (Exception e) {
            log.error("发布订单退款事件消息失败，订单ID: {}, 错误信息: {}", orderId, e.getMessage(), e);
        }
    }

    /**
     * 发布配送开始事件
     * Binding: deliveryEvents-out-0 -> delivery-events-exchange
     * Routing Key: delivery.started
     * 消费者: deliveryStartedConsumer-in-0
     * 
     * 业务处理：通知用户和配送员、开启配送轨迹追踪
     */
    @Override
    public void publishDeliveryStarted(String orderId, String merchantId, String userId, String riderId,
                                     DeliveryType deliveryType, String deliveryAddress, String receiverName,
                                     String receiverPhone, LocalDateTime estimatedDeliveryTime, LocalDateTime startTime) {
        try {
            DeliveryStartedEventMessage payload = DeliveryStartedEventMessage.builder()
                    .orderId(orderId)
                    .merchantId(merchantId)
                    .userId(userId)
                    .riderId(riderId)
                    .deliveryType(deliveryType.name())
                    .deliveryAddress(deliveryAddress)
                    .receiverName(receiverName)
                    .receiverPhone(receiverPhone)
                    .estimatedDeliveryTime(estimatedDeliveryTime)
                    .startTime(startTime)
                    .build();

            // 构建消息，路由到配送开始消费者
            Message<DeliveryStartedEventMessage> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader("routingKey", "delivery.started")
                    .build();
            
            boolean sent = streamBridge.send(DELIVERY_EVENTS_BINDING, message);
            if (sent) {
                log.info("配送开始事件消息发布成功，订单ID: {}, 配送员ID: {}, routingKey: delivery.started", 
                        orderId, riderId);
            } else {
                log.warn("配送开始事件消息发布返回false，订单ID: {}", orderId);
            }
        } catch (Exception e) {
            log.error("发布配送开始事件消息失败，订单ID: {}, 错误信息: {}", orderId, e.getMessage(), e);
        }
    }

    /**
     * 发布配送完成事件
     * Binding: deliveryEvents-out-0 -> delivery-events-exchange
     * Routing Key: delivery.completed
     * 消费者: deliveryCompletedConsumer-in-0
     * 
     * 业务处理：发送通知、处理评价、触发结算
     */
    @Override
    public void publishDeliveryCompleted(String orderId, String merchantId, String userId, String riderId,
                                       LocalDateTime completedTime, String actualDeliveryAddress,
                                       Boolean receiverConfirmed, String deliveryNote, Integer deliveryRating) {
        try {
            DeliveryCompletedEventMessage payload = DeliveryCompletedEventMessage.builder()
                    .orderId(orderId)
                    .merchantId(merchantId)
                    .userId(userId)
                    .riderId(riderId)
                    .completedTime(completedTime)
                    .actualDeliveryAddress(actualDeliveryAddress)
                    .receiverConfirmed(receiverConfirmed)
                    .deliveryNote(deliveryNote)
                    .deliveryRating(deliveryRating)
                    .build();

            // 构建消息，路由到配送完成消费者
            Message<DeliveryCompletedEventMessage> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader("routingKey", "delivery.completed")
                    .build();
            
            boolean sent = streamBridge.send(DELIVERY_EVENTS_BINDING, message);
            if (sent) {
                log.info("配送完成事件消息发布成功，订单ID: {}, 配送员ID: {}, routingKey: delivery.completed", 
                        orderId, riderId);
            } else {
                log.warn("配送完成事件消息发布返回false，订单ID: {}", orderId);
            }
        } catch (Exception e) {
            log.error("发布配送完成事件消息失败，订单ID: {}, 错误信息: {}", orderId, e.getMessage(), e);
        }
    }

    /**
     * 发布库存预留事件
     * 
     * 注：库存预留已改为同步处理，不再发送到 MQ
     * OrderDomainService 中直接调用 InventoryReservationService
     * 
     * 保留此方法是为了实现接口，但只记录日志，不发送消息
     */
    @Override
    public void publishInventoryReserved(String orderId, String merchantId, String userId,
                                       List<InventoryReservedItem> reservedItems,
                                       LocalDateTime reserveTime, LocalDateTime expireTime) {
        log.info("库存预留事件（仅记录）: 订单ID={}, 商户ID={}, 预留商品数={}, 预留时间={}, 过期时间={}", 
                orderId, merchantId, reservedItems.size(), reserveTime, expireTime);
        // 库存预留改为同步处理，不发送到 MQ
        // 在 OrderDomainService.createOrder() 中直接调用 inventoryReservationService.reserveInventory()
    }
}
