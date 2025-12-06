package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 支付执行命令
 * 封装支付执行所需的所有参数，用于在系统中执行具体的支付操作
 * <p>
 * 该命令包含支付票据、用户信息和支付密码，用于验证和执行支付
 * <p>
 * 使用场景：
 * 1. 用户确认支付
 * 2. 支付页面提交
 * 3. 扫码支付确认
 */
@Data
@Schema(description = "支付执行命令对象", title = "支付执行命令")
public class ExecutePaymentCommand {
    
    /**
     * 支付票据
     * <p>
     * 支付操作的唯一标识票据，用于验证支付请求的合法性。
     * 票据包含订单信息、用户信息等关键数据，确保支付安全。
     */
    @Schema(description = "支付票据", example = "ticket_abc123def456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paymentTicket;
    
    /**
     * 用户ID
     * <p>
     * 执行支付操作的用户ID，用于验证用户身份和权限。
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