package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderStatusType;
import com.ares.cloud.order.domain.model.valueobject.OrderStatusLog;

import java.util.List;

/**
 * 订单状态日志服务接口
 * 用于记录和查询订单状态变更历史
 */
public interface OrderStatusLogService {
    
    /**
     * 记录状态变更
     *
     * @param orderId 订单ID
     * @param oldStatus 旧状态
     * @param newStatus 新状态
     * @param statusType 状态类型
     * @param operatorId 操作人ID
     * @param remark 备注
     */
    void recordStatusChange(String orderId, OrderStatus oldStatus, OrderStatus newStatus, 
                           OrderStatusType statusType, String operatorId, String remark);
    
    /**
     * 查询订单状态日志
     *
     * @param orderId 订单ID
     * @param statusType 状态类型，可为null表示查询所有类型
     * @return 订单状态日志列表
     */
    List<OrderStatusLog> queryStatusLogs(String orderId, OrderStatusType statusType);
}