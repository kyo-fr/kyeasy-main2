package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.enums.OrderAction;
import org.ares.cloud.common.model.Money;
import com.ares.cloud.order.domain.model.valueobject.OrderOperationLog;
import org.ares.cloud.common.dto.PageResult;

import java.util.List;

/**
 * 订单操作日志服务接口
 * 用于记录和查询订单操作历史
 */
public interface OrderOperationLogService {
    
    /**
     * 记录订单操作
     *
     * @param orderId 订单ID
     * @param action 操作类型
     * @param operatorId 操作人ID
     * @param content 操作内容
     * @param remark 备注
     * @param amount 操作涉及的金额
     * @param itemCount 操作涉及的商品数量
     * @param orderCount 操作涉及的订单数量
     * @param tenantId 租户ID
     */
    void recordOperation(String orderId, OrderAction action, String operatorId,
                         String content, String remark,Money amount,Integer itemCount,Integer orderCount,String tenantId);

    /**
     * 查询订单操作日志
     *
     * @param orderId 订单ID
     * @return 订单操作日志列表
     */
    List<OrderOperationLog> queryOperationLogs(String orderId);
}