package com.ares.cloud.order.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ares.cloud.api.order.commod.PayCommand;

import java.math.BigDecimal;
import java.util.List;

/**
 * 部分支付命令
 */
@Data
@Schema(description = "部分支付命令")
public class PartialPayOrderCommand {

    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("orderId")
    @NotBlank(message = "订单ID不能为空")
    private String orderId;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    @NotNull(message = "validation.user.countryCode")
    private String countryCode;

    @Schema(description = "手机号")
    @JsonProperty("userPhone")
    @NotNull(message = "validation.user.userPhone")
    private String userPhone;

    @Schema(description = "支付渠道", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("payChannels")
    @NotNull(message = "支付渠道为空")
    private List<PayCommand> payChannels;

    @Schema(description = "待支付商品ID列表(按商品支付时使用)")
    @JsonProperty("orderItemIds")
    private List<String> orderItemIds;

    @Schema(description = "减免金额")
    @JsonProperty("orderDeductAmount")
    private BigDecimal orderDeductAmount;

    @Schema(description = "减免原因，可选暂不记录")
    @JsonProperty("orderDeductReason")
    private String orderDeductReason;

    @Schema(description = "赠送礼物点")
    @JsonProperty("giftPoints")
    private BigDecimal giftPoints;
}
