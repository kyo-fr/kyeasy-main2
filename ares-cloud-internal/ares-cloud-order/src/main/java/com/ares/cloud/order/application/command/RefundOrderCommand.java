package com.ares.cloud.order.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * 退款命令
 */
@Data
@Schema(description = "退款命令")
public class RefundOrderCommand {

    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("orderId")
    @NotBlank(message = "订单ID不能为空")
    private String orderId;

    @Schema(description = "退款原因", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("reason")
    @NotBlank(message = "退款原因不能为空")
    private String reason;
} 