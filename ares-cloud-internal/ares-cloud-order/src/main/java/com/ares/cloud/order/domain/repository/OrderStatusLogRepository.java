package com.ares.cloud.order.domain.repository;

import com.ares.cloud.order.domain.enums.OrderStatusType;
import com.ares.cloud.order.domain.model.valueobject.OrderStatusLog;

import java.util.List;

/**
 * 订单状态日志仓储接口
 */
public interface OrderStatusLogRepository {
    
    /**
     * 保存订单状态日志
     *
     * @param orderStatusLog 订单状态日志
     */
    void save(OrderStatusLog orderStatusLog);
    
    /**
     * 查询订单状态日志
     *
     * @param orderId 订单ID
     * @param statusType 状态类型，可为null表示查询所有类型
     * @return 订单状态日志列表
     */
    List<OrderStatusLog> findByOrderId(String orderId, OrderStatusType statusType);
}