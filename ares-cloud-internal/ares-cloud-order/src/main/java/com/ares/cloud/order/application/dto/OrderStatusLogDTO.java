package com.ares.cloud.order.application.dto;

import lombok.Builder;
import lombok.Data;
import com.ares.cloud.order.domain.enums.OrderStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 订单状态日志DTO
 */
@Schema(description = "订单状态日志DTO")
@Builder
@Data
public class OrderStatusLogDTO {
    @Schema(description = "日志ID")
    private String id;
    
    @Schema(description = "订单ID")
    private String orderId;
    
    @Schema(description = "状态类型")
    private OrderStatusType statusType;
    
    @Schema(description = "原状态值")
    private Integer oldStatus;
    
    @Schema(description = "新状态值")
    private Integer newStatus;
    
    @Schema(description = "操作人ID")
    private String operatorId;
    
    @Schema(description = "操作备注")
    private String remark;
    
    @Schema(description = "操作时间")
    private Long operateTime;
}