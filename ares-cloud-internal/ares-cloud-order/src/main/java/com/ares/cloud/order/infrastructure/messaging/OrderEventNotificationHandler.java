package com.ares.cloud.order.infrastructure.messaging;

import com.ares.cloud.order.domain.event.*;
import com.ares.cloud.order.domain.service.NotificationService;
import com.rabbitmq.client.Channel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.model.Money;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

/**
 * 订单事件通知消费者（独立消费者组）
 * 专门处理订单相关事件的通知发送
 * 
 * 架构设计：
 * - 独立的消费者组，与业务处理解耦
 * - 订阅相同的事件，但只负责通知发送
 * - 失败隔离：通知失败不影响业务处理
 * 
 * 消费者配置：
 * - 每个消费者都有独立的队列和消费者组
 * - 使用 MANUAL ACK 模式
 * - 配置死信队列处理失败消息
 * 
 * 消费者列表：
 * 1. orderCreatedNotificationConsumer - 订单创建通知
 * 2. orderPaidNotificationConsumer - 订单支付通知
 * 3. orderRefundedNotificationConsumer - 订单退款通知
 * 4. orderStatusNotificationConsumer - 订单状态变更通知
 * 5. deliveryStartedNotificationConsumer - 配送开始通知
 * 6. deliveryCompletedNotificationConsumer - 配送完成通知
 * 
 * @author ares-cloud
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderEventNotificationHandler {

    private final NotificationService notificationService;

    @PostConstruct
    public void init() {
        log.info("==================== OrderEventNotificationHandler 初始化 ====================");
        log.info("【通知处理消费者组】已注册 6 个消费者函数:");
        log.info("  1. orderCreatedNotificationConsumer - 订单创建通知");
        log.info("  2. orderPaidNotificationConsumer - 订单支付通知");
        log.info("  3. orderRefundedNotificationConsumer - 订单退款通知");
        log.info("  4. orderStatusNotificationConsumer - 订单状态变更通知");
        log.info("  5. deliveryStartedNotificationConsumer - 配送开始通知");
        log.info("  6. deliveryCompletedNotificationConsumer - 配送完成通知");
        log.info("===========================================================================");
    }

    // ==================== 订单创建事件通知消费者 ====================

    /**
     * 订单创建事件通知消费者
     * 
     * 配置对应关系（application.yml）：
     * - Binding: orderCreatedNotificationConsumer-in-0
     * - Exchange: order-events-exchange (direct)
     * - Binding Key: order.created
     * - Consumer Group: order-notification-group
     * - ACK Mode: MANUAL
     * - DLQ: 自动绑定
     * 
     * 职责：通知商户有新订单
     */
    @Bean
    public Consumer<Message<OrderCreatedEventMessage>> orderCreatedNotificationConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                OrderCreatedEventMessage event = message.getPayload();
                log.debug("【通知消费者】处理订单创建通知: 订单ID={}, 商户ID={}", 
                        event.getOrderId(), event.getMerchantId());
                
                // 通知商户有新订单
                String title = "新订单提醒";
                String content = String.format("您有一笔新订单，订单号：%s，订单类型：%s，金额：%s", 
                        event.getOrderId(), event.getOrderType(), event.getTotalAmount());
                
                notificationService.notifyMerchant(
                        event.getMerchantId(), 
                        event.getOrderId(), 
                        title, 
                        content
                );
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "订单创建通知发送成功", event.getOrderId());
                
            } catch (Exception e) {
                log.error("【通知消费者】订单创建通知发送失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "订单创建通知发送失败", null);
            }
        };
    }

    // ==================== 订单支付事件通知消费者 ====================

    /**
     * 订单支付成功事件通知消费者
     * 
     * 配置对应关系（application.yml）：
     * - Binding: orderPaidNotificationConsumer-in-0
     * - Exchange: order-events-exchange (direct)
     * - Binding Key: order.paid
     * - Consumer Group: order-notification-group
     * - ACK Mode: MANUAL
     * - DLQ: 自动绑定
     * 
     * 职责：通知商户收到款项
     */
    @Bean
    public Consumer<Message<OrderPaidEventMessage>> orderPaidNotificationConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                OrderPaidEventMessage event = message.getPayload();
                
                // 通过支付项计算总金额
                java.math.BigDecimal totalAmount = event.getPayItems().stream()
                        .map(OrderPaidEventMessage.PayItemInfo::getAmount)
                        .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
                
                log.debug("【通知消费者】处理订单支付成功通知: 订单ID={}, 商户ID={}, 支付项数量={}, 支付总金额={}", 
                        event.getOrderId(), event.getMerchantId(),
                        event.getPayItems().size(),
                        totalAmount);
                
                // 通知商户收到款项
                String title = "订单已支付";
                String content = String.format("订单 %s 已完成支付，支付总金额：%s元（共 %d 笔支付）", 
                        event.getOrderId(), 
                        totalAmount.doubleValue(),
                        event.getPayItems() != null ? event.getPayItems().size() : 0);
                
                notificationService.notifyMerchant(
                        event.getMerchantId(), 
                        event.getOrderId(), 
                        title, 
                        content
                );
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "订单支付成功通知发送完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("【通知消费者】订单支付成功通知发送失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "订单支付成功通知发送失败", null);
            }
        };
    }

    // ==================== 订单退款事件通知消费者 ====================

    /**
     * 订单退款事件通知消费者
     * 
     * 配置对应关系（application.yml）：
     * - Binding: orderRefundedNotificationConsumer-in-0
     * - Exchange: order-events-exchange (direct)
     * - Binding Key: order.refunded
     * - Consumer Group: order-notification-group
     * - ACK Mode: MANUAL
     * - DLQ: 自动绑定
     * 
     * 职责：通知商户退款信息
     */
    @Bean
    public Consumer<Message<OrderRefundedEventMessage>> orderRefundedNotificationConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                OrderRefundedEventMessage event = message.getPayload();
                log.debug("【通知消费者】处理订单退款通知: 订单ID={}, 商户ID={}, 退款金额={}", 
                        event.getOrderId(), event.getMerchantId(), event.getRefundAmount());
                
                // 通知商户退款信息
                String title = "订单退款";
                String content = String.format("订单 %s 发起退款，金额：%s元，原因：%s", 
                        event.getOrderId(), event.getRefundAmount(), event.getRefundReason());
                
                notificationService.notifyMerchant(
                        event.getMerchantId(), 
                        event.getOrderId(), 
                        title, 
                        content
                );
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "订单退款通知发送完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("【通知消费者】订单退款通知发送失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "订单退款通知发送失败", null);
            }
        };
    }

    // ==================== 订单状态变更事件通知消费者 ====================

    /**
     * 订单状态变更事件通知消费者
     * 
     * 配置对应关系（application.yml）：
     * - Binding: orderStatusNotificationConsumer-in-0
     * - Exchange: order-events-exchange (direct)
     * - Binding Key: order.status
     * - Consumer Group: order-notification-group
     * - ACK Mode: MANUAL
     * - DLQ: 自动绑定
     * 
     * 职责：根据 OrderStatus 枚举自动生成通知内容，通知商户
     */
    @Bean
    public Consumer<Message<OrderStatusChangedEventMessage>> orderStatusNotificationConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                OrderStatusChangedEventMessage event = message.getPayload();
                log.debug("【通知消费者】处理订单状态变更通知: 订单ID={}, 状态: {} -> {}, 原因={}", 
                        event.getOrderId(), event.getFromStatus(), event.getToStatus(), event.getReason());
                
                // 根据订单状态生成通知内容并发送给商户
                String notificationContent = generateStatusNotification(
                        event.getOrderId(), 
                        event.getToStatus(), 
                        event.getReason()
                );
                
                if (notificationContent != null) {
                    notificationService.notifyMerchant(
                            event.getMerchantId(), 
                            event.getOrderId(), 
                            "订单状态更新", 
                            notificationContent
                    );
                }
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "订单状态变更通知发送完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("【通知消费者】订单状态变更通知发送失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "订单状态变更通知发送失败", null);
            }
        };
    }
    
    /**
     * 根据订单状态生成通知内容
     * 结合 OrderStatus 枚举自动生成对应的通知文案
     * 
     * @param orderId 订单ID
     * @param toStatusStr 目标状态字符串
     * @param reason 变更原因
     * @return 通知内容
     */
    private String generateStatusNotification(String orderId, String toStatusStr, String reason) {
        try {
            // 将字符串状态转换为枚举（支持枚举名称）
            com.ares.cloud.order.domain.enums.OrderStatus status = 
                    com.ares.cloud.order.domain.enums.OrderStatus.valueOf(toStatusStr);
            
            String reasonInfo = (reason != null && !reason.isEmpty()) ? "，原因：" + reason : "";
            
            // 根据不同状态生成不同的通知内容
            return switch (status) {
                case TO_BE_CONFIRMED -> 
                    String.format("订单 %s 待确认%s", orderId, reasonInfo);
                case UNSETTLED -> 
                    String.format("订单 %s 待结算（未结算）%s", orderId, reasonInfo);
                case PARTIALLY_SETTLED -> 
                    String.format("订单 %s 已部分结算，请关注剩余款项%s", orderId, reasonInfo);
                case SETTLED -> 
                    String.format("订单 %s 已完成结算%s", orderId, reasonInfo);
                case WAITING_DINING -> 
                    String.format("订单 %s 等待就餐中，客户即将到店%s", orderId, reasonInfo);
                case DINING_IN_PROGRESS -> 
                    String.format("订单 %s 客户正在就餐中%s", orderId, reasonInfo);
                case CANCELLED -> 
                    String.format("订单 %s 已取消%s", orderId, reasonInfo);
            };
            
        } catch (IllegalArgumentException e) {
            // 如果状态无法解析，使用通用格式
            log.warn("无法解析订单状态: {}, 订单ID: {}", toStatusStr, orderId);
            String reasonInfo = (reason != null && !reason.isEmpty()) ? "，原因：" + reason : "";
            return String.format("订单 %s 状态已更新为：%s%s", orderId, toStatusStr, reasonInfo);
        }
    }

    // ==================== 配送开始事件通知消费者 ====================

    /**
     * 配送开始事件通知消费者
     * 
     * 配置对应关系（application.yml）：
     * - Binding: deliveryStartedNotificationConsumer-in-0
     * - Exchange: delivery-events-exchange (direct)
     * - Binding Key: delivery.started
     * - Consumer Group: order-notification-group
     * - ACK Mode: MANUAL
     * - DLQ: 自动绑定
     * 
     * 职责：通知商户配送已开始
     */
    @Bean
    public Consumer<Message<DeliveryStartedEventMessage>> deliveryStartedNotificationConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                DeliveryStartedEventMessage event = message.getPayload();
                log.debug("【通知消费者】处理配送开始通知: 订单ID={}, 商户ID={}", 
                        event.getOrderId(), event.getMerchantId());
                
                // 通知商户配送已开始
                String title = "订单配送中";
                String content = String.format("订单 %s 已开始配送，配送地址：%s", 
                        event.getOrderId(), event.getDeliveryAddress());
                
                notificationService.notifyMerchant(
                        event.getMerchantId(), 
                        event.getOrderId(), 
                        title, 
                        content
                );
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "配送开始通知发送完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("【通知消费者】配送开始通知发送失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "配送开始通知发送失败", null);
            }
        };
    }

    // ==================== 配送完成事件通知消费者 ====================

    /**
     * 配送完成事件通知消费者
     * 
     * 配置对应关系（application.yml）：
     * - Binding: deliveryCompletedNotificationConsumer-in-0
     * - Exchange: delivery-events-exchange (direct)
     * - Binding Key: delivery.completed
     * - Consumer Group: order-notification-group
     * - ACK Mode: MANUAL
     * - DLQ: 自动绑定
     * 
     * 职责：通知商户订单配送已完成
     */
    @Bean
    public Consumer<Message<DeliveryCompletedEventMessage>> deliveryCompletedNotificationConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                DeliveryCompletedEventMessage event = message.getPayload();
                log.debug("【通知消费者】处理配送完成通知: 订单ID={}, 商户ID={}", 
                        event.getOrderId(), event.getMerchantId());
                
                // 通知商户订单已完成
                String title = "订单已完成";
                String content = String.format("订单 %s 配送已完成，订单状态已更新", event.getOrderId());
                
                notificationService.notifyMerchant(
                        event.getMerchantId(), 
                        event.getOrderId(), 
                        title, 
                        content
                );
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "配送完成通知发送完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("【通知消费者】配送完成通知发送失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "配送完成通知发送失败", null);
            }
        };
    }
    
    // ==================== RabbitMQ 手动确认辅助方法 ====================

    /**
     * 手动确认消息（使用 RabbitMQ 原生 Channel）
     * 
     * @param channel RabbitMQ Channel
     * @param deliveryTag 消息投递标签
     * @param ack true=确认(ACK), false=拒绝(NACK)
     * @param logMessage 日志消息
     * @param orderId 订单ID（可选）
     */
    private void manualAck(Channel channel, Long deliveryTag, boolean ack, String logMessage, String orderId) {
        if (channel == null || deliveryTag == null) {
            log.warn("⚠️ Channel 或 deliveryTag 为 null，无法手动确认消息");
            return;
        }
        
        try {
            if (ack) {
                // 确认消息（从队列中删除）
                channel.basicAck(deliveryTag, false);
                log.info("✅ [ACK] {}{}", logMessage, orderId != null ? ": " + orderId : "");
            } else {
                // 拒绝消息（requeue=false 发送到死信队列）
                channel.basicNack(deliveryTag, false, false);
                log.warn("❌ [NACK] {}{}", logMessage, orderId != null ? ": " + orderId : "");
            }
        } catch (Exception e) {
            log.error("⚠️ 手动确认失败{}: {}", 
                    orderId != null ? "（订单ID=" + orderId + "）" : "", 
                    e.getMessage(), e);
        }
    }
}
