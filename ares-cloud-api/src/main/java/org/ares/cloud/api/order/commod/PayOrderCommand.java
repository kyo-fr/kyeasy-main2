package org.ares.cloud.api.order.commod;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

/**
 * 支付订单命令
 */
@Data
@Schema(description = "支付订单命令")
public class PayOrderCommand {

    /**
     * 商户ID，用于获取币种和精度信息
     * <p>
     * 商户ID是发票关联的商户标识，系统通过该ID获取对应的币种和精度设置，
     * 确保发票金额计算的准确性。格式通常为字母M开头加上日期和序号。
     */
    @Schema(description = "商户ID", example = "M202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantId;

    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("orderId")
    @NotBlank(message = "{validation.orderId.notBlank}")
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
    @NotNull(message = "{validation.payChannels.notNull}")
    private List<PayCommand> payChannels;

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