package com.ares.cloud.pay.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

/**
 * 用户间转账命令
 */
@Data
@Schema(description = "用户间转账请求")
public class UserTransferCommand {
    /**
     * 收款方国家代码
     */
    @NotBlank(message = "收款方国家代码不能为空")
    @Pattern(regexp = "^\\+[0-9]{1,4}$", message = "国家代码格式不正确")
    @Schema(description = "收款方国家代码", example = "+86", pattern = "^\\+[0-9]{1,4}$")
    private String countryCode;

    /**
     * 收款方手机号
     */
    @NotBlank(message = "收款方手机号不能为空")
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号格式不正确")
    @Schema(description = "收款方手机号", example = "13800138000", pattern = "^[0-9]{11}$")
    private String phone;
    
    /**
     * 转账金额（以元为单位）
     */
    @NotNull(message = "转账金额不能为空")
    @Schema(description = "转账金额（以元为单位）", example = "100.50", minimum = "0.01")
    private BigDecimal amount;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    @NotBlank(message = "支付区域不能为空")
    @Pattern(regexp = "^(EUR|USD|CNY|CHF|GBP)$", message = "支付区域格式不正确")
    @Schema(description = "支付区域", example = "CNY", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    private String paymentRegion;
    
    /**
     * 转账说明
     */
    @Schema(description = "转账说明", example = "转账给朋友")
    private String description;
    
    /**
     * 支付密码
     */
    @NotBlank(message = "支付密码不能为空")
    @Schema(description = "支付密码", example = "123456")
    private String paymentPassword;
} 