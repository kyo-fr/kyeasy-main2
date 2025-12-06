package com.ares.cloud.order.domain.model.valueobject;

import lombok.Builder;
import lombok.Getter;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderStatusType;

/**
 * 订单状态日志值对象
 * 用于记录订单状态变更历史
 */
@Getter
@Builder
public class OrderStatusLog {
    private String id;
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 状态类型（订单状态/支付状态/配送状态）
     */
    private OrderStatusType statusType;
    
    /**
     * 旧状态值
     */
    private OrderStatus oldStatus;
    
    /**
     * 新状态值
     */
    private OrderStatus newStatus;
    
    /**
     * 操作人ID
     */
    private String operatorId;
    
    /**
     * 操作备注
     */
    private String remark;
    
    /**
     * 变更时间
     */
    private Long operateTime;
    private Integer version;
    private Integer deleted;
}