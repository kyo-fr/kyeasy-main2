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
 * @description 支付回调结果数据模型
 * @date 2024-03-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付回调结果")
public class PaymentCallbackResult {

    @Schema(description = "回调处理消息")
    private String message;

    @Schema(description = "订单ID")
    @JsonProperty("order_id")
    private String orderId;

    @Schema(description = "交易ID")
    @JsonProperty("transaction_id")
    private String transactionId;

    @Schema(description = "支付状态")
    private String status;

    @Schema(description = "支付金额")
    private String amount;
} 