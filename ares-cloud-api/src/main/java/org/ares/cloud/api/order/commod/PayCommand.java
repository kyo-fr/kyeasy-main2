package org.ares.cloud.api.order.commod;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付命令
 */
@Data
@Schema(description = "支付命令")
public class PayCommand {

    @Schema(description = "支付渠道ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("channelId")
    @NotBlank(message = "{validation.channelId.notBlank}")
    private String channelId;

    @Schema(description = "支付流水号")
    @JsonProperty("tradeNo")
    private String tradeNo;

    @Schema(description = "支付金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("amount")
    @NotNull(message = "{validation.amount.notNull}")
    private BigDecimal amount;
}