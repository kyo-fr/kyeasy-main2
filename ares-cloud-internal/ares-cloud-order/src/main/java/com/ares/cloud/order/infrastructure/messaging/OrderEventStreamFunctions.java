package com.ares.cloud.order.infrastructure.messaging;

import com.ares.cloud.order.domain.event.*;
import com.ares.cloud.order.infrastructure.service.OrderEventProcessService;
import com.rabbitmq.client.Channel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

/**
 * 订单事件流处理函数（业务处理消费者组）
 * 
 * 简化架构：只处理核心业务
 * 1. orderCreatedConsumer - 订单创建后库存变动通知（打印日志）
 * 2. orderCancelledConsumer - 订单取消后库存变动通知（打印日志）
 * 3. orderPaidInvoiceConsumer - 订单支付后生成发票
 * 4. orderPaidGiftConsumer - 订单支付后赠送礼物点
 *
 * @author ares-cloud
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderEventStreamFunctions {
    
    private final OrderEventProcessService orderEventProcessService;

    @PostConstruct
    public void init() {
        log.info("==================== OrderEventStreamFunctions 初始化 ====================");
        log.info("【业务处理消费者组】已注册 4 个消费者函数:");
        log.info("  1. orderCreatedConsumer - 订单创建库存变动通知");
        log.info("  2. orderCancelledConsumer - 订单取消库存变动通知");
        log.info("  3. orderPaidInvoiceConsumer - 订单支付发票生成");
        log.info("  4. orderPaidGiftConsumer - 订单支付礼物点赠送");
        log.info("=======================================================================");
    }

    // ==================== 订单创建消费者 ====================

    /**
     * 订单创建消费者 - 库存变动通知
     * 打印库存变动日志
     */
    @Bean
    public Consumer<Message<OrderCreatedEventMessage>> orderCreatedConsumer() {
        return message -> {
            // 从 header 中获取 RabbitMQ 原生确认所需的参数
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                OrderCreatedEventMessage event = message.getPayload();
                
                // 打印库存变动通知日志
                log.info("【库存变动通知】订单创建: 订单ID={}, 商户ID={}, 用户ID={}, 订单类型={}, 金额={}", 
                        event.getOrderId(), event.getMerchantId(), event.getUserId(), 
                        event.getOrderType(), event.getTotalAmount());
                
                // TODO: 后续可以调用库存服务通知库存变动
                
                // 成功确认（使用 RabbitMQ 原生方式）
                manualAck(channel, deliveryTag, true, "订单创建库存通知处理完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("订单创建库存通知失败: {}", e.getMessage(), e);
                // 拒绝消息并发送到死信队列
                manualAck(channel, deliveryTag, false, "订单创建库存通知失败", null);
                // 不要再次抛出异常，避免重复处理
            }
        };
    }

    // ==================== 订单取消消费者 ====================

    /**
     * 订单取消消费者 - 库存变动通知
     * 打印库存恢复日志
     */
    @Bean
    public Consumer<Message<OrderStatusChangedEventMessage>> orderCancelledConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                OrderStatusChangedEventMessage event = message.getPayload();
                
                // 只处理订单取消的状态变更
                if ("CANCELLED".equals(event.getToStatus())) {
                    // 打印库存恢复通知日志
                    log.info("【库存变动通知】订单取消: 订单ID={}, 商户ID={}, 用户ID={}, 状态: {} -> {}, 原因={}", 
                            event.getOrderId(), event.getMerchantId(), event.getUserId(),
                            event.getFromStatus(), event.getToStatus(), event.getReason());
                    
                    // TODO: 后续可以调用库存服务恢复库存
                }
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "订单取消库存通知处理完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("订单取消库存通知失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "订单取消库存通知失败", null);
            }
        };
    }

    // ==================== 订单支付 - 发票消费组 ====================

    /**
     * 订单支付 - 发票消费组
     * 专门处理发票生成
     */
    @Bean
    public Consumer<Message<OrderPaidEventMessage>> orderPaidInvoiceConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                OrderPaidEventMessage event = message.getPayload();
                log.info("【发票消费组】处理订单支付: 订单ID={}, 商户ID={}, 支付金额={}", 
                        event.getOrderId(), event.getMerchantId(), event.getDeductAmount());
                
                // 委托给业务处理服务生成发票
                orderEventProcessService.generateInvoice(event);
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "发票生成完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("发票生成失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "发票生成失败", null);
            }
        };
    }

    // ==================== 订单支付 - 礼物点消费组 ====================

    /**
     * 订单支付 - 礼物点消费组
     * 专门处理礼物点赠送
     */
    @Bean
    public Consumer<Message<OrderPaidEventMessage>> orderPaidGiftConsumer() {
        return message -> {
            Channel channel = message.getHeaders().get(AmqpHeaders.CHANNEL, Channel.class);
            Long deliveryTag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);
            
            try {
                OrderPaidEventMessage event = message.getPayload();
                log.info("【礼物点消费组】处理订单支付: 订单ID={}, 用户ID={}, 支付金额={}", 
                        event.getOrderId(), event.getUserId(), event.getDeductAmount());
                
                // 委托给业务处理服务赠送礼物点
                orderEventProcessService.giveGiftPoints(event);
                
                // 成功确认
                manualAck(channel, deliveryTag, true, "礼物点赠送完成", event.getOrderId());
                
            } catch (Exception e) {
                log.error("礼物点赠送失败: {}", e.getMessage(), e);
                manualAck(channel, deliveryTag, false, "礼物点赠送失败", null);
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
