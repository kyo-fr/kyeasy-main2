package com.ares.cloud.order.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 删除订单商品命令
 */
@Data
@Schema(description = "删除订单商品命令")
public class DeleteOrderItemCommand {
    
    /**
     * 订单ID
     */
    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderId;
    
    /**
     * 订单商品ID
     */
    @Schema(description = "订单商品ID",requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> orderItemId;
    
    /**
     * 删除原因
     */
    @Schema(description = "删除原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;
}