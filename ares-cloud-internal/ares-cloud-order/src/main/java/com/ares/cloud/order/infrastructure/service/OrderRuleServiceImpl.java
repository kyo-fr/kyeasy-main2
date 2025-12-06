package com.ares.cloud.order.infrastructure.service;

import lombok.extern.slf4j.Slf4j;
import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.service.OrderRuleService;
import org.springframework.stereotype.Service;

/**
 * @author hugo
 * @version 1.0
 * @description: 订单规则服务实现，可以处理订单操作前后的校验逻辑
 * @date 2025/1/18 10:22
 */
@Service
@Slf4j
public class OrderRuleServiceImpl implements OrderRuleService {
    @Override
    public boolean createBefore(Order order) {
        log.debug("Create order before: {}", order);
        return true;
    }
}
