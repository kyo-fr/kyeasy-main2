package com.ares.cloud.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 订单模块 MQ 配置
 * 导入 MQ 自动配置并定义订单模块的 MQ 主题
 *
 * @author ares-cloud
 */
@Slf4j
public class OrderMqConfig {

    /**
     * 订单事件主题
     */
    public static final String ORDER_EVENTS_TOPIC = "order-events-topic";

    /**
     * 库存事件主题
     */
    public static final String INVENTORY_EVENTS_TOPIC = "inventory-events-topic";

    /**
     * 配送事件主题
     */
    public static final String DELIVERY_EVENTS_TOPIC = "delivery-events-topic";

    /**
     * 订单结算主题
     */
    public static final String ORDER_SETTLEMENT_TOPIC = "order-settlement-topic";

    /**
     * 订单事件消费者组
     */
    public static final String ORDER_EVENT_CONSUMER_GROUP = "order-event-consumer-group";

    /**
     * 库存事件消费者组
     */
    public static final String INVENTORY_EVENT_CONSUMER_GROUP = "inventory-event-consumer-group";

    /**
     * 配送事件消费者组
     */
    public static final String DELIVERY_EVENT_CONSUMER_GROUP = "delivery-event-consumer-group";

    /**
     * 订单结算消费者组
     */
    public static final String ORDER_SETTLEMENT_CONSUMER_GROUP = "order-settlement-consumer-group";

    static {
        log.info("订单模块 MQ 配置初始化完成");
        log.info("订单事件主题: {}", ORDER_EVENTS_TOPIC);
        log.info("库存事件主题: {}", INVENTORY_EVENTS_TOPIC);
        log.info("配送事件主题: {}", DELIVERY_EVENTS_TOPIC);
        log.info("订单结算主题: {}", ORDER_SETTLEMENT_TOPIC);
    }
}
