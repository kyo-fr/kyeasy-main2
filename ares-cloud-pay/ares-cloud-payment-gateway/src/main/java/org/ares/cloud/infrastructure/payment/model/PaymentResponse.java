package org.ares.cloud.infrastructure.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "支付下单响应")
public class PaymentResponse {
    @Schema(description = "订单号")
    @JsonProperty("orderNo")
    private String orderNo;

    @Schema(description = "支付链接")
    @JsonProperty("paymentUrl")
    private String paymentUrl;

    @Schema(description = "订单状态")
    @JsonProperty("status")
    private String status;

    @Schema(description = "支付渠道订单号")
    @JsonProperty("channelOrderNo")
    private String channelOrderNo;

    @Schema(description = "二维码链接(可选)")
    @JsonProperty("qrCodeUrl")
    private String qrCodeUrl;

    @Schema(description = "APP支付参数(可选)")
    @JsonProperty("appPayParams")
    private String appPayParams;
} 