package com.ares.cloud.pay.domain.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;
import lombok.Getter;

/**
 * 支付错误码
 */
@Getter
public enum PaymentError implements BaseErrorInfoInterface {
    // 账户相关错误
    ACCOUNT_NOT_FOUND("error.account_not_found", "账户不存在"),
    ACCOUNT_ALREADY_EXISTS("error.account_already_exists", "账户已存在"),
    INVALID_ACCOUNT_STATUS("error.invalid_account_status", "无效的账户状态"),
    ACCOUNT_FROZEN("error.account_frozen", "账户已冻结"),
    ACCOUNT_CLOSED("error.account_closed", "账户已关闭"),
    INVALID_ACCOUNT_TYPE("error.invalid_account_type", "无效的账户类型"),
    ACCOUNT_BALANCE_INSUFFICIENT("error.account_balance_insufficient", "账户余额不足"),
    ACCOUNT_FROZEN_AMOUNT_INSUFFICIENT("error.account_frozen_amount_insufficient", "账户冻结金额不足"),
    INVALID_PASSWORD("error.invalid_password", "密码错误"),
    PASSWORD_TOO_WEAK("error.password_too_weak", "密码强度不足"),
    PASSWORD_MISMATCH("error.password_mismatch", "密码不一致"),
    PAYMENT_PASSWORD_REQUIRED("error.payment_password_required", "支付密码不能为空"),
    INVALID_PAYMENT_PASSWORD("error.invalid_payment_password", "支付密码错误"),

    // 商户相关错误
    MERCHANT_NOT_FOUND("error.merchant_not_found", "商户不存在"),
    MERCHANT_ALREADY_EXISTS("error.merchant_already_exists", "商户已存在"),
    INVALID_MERCHANT_STATUS("error.invalid_merchant_status", "无效的商户状态"),
    MERCHANT_FROZEN("error.merchant_frozen", "商户已冻结"),
    MERCHANT_CLOSED("error.merchant_closed", "商户已关闭"),
    INVALID_MERCHANT_TYPE("error.invalid_merchant_type", "无效的商户类型"),
    MERCHANT_BALANCE_INSUFFICIENT("error.merchant_balance_insufficient", "商户余额不足"),
    MERCHANT_FROZEN_AMOUNT_INSUFFICIENT("error.merchant_frozen_amount_insufficient", "商户冻结金额不足"),
    INVALID_MERCHANT_REGION("error.invalid_merchant_region", "无效的商户支付区域"),
    INVALID_MERCHANT_KEY("error.invalid_merchant_key", "无效的商户密钥"),
    ENCRYPTION_FAILED("error.encryption_failed", "加密失败"),
    DECRYPTION_FAILED("error.decryption_failed", "解密失败"),
    INVALID_SIGNATURE("error.invalid_signature", "签名验证失败"),

    // 钱包相关错误
    WALLET_NOT_FOUND("error.wallet_not_found", "钱包不存在"),
    WALLET_ALREADY_EXISTS("error.wallet_already_exists", "钱包已存在"),
    INVALID_WALLET_STATUS("error.invalid_wallet_status", "无效的钱包状态"),
    WALLET_FROZEN("error.wallet_frozen", "钱包已冻结"),
    WALLET_CLOSED("error.wallet_closed", "钱包已关闭"),
    WALLET_BALANCE_INSUFFICIENT("error.wallet_balance_insufficient", "钱包余额不足"),
    WALLET_FROZEN_AMOUNT_INSUFFICIENT("error.wallet_frozen_amount_insufficient", "钱包冻结金额不足"),
    INVALID_WALLET_REGION("error.invalid_wallet_region", "无效的钱包支付区域"),

    // 支付订单相关错误
    PAYMENT_ORDER_NOT_FOUND("error.payment_order_not_found", "支付订单不存在"),
    PAYMENT_ORDER_ALREADY_EXISTS("error.payment_order_already_exists", "支付订单已存在"),
    INVALID_PAYMENT_ORDER_STATUS("error.invalid_payment_order_status", "无效的支付订单状态"),
    PAYMENT_ORDER_EXPIRED("error.payment_order_expired", "支付订单已过期"),
    PAYMENT_ORDER_CLOSED("error.payment_order_closed", "支付订单已关闭"),
    PAYMENT_ORDER_PAID("error.payment_order_paid", "支付订单已支付"),
    PAYMENT_ORDER_REFUNDED("error.payment_order_refunded", "支付订单已退款"),
    PAYMENT_ORDER_FAILED("error.payment_order_failed", "支付订单失败"),
    INVALID_PAYMENT_AMOUNT("error.invalid_payment_amount", "无效的支付金额"),
    PAYMENT_AMOUNT_EXCEEDED("error.payment_amount_exceeded", "支付金额超出限制"),
    PAYMENT_AMOUNT_MISMATCH("error.payment_amount_mismatch", "支付金额不匹配"),

