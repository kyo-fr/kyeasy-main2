package org.ares.cloud.api.payment.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 通用转账命令
 */
@Data
@Schema(description = "通用转账命令")
public class GenericTransferCommand {
    
    /**
     * 转出账户ID
     */
    @NotBlank(message = "转出账户ID不能为空")
    @Schema(description = "转出账户ID", example = "user_123456")
    @JsonProperty("fromAccountId")
    private String fromAccountId;
    
    /**
     * 转入账户ID
     */
    @NotBlank(message = "转入账户ID不能为空")
    @Schema(description = "转入账户ID", example = "user_789012")
    @JsonProperty("toAccountId")
    private String toAccountId;
    
    /**
     * 转账金额（以元为单位，支持小数）
     */
    @NotNull(message = "转账金额不能为空")
    @Positive(message = "转账金额必须大于0")
    @Schema(description = "转账金额（以元为单位，支持小数）", example = "100.50")
    @JsonProperty("amount")
    private BigDecimal amount;
    
    /**
     * 支付区域
     */
    @NotBlank(message = "支付区域不能为空")
    @Schema(description = "支付区域", example = "CNY", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    @JsonProperty("paymentRegion")
    private String paymentRegion;
    
    /**
     * 交易类型
     */
    @NotBlank(message = "交易类型不能为空")
    @Schema(description = "交易类型", example = "USER_TRANSFER", 
            allowableValues = {
                // 用户类型
                "SHOPPING", "ACTIVITY_REBATE", "USER_TRANSFER",
                "MERCHANT_DISCOUNT_REDUCTION", "MERCHANT_PRICE_REDUCTION",
                // 商户类型
                "MERCHANT_PURCHASE", "MERCHANT_SELL", "MERCHANT_ACTIVITY_GIFT",
                "MERCHANT_DISCOUNT_GRANT", "MERCHANT_PRICE_GRANT", "MERCHANT_COLLECTION",
                // 系统类型
                "SYSTEM_SALE", "SYSTEM_RECYCLE",
                // 兼容旧代码
                "USER_TO_USER", "USER_TO_MERCHANT", "MERCHANT_TO_USER",
                "PLATFORM_RECHARGE", "PLATFORM_DEDUCTION", "SYSTEM_RECHARGE", 
                "SYSTEM_DEDUCTION", "MERCHANT_TO_GIFT", "REFUND", "FEE"
            })
    @JsonProperty("transactionType")
    private String transactionType;
    
    /**
     * 交易描述
     */
    @Schema(description = "交易描述", example = "用户转账")
    @JsonProperty("description")
    private String description;
    
    /**
     * 支付密码
     */
    @Schema(description = "支付密码", example = "123456")
    @JsonProperty("paymentPassword")
    private String paymentPassword;
    
    /**
     * 是否需要验证支付密码
     */
    @Schema(description = "是否需要验证支付密码", example = "true")
    @JsonProperty("requirePaymentPassword")
    private Boolean requirePaymentPassword = true;
    
    /**
     * 手续费承担方（FROM: 转出方承担, TO: 转入方承担）
     */
    @Schema(description = "手续费承担方", example = "TO", allowableValues = {"FROM", "TO"})
    @JsonProperty("feeBearer")
    private String feeBearer = "TO";
}
