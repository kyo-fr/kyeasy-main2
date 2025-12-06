package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.model.aggregate.Order;

/**
 * @author hugo
 * @version 1.0
 * @description: 订单校验
 * @date 2025/1/18 10:14
 */
public interface OrderRuleService {
    /**
     * 创建订单前校验
     * @param order domain
     * @return 校验结果
     */
    boolean createBefore(Order order);
}