    // 支付方式相关错误
    INVALID_PAYMENT_METHOD("error.invalid_payment_method", "无效的支付方式"),
    PAYMENT_METHOD_NOT_SUPPORTED("error.payment_method_not_supported", "不支持的支付方式"),
    PAYMENT_METHOD_DISABLED("error.payment_method_disabled", "支付方式已禁用"),

    // 支付渠道相关错误
    INVALID_PAYMENT_CHANNEL("error.invalid_payment_channel", "无效的支付渠道"),
    PAYMENT_CHANNEL_NOT_SUPPORTED("error.payment_channel_not_supported", "不支持的支付渠道"),
    PAYMENT_CHANNEL_DISABLED("error.payment_channel_disabled", "支付渠道已禁用"),
    PAYMENT_CHANNEL_ERROR("error.payment_channel_error", "支付渠道错误"),

    // 支付凭证相关错误
    PAYMENT_VOUCHER_NOT_FOUND("error.payment_voucher_not_found", "支付凭证不存在"),
    INVALID_PAYMENT_VOUCHER("error.invalid_payment_voucher", "无效的支付凭证"),
    PAYMENT_VOUCHER_EXPIRED("error.payment_voucher_expired", "支付凭证已过期"),

    // 支付通知相关错误
    PAYMENT_NOTIFY_FAILED("error.payment_notify_failed", "支付通知失败"),
    PAYMENT_NOTIFY_TIMEOUT("error.payment_notify_timeout", "支付通知超时"),
    INVALID_PAYMENT_NOTIFY("error.invalid_payment_notify", "无效的支付通知"),

    // 退款相关错误
    REFUND_NOT_ALLOWED("error.refund_not_allowed", "不允许退款"),
    REFUND_AMOUNT_EXCEEDED("error.refund_amount_exceeded", "退款金额超出限制"),
    REFUND_AMOUNT_INVALID("error.refund_amount_invalid", "无效的退款金额"),
    REFUND_REASON_REQUIRED("error.refund_reason_required", "退款原因不能为空"),
    REFUND_FAILED("error.refund_failed", "退款失败"),

    // 交易相关错误
    TRANSACTION_NOT_FOUND("error.transaction_not_found", "交易不存在"),
    TRANSACTION_FAILED("error.transaction_failed", "交易失败"),
    TRANSACTION_TIMEOUT("error.transaction_timeout", "交易超时"),
    TRANSACTION_DUPLICATE("error.transaction_duplicate", "重复交易"),
    INVALID_TRANSACTION_TYPE("error.invalid_transaction_type", "无效的交易类型"),
    INVALID_TRANSACTION_STATUS("error.invalid_transaction_status", "无效的交易状态"),

    // 系统相关错误
    SYSTEM_ERROR("error.system_error", "系统错误"),
    NETWORK_ERROR("error.network_error", "网络错误"),
    DATABASE_ERROR("error.database_error", "数据库错误"),
    CONFIGURATION_ERROR("error.configuration_error", "配置错误"),
    VALIDATION_ERROR("error.validation_error", "验证错误"),

    // 参数相关错误
    INVALID_PARAMETER("error.invalid_parameter", "无效的参数"),
    MISSING_PARAMETER("error.missing_parameter", "缺少必要参数"),
    PARAMETER_TYPE_MISMATCH("error.parameter_type_mismatch", "参数类型不匹配"),
    PARAMETER_FORMAT_INVALID("error.parameter_format_invalid", "参数格式无效"),

    // 权限相关错误
    UNAUTHORIZED("error.unauthorized", "未授权"),
    FORBIDDEN("error.forbidden", "禁止访问"),
    INVALID_TOKEN("error.invalid_token", "无效的令牌"),
    TOKEN_EXPIRED("error.token_expired", "令牌已过期"),

    // 业务相关错误
    BUSINESS_RULE_VIOLATION("error.business_rule_violation", "违反业务规则"),
    OPERATION_NOT_ALLOWED("error.operation_not_allowed", "操作不允许"),
    RESOURCE_NOT_FOUND("error.resource_not_found", "资源不存在"),
    RESOURCE_ALREADY_EXISTS("error.resource_already_exists", "资源已存在"),
    
    // 手续费配置相关错误
    FEE_CONFIG_NOT_FOUND("error.fee_config_not_found", "手续费配置不存在"),
    FEE_CONFIG_ALREADY_EXISTS("error.fee_config_already_exists", "手续费配置已存在"),
    INVALID_FEE_RATE("error.invalid_fee_rate", "无效的手续费比例"),
    INVALID_FEE_AMOUNT("error.invalid_fee_amount", "无效的手续费金额");

    private final Integer code;
    private final String messageKey;
    private final String message;

    PaymentError(String messageKey, String message) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = messageKey;
        this.message = message;
    }

    PaymentError(Integer code, String messageKey, String message) {
        this.code = code;
        this.messageKey = messageKey;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}