package com.ares.cloud.order.application.query;

import com.ares.cloud.order.application.dto.OrderDTO;
import com.ares.cloud.order.application.dto.OrderOperationLogDTO;
import com.ares.cloud.order.application.dto.OrderStatusLogDTO;
import com.ares.cloud.order.application.query.OrderQuery;
import com.ares.cloud.order.application.query.DeliveryOrderQuery;
import com.ares.cloud.order.application.query.OrderOperationLogQuery;
import com.ares.cloud.order.application.query.CommonOrdersQuery;

import java.util.List;

/**
 * 订单查询服务
 * CQRS模式中的查询端，负责订单相关的查询操作
 * 与命令端（OrderDomainService）分离
 *
 * @author ares-cloud
 */
public interface OrderQueryService {

    /**
     * 根据订单ID查询订单详情
     *
     * @param orderId 订单ID
     * @return 订单详情
     */
    OrderDTO findOrderById(String orderId);

    /**
     * 根据用户ID查询订单列表
     *
     * @param userId 用户ID
     * @param query 查询条件
     * @return 订单列表
     */
    List<OrderDTO> findOrdersByUserId(String userId, OrderQuery query);

    /**
     * 根据商户ID查询订单列表
     *
     * @param merchantId 商户ID
     * @param query 查询条件
     * @return 订单列表
     */
    List<OrderDTO> findOrdersByMerchantId(String merchantId, OrderQuery query);

    /**
     * 查询配送订单
     *
     * @param query 配送订单查询条件
     * @return 配送订单列表
     */
    List<OrderDTO> findDeliveryOrders(DeliveryOrderQuery query);

    /**
     * 查询订单操作日志
     *
     * @param query 操作日志查询条件
     * @return 操作日志列表
     */
    List<OrderOperationLogDTO> findOrderOperationLogs(OrderOperationLogQuery query);

    /**
     * 查询订单状态日志
     *
     * @param orderId 订单ID
     * @return 状态日志列表
     */
    List<OrderStatusLogDTO> findOrderStatusLogs(String orderId);

    /**
     * 查询通用订单列表
     *
     * @param query 通用查询条件
     * @return 订单列表
     */
    List<OrderDTO> findCommonOrders(CommonOrdersQuery query);

    /**
     * 统计订单数量
     *
     * @param query 查询条件
     * @return 订单数量
     */
    long countOrders(OrderQuery query);

    /**
     * 统计用户订单数量
     *
     * @param userId 用户ID
     * @param query 查询条件
     * @return 订单数量
     */
    long countOrdersByUserId(String userId, OrderQuery query);

    /**
     * 统计商户订单数量
     *
     * @param merchantId 商户ID
     * @param query 查询条件
     * @return 订单数量
     */
    long countOrdersByMerchantId(String merchantId, OrderQuery query);
}
