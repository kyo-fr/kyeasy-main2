package com.ares.cloud.pay.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * 测试支付命令
 * 用于测试下单功能，使用平台商户信息创建支付订单
 */
@Data
@Schema(description = "测试支付命令")
public class TestPaymentCommand {
    
    /**
     * 支付金额（以元为单位）
     */
    @NotNull(message = "支付金额不能为空")
    @Positive(message = "支付金额必须大于0")
    @Schema(description = "支付金额（以元为单位）", example = "99.99", minimum = "0.01")
    private BigDecimal amount;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    @Schema(description = "支付区域", example = "CNY", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    private String paymentRegion = "CNY";
    
    /**
     * 订单标题
     */
    @Schema(description = "订单标题", example = "测试商品购买")
    private String subject = "测试商品购买";
    
    /**
     * 订单描述
     */
    @Schema(description = "订单描述", example = "这是一个测试订单")
    private String description = "这是一个测试订单";
    
    /**
     * 支付完成后的跳转地址
     */
    @Schema(description = "支付完成后的跳转地址", example = "https://example.com/success")
    private String returnUrl;
    
    /**
     * 支付结果通知地址
     */
    @Schema(description = "支付结果通知地址", example = "https://example.com/notify")
    private String notifyUrl;
} 