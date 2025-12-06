package com.ares.cloud.order.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/24 16:22
 */

@Data
@Builder
@Schema(description = "骑手接单命令")
public class AcceptDeliveryCommand {

    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderId;

    @Schema(description = "骑手ID,如果是商家指派骑手的话才需要")
    private String riderId;
}
