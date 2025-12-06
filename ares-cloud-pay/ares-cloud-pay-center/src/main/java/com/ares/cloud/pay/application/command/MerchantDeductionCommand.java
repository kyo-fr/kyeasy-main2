package com.ares.cloud.pay.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

/**
 * 商户回收命令
 * 应用层命令，用于商户回收
 */
@Data
@Schema(description = "商户回收请求")
public class MerchantDeductionCommand {
    
    /**
     * 回收金额（以元为单位）
     */
    @NotNull(message = "回收金额不能为空")
    @Schema(description = "回收金额（以元为单位）", example = "500.00", minimum = "0.01")
    private BigDecimal amount;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    @NotBlank(message = "支付区域不能为空")
    @Pattern(regexp = "^(EUR|USD|CNY|CHF|GBP)$", message = "支付区域格式不正确")
    @Schema(description = "支付区域", example = "CNY", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    private String paymentRegion;
    
    /**
     * 回收说明
     */
    @Schema(description = "回收说明", example = "商户回收")
    private String description;
} 