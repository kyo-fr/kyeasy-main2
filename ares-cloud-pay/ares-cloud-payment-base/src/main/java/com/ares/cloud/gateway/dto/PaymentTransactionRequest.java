package com.ares.cloud.gateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * @author hugo
 * @version 1.0.0
 * @description 支付交易请求参数
 * @date 2024-03-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付交易请求参数")
public class PaymentTransactionRequest {

    @NotBlank(message = "支付金额不能为空")
    @Schema(description = "支付金额",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("total_amount")
    private String totalAmount;

    @NotBlank(message = "支付令牌不能为空")
    @Schema(description = "支付令牌",requiredMode = Schema.RequiredMode.REQUIRED)
    private String nonce;

    @Schema(description = "订单ID",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("order_id")
    private String orderId;

    @Schema(description = "设备数据",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("device_data")
    private String deviceData;

    @Schema(description = "货币类型")
    private String currency;

    @Schema(description = "描述信息")
    private String description;
} 