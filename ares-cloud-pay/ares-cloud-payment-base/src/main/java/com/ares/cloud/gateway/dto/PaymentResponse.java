package com.ares.cloud.gateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author hugo
 * @version 1.0.0
 * @description 支付响应数据模型
 * @date 2024-03-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付响应结果")
public class PaymentResponse {

    @Schema(description = "交易ID")
    @JsonProperty("transaction_id")
    private String transactionId;

    @Schema(description = "订单ID")
    @JsonProperty("order_id")
    private String orderId;

    @Schema(description = "支付金额")
    private String amount;

    @Schema(description = "支付状态")
    private String status;

    @Schema(description = "错误信息")
    @JsonProperty("error_message")
    private String errorMessage;

    @Schema(description = "原始响应数据")
    @JsonProperty("raw_response")
    private Object rawResponse;
} 