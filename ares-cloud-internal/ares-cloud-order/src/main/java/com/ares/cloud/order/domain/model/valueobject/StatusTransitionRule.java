package com.ares.cloud.order.domain.model.valueobject;

import lombok.Builder;
import lombok.Value;
import com.ares.cloud.order.domain.enums.OrderAction;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderType;

/**
 * 状态转换规则值对象
 */
@Value
@Builder
public class StatusTransitionRule {
    /**
     * 订单类型
     */
    OrderType orderType;
    
    /**
     * 当前状态
     */
    OrderStatus currentStatus;
    
    /**
     * 触发动作
     */
    OrderAction action;
    
    /**
     * 目标状态
     */
    OrderStatus targetStatus;
    
    /**
     * 是否需要验证支付状态
     */
    boolean requirePaymentCheck;
    
    /**
     * 是否需要验证配送信息
     */
    boolean requireDeliveryCheck;
} 