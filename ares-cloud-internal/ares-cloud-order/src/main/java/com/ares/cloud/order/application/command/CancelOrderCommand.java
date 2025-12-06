package com.ares.cloud.order.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/4/2 01:46
 */
@Data
@Schema(description = "取消订单")
public class CancelOrderCommand {
    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderId;
    @Schema(description = "取消原因")
    private String reason;
}
