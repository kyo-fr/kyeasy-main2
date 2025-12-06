package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * 转账命令
 * 封装用户间转账所需的所有参数，用于在系统中执行用户间的资金转账
 * <p>
 * 该命令包含转账双方信息、转账金额和支付区域，用于执行安全的资金转账
 * <p>
 * 使用场景：
 * 1. 用户间转账
 * 2. 朋友间转账
 * 3. 跨区域转账
 */
@Data
@Schema(description = "转账命令对象", title = "转账命令")
public class TransferCommand {
    
    /**
     * 当前用户ID（付款方）
     * <p>
     * 执行转账操作的当前用户ID，用于验证用户身份和权限。
     * 系统会验证该用户是否有足够的余额进行转账。
     */
    @Schema(description = "当前用户ID（付款方）", example = "user_123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fromUserId;
    
    /**
     * 收款方国家代码
     * <p>
     * 收款方的国家代码，用于定位收款方用户。
     * 格式为+号开头加上1-4位数字，如+86表示中国。
     */
    @Schema(description = "收款方国家代码", example = "+86", requiredMode = Schema.RequiredMode.REQUIRED)
    private String toCountryCode;
    
    /**
     * 收款方手机号
     * <p>
     * 收款方的手机号码，用于定位收款方用户。
     * 格式为11位数字，如13800138000。
     */
    @Schema(description = "收款方手机号", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    private String toPhone;
    
    /**
     * 转账金额（以元为单位）
     * <p>
     * 要转账的金额，使用BigDecimal类型确保金额计算的精确性。
     * 金额必须大于0，且不能超过付款方的可用余额。
     */
    @Schema(description = "转账金额（以元为单位）", example = "100.50", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     * <p>
     * 转账的货币区域，决定转账金额的币种和汇率计算。
     * 支持的区域包括：EUR（欧元）、USD（美元）、CNY（人民币）、CHF（瑞士法郎）、GBP（英镑）。
     */
    @Schema(description = "支付区域", example = "CNY", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String paymentRegion;
    
    /**
     * 转账说明
     * <p>
     * 转账的说明信息，用于记录转账的目的和用途。
     * 非必填项，但建议填写，便于后续的转账记录查询。
     */
    @Schema(description = "转账说明", example = "转账给朋友")
    private String description;
} 