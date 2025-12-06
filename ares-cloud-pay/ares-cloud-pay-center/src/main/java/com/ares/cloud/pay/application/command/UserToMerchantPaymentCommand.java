package com.ares.cloud.pay.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

/**
 * 用户向商户付款命令
 */
@Data
@Schema(description = "用户向商户付款请求")
public class UserToMerchantPaymentCommand {
    
    /**
     * 商户ID
     */
    @NotBlank(message = "商户ID不能为空")
    @Schema(description = "商户ID", example = "merchant_123456")
    private String merchantId;
    
    /**
     * 付款金额
     */
    @NotNull(message = "付款金额不能为空")
    @Schema(description = "付款金额（以元为单位）", example = "99.99", minimum = "0.01")
    private BigDecimal amount;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    @NotBlank(message = "支付区域不能为空")
    @Pattern(regexp = "^(EUR|USD|CNY|CHF|GBP)$", message = "支付区域格式不正确")
    @Schema(description = "支付区域", example = "CNY", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    private String paymentRegion;
    
    /**
     * 付款说明
     */
    @Schema(description = "付款说明", example = "购买商品")
    private String description;
    
    /**
     * 支付密码
     */
    @NotBlank(message = "支付密码不能为空")
    @Schema(description = "支付密码", example = "123456")
    private String paymentPassword;
} 