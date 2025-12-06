package com.ares.cloud.order.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "开始第三方配送命令")
public class StartThirdPartyDeliveryCommand {
    
    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("orderId")
    @NotBlank(message = "订单ID不能为空")
    private String orderId;
    
    @Schema(description = "配送公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("deliveryCompany")
    @NotBlank(message = "配送公司不能为空")
    private String deliveryCompany;
    
    @Schema(description = "物流单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("trackingNo")
    @NotBlank(message = "物流单号不能为空")
    private String trackingNo;
    
    @Schema(description = "配送员联系电话")
    @JsonProperty("deliveryPhone")
    private String deliveryPhone;

    @Schema(description = "配送地址")
    private String deliveryAddress;

    @Schema(description = "配送地址纬度")
    private Double deliveryLatitude;

    @Schema(description = "配送地址经度")
    private Double deliveryLongitude;

    @Schema(description = "配送费")
    private BigDecimal deliveryFee;

    @Schema(description = "配送距离")
    private Double deliveryDistance;
} 