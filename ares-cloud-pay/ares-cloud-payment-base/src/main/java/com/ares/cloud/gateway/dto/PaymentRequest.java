package com.ares.cloud.gateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author hugo
 * @version 1.0.0
 * @description 支付请求数据模型
 * @date 2024-03-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "支付请求参数")
public class PaymentRequest {

    @NotBlank(message = "订单ID不能为空")
    @Size(max = 64, message = "订单ID长度不能超过64个字符")
    @Schema(description = "订单ID")
    @JsonProperty("order_id")
    private String orderId;

    @NotBlank(message = "支付金额不能为空")
    @Schema(description = "支付金额")
    private String amount;

    @NotBlank(message = "支付方式令牌不能为空")
    @Schema(description = "支付方式令牌")
    @JsonProperty("payment_method_nonce")
    private String paymentMethodNonce;

    @Schema(description = "商户ID")
    @JsonProperty("merchant_id")
    private String merchantId;

    @Schema(description = "货币类型")
    private String currency;

    @Size(max = 255, message = "描述信息长度不能超过255个字符")
    @Schema(description = "描述信息")
    private String description;
} 