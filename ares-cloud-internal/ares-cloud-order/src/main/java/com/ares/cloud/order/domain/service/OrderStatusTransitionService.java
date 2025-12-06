package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.enums.OrderAction;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderType;
import com.ares.cloud.order.domain.model.aggregate.Order;

import java.util.Set;

/**
 * 订单结算状态转换服务
 */
public interface OrderStatusTransitionService {
    
    /**
     * 获取目标结算状态
     *
     * @param currentStatus 当前结算状态
     * @param action 触发动作
     * @return 目标结算状态
     */
    OrderStatus getTargetStatus(OrderStatus currentStatus, OrderAction action);
    
    /**
     * 验证结算状态转换是否有效
     *
     * @param order 订单
     * @param action 触发动作
     * @return 是否可以转换
     */
    boolean validateTransition(Order order, OrderAction action);
    
    /**
     * 执行结算状态转换
     *
     * @param order 订单
     * @param action 触发动作
     */
    void transit(Order order, OrderAction action);
    
    /**
     * 获取订单初始结算状态
     *
     * @return 初始结算状态
     */
    OrderStatus getInitialStatus(OrderType orderType);
    
    /**
     * 判断当前结算状态是否可以转换到目标状态
     *
     * @param currentStatus 当前结算状态
     * @param targetStatus 目标结算状态
     * @return 是否可以转换
     */
    boolean canTransitionTo(OrderStatus currentStatus, OrderStatus targetStatus);
    
    /**
     * 获取当前结算状态所有可达的目标状态
     *
     * @param currentStatus 当前结算状态
     * @return 可达目标状态集合
     */
    Set<OrderStatus> getAllowedTransitions(OrderStatus currentStatus);
}