package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 扫码支付命令
 * 封装扫码支付所需的所有参数，用于在系统中执行扫码支付操作
 * <p>
 * 该命令包含二维码内容、用户信息和支付密码，用于验证和执行扫码支付
 * <p>
 * 使用场景：
 * 1. 移动端扫码支付
 * 2. 二维码支付确认
 * 3. 线下扫码支付
 */
@Data
@Schema(description = "扫码支付命令对象", title = "扫码支付命令")
public class ScanCodePaymentCommand {
    
    /**
     * 二维码内容
     * <p>
     * 支付二维码的内容，包含支付订单的关键信息。
     * 系统会解析二维码内容，获取订单信息和支付票据。
     */
    @Schema(description = "二维码内容", example = "https://pay.example.com/qrcode?ticket=abc123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String qrCodeContent;
    
    /**
     * 用户ID
     * <p>
     * 执行扫码支付操作的用户ID，用于验证用户身份和权限。
     * 系统会验证该用户是否有权限执行此支付操作。
     */
    @Schema(description = "用户ID", example = "user_123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    
    /**
     * 支付密码
     * <p>
     * 用户的支付密码，用于验证支付操作的安全性。
     * 密码验证通过后才能执行实际的资金转账操作。
     */
    @Schema(description = "支付密码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paymentPassword;
} 