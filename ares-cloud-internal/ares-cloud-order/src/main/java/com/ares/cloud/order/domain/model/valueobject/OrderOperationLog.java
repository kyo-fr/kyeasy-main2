package com.ares.cloud.order.domain.model.valueobject;

import lombok.Builder;
import lombok.Getter;
import com.ares.cloud.order.domain.enums.OrderAction;
import org.ares.cloud.common.model.Money;

/**
 * 订单操作日志值对象
 * 用于记录订单操作历史
 */
@Getter
@Builder
public class OrderOperationLog {
    /**
     * 日志ID
     */
    private String id;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 操作类型
     */
    private OrderAction action;
    
    /**
     * 操作人ID
     */
    private String operatorId;
    
    /**
     * 操作内容
     */
    private String content;
    
    /**
     * 操作备注
     */
    private String remark;
    
    /**
     * 操作时间
     */
    private Long operateTime;
    
    /**
     * 版本号
     */
    private Integer version;
    
    /**
     * 操作涉及的金额
     */
    private Money amount;
    
    /**
     * 操作涉及的商品数量
     */
    private Integer itemCount;
    
    /**
     * 操作涉及的订单数量
     */
    private Integer orderCount;
    /**
     *  租户id
     */
    private String tenantId;
}