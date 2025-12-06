package com.ares.cloud.order.domain.repository;

import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * 订单仓储接口
 */
/**
 * 订单仓库接口，用于执行与订单相关的数据库操作
 */
public interface OrderRepository {

    /**
     * 保存一个订单
     *
     * @param order 要保存的订单对象
     */
    void save(Order order);

    /**
     * 通过订单ID查找订单
     *
     * @param id 订单的唯一标识符
     * @return 可能包含订单的Optional对象，如果找不到则为Optional.empty()
     */
    Optional<Order> findById(String id);

    /**
     * 根据订单状态查询订单
     *
     * @param status 订单的状态
     * @return 符合给定状态的订单列表
     */
    List<Order> findByStatus(OrderStatus status);
}
