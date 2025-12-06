package com.ares.cloud.order.application.dto;

import lombok.Builder;
import lombok.Data;
import com.ares.cloud.order.domain.enums.OrderAction;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * 订单操作日志DTO
 */
@Schema(description = "订单操作日志DTO")
@Builder
@Data
public class OrderOperationLogDTO {
    @Schema(description = "日志ID")
    private String id;
    
    @Schema(description = "订单ID")
    private String orderId;
    
    @Schema(description = "操作类型：13 取消订单；19 删除商品")
    private OrderAction action;
    
    @Schema(description = "操作类型描述")
    private String actionDesc;
    
    @Schema(description = "操作人ID")
    private String operatorId;
    
    @Schema(description = "操作内容")
    private String content;
    
    @Schema(description = "操作备注")
    private String remark;
    
    @Schema(description = "操作时间")
    private Long operateTime;
    
    /**
     * 操作涉及的金额
     */
    @Schema(description = "操作涉及的金额")
    private BigDecimal amount;
    
    /**
     * 操作涉及的商品数量
     */
    @Schema(description = "操作涉及的商品数量")
    private Integer itemCount;
    
    /**
     * 操作涉及的订单数量
     */
    @Schema(description = "操作涉及的订单数量")
    private Integer orderCount;
}