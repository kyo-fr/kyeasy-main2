package com.ares.cloud.order.domain.repository;

import com.ares.cloud.order.domain.model.valueobject.OrderOperationLog;
import org.ares.cloud.common.dto.PageResult;

import java.util.List;

/**
 * 订单操作日志仓储接口
 */
public interface OrderOperationLogRepository {
    
    /**
     * 保存订单操作日志
     *
     * @param orderOperationLog 订单操作日志
     */
    void save(OrderOperationLog orderOperationLog);

    /**
     * 查询订单操作日志
     *
     * @param orderId 订单ID
     * @return 订单操作日志列表
     */
    List<OrderOperationLog> findByOrderId(String orderId);
}