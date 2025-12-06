package com.ares.cloud.order.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.ares.cloud.order.domain.enums.OrderAction;

/**
 * 订单操作日志实体
 */
@Data
@TableName("order_operation_logs")
public class OrderOperationLogEntity {
    @TableId(type = IdType.ASSIGN_ID)
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
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 操作涉及的金额
     */
    private Long amount;
    
    /**
     * 操作涉及的商品数量
     */
    private Integer itemCount;
    
    /**
     * 操作涉及的订单数量
     */
    private Integer orderCount;
      /**
     * 币种
     */
    private String currency;

    /**
     * 币种精度
     */
    private Integer currencyScale;
}