package com.ares.cloud.order.domain.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.exception.RequestBadException;
import com.ares.cloud.order.domain.enums.OrderAction;
import com.ares.cloud.order.domain.enums.OrderError;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderType;
import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.model.valueobject.StatusTransitionRule;
import com.ares.cloud.order.domain.service.OrderStatusTransitionService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderStatusTransitionServiceImpl implements OrderStatusTransitionService {

    // 状态转换规则映射
    private final Map<OrderStatus, Map<OrderAction, StatusTransitionRule>> transitionRules = new HashMap<>();

    // 初始状态
    private static final OrderStatus INITIAL_STATUS = OrderStatus.UNSETTLED;

    @PostConstruct
    public void init() {
        initSettlementRules();
    }

    @Override
    public OrderStatus getInitialStatus(OrderType orderType) {
        if (orderType == OrderType.RESERVATION) {
            return OrderStatus.TO_BE_CONFIRMED;
        }
        return INITIAL_STATUS;
    }

    @Override
    public boolean canTransitionTo(OrderStatus currentStatus, OrderStatus targetStatus) {
        Map<OrderAction, StatusTransitionRule> actionRules = transitionRules.get(currentStatus);
        if (actionRules == null) {
            return false;
        }

        return actionRules.values().stream()
                .anyMatch(rule -> rule.getTargetStatus() == targetStatus);
    }

    @Override
    public Set<OrderStatus> getAllowedTransitions(OrderStatus currentStatus) {
        return Optional.ofNullable(transitionRules.get(currentStatus))
                .map(actionRules -> actionRules.values().stream()
                        .map(StatusTransitionRule::getTargetStatus)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    private void initSettlementRules() {
        // 待确认 -> 未结算
        addTransitionRule(StatusTransitionRule.builder()
                .currentStatus(OrderStatus.TO_BE_CONFIRMED)
                .action(OrderAction.CONFIRM)
                .targetStatus(OrderStatus.TO_BE_CONFIRMED)
                .build());

        // 未结算 -> 部分结算
        addTransitionRule(StatusTransitionRule.builder()
                .currentStatus(OrderStatus.UNSETTLED)
                .action(OrderAction.PARTIAL_PAY)
                .targetStatus(OrderStatus.PARTIALLY_SETTLED)
                .build());

        // 未结算 -> 待就餐
        addTransitionRule(StatusTransitionRule.builder()
                .currentStatus(OrderStatus.UNSETTLED)
                .action(OrderAction.WAITING_DINING)
                .targetStatus(OrderStatus.WAITING_DINING)
                .build());

        // 未结算 -> 就餐中
        addTransitionRule(StatusTransitionRule.builder()
                .currentStatus(OrderStatus.UNSETTLED)
                .action(OrderAction.START_DINING)
                .targetStatus(OrderStatus.DINING_IN_PROGRESS)
                .build());

        // 就餐中 -> 已结算
        addTransitionRule(StatusTransitionRule.builder()
                .currentStatus(OrderStatus.DINING_IN_PROGRESS)
                .action(OrderAction.COMPLETE_DINING)
                .targetStatus(OrderStatus.SETTLED)
                .requirePaymentCheck(true)
                .build());

        // 任意状态 -> 已结算（允许从任何状态直接结算）
        Arrays.stream(OrderStatus.values())
                .filter(status -> status != OrderStatus.SETTLED && status != OrderStatus.CANCELLED)
                .forEach(status -> addTransitionRule(StatusTransitionRule.builder()
                        .currentStatus(status)
                        .action(OrderAction.PAY)
                        .targetStatus(OrderStatus.SETTLED)
                        .requirePaymentCheck(true)
                        .build()));

        // 任意状态 -> 已取消
        Arrays.stream(OrderStatus.values())
                .filter(status -> status != OrderStatus.CANCELLED)
                .forEach(status -> addTransitionRule(StatusTransitionRule.builder()
                        .currentStatus(status)
                        .action(OrderAction.CANCEL)
                        .targetStatus(OrderStatus.CANCELLED)
                        .build()));
    }

    private void addTransitionRule(StatusTransitionRule rule) {
        if (rule.getOrderType() == null) {
            transitionRules.computeIfAbsent(rule.getCurrentStatus(), k -> new HashMap<>())
                    .put(rule.getAction(), rule);
        } else {
            transitionRules
                    .computeIfAbsent(rule.getCurrentStatus(), k -> new HashMap<>())
                    .put(rule.getAction(), rule);
        }
    }

    @Override
    public OrderStatus getTargetStatus(OrderStatus currentStatus, OrderAction action) {
        Map<OrderAction, StatusTransitionRule> actionRules = transitionRules.get(currentStatus);
        if (actionRules == null) {
            return null;
        }
        
        StatusTransitionRule rule = actionRules.get(action);
        if (rule != null) {
            return rule.getTargetStatus();
        }
        
        return null;
    }


    @Override
    public boolean validateTransition(Order order, OrderAction action) {
        StatusTransitionRule rule = findTransitionRule(order.getOrderType(), order.getStatus(), action);
        // 找不到转换规则, 直接返回true
        if (rule == null) {
            return true;
        }

        // 检查支付状态
        if (rule.isRequirePaymentCheck() && !order.isFullyPaid()) {
            return false;
        }

        // 检查配送信息
        if (rule.isRequireDeliveryCheck() && order.getDeliveryInfo() == null) {
            return false;
        }

        return true;
    }

    @Override
    public void transit(Order order, OrderAction action) {
        if (!validateTransition(order, action)) {
            throw new RequestBadException(OrderError.INVALID_STATUS_TRANSITION);
        }

        OrderStatus targetStatus = getTargetStatus( order.getStatus(), action);
        order.setStatus(targetStatus);

        log.info("Order status transited: orderId={}, from={}, to={}, action={}",
                order.getId(), order.getStatus(), targetStatus, action);
    }

    private StatusTransitionRule findTransitionRule(OrderType orderType, OrderStatus currentStatus, OrderAction action) {
        Map<OrderAction, StatusTransitionRule> actionRules = transitionRules.get(currentStatus);
        if (actionRules == null) {
            return null;
        }
        
        StatusTransitionRule rule = actionRules.get(action);
        if (rule != null && (rule.getOrderType() == null || rule.getOrderType() == orderType)) {
            return rule;
        }
        
        return null;
    }
}