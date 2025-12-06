package com.ares.cloud.order.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.ares.cloud.order.domain.enums.OrderStatusType;

@Data
@TableName("order_status_logs")
public class OrderStatusLogEntity {
    @TableId(type = IdType.ASSIGN_ID)
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
    private Integer oldStatus;
    
    /**
     * 新状态值
     */
    private Integer newStatus;
    
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
    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 删除标记
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}