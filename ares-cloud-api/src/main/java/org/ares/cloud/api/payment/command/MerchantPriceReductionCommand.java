package org.ares.cloud.api.payment.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 商户减价命令
 * 商户结算时给用户的具体金额减价，手续费5%由用户承担
 */
@Data
@Schema(description = "商户减价命令")
public class MerchantPriceReductionCommand {
    
    /**
     * 商户ID
     */
    @NotBlank(message = "商户ID不能为空")
    @Schema(description = "商户ID", example = "MERCHANT_001")
    @JsonProperty("merchantId")
    private String merchantId;
    
    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "USER_001")
    @JsonProperty("userId")
    private String userId;
    
    /**
     * 减价金额（以元为单位，支持小数）
     */
    @NotNull(message = "减价金额不能为空")
    @Positive(message = "减价金额必须大于0")
    @Schema(description = "减价金额（以元为单位，支持小数）", example = "5.00")
    @JsonProperty("amount")
    private BigDecimal amount;
    
    /**
     * 支付区域
     */
    @NotBlank(message = "支付区域不能为空")
    @Schema(description = "支付区域", example = "EUR", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    @JsonProperty("paymentRegion")
    private String paymentRegion;
    
    /**
     * 减价说明
     */
    @Schema(description = "减价说明", example = "结算减价5元")
    @JsonProperty("description")
    private String description;
    
    /**
     * 商户支付密码
     */
    @Schema(description = "商户支付密码")
    @JsonProperty("paymentPassword")
    private String paymentPassword;
}

