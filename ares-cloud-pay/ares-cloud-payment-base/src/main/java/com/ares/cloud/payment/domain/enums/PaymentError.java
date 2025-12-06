package com.ares.cloud.payment.domain.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;
import lombok.Getter;

/**
 * 支付错误码
 */
@Getter
public enum PaymentError implements BaseErrorInfoInterface {
    // 通用错误
    PAYMENT_NOT_FOUND("error.payment_not_found", "支付记录不存在"),
    INVALID_PAYMENT_TYPE("error.invalid_payment_type", "无效的支付类型"),
    INVALID_STATUS_TRANSITION("error.invalid_status_transition", "状态转换无效"),
    STATUS_TRANSITION_FAILED("error.status_transition_failed", "状态转换失败"),
    MERCHANT_NOT_EXIST("error.merchant_not_exist", "商户不存在"),
    PAYMENT_TIMEOUT("error.payment_timeout", "支付已超时"),

    // 账户相关错误
    ACCOUNT_NOT_FOUND("error.account_not_found", "账户不存在"),
    ACCOUNT_ALREADY_EXISTS("error.account_already_exists", "账户已存在"),
    INVALID_ACCOUNT_TYPE("error.invalid_account_type", "无效的账户类型"),
    ACCOUNT_STATUS_INVALID("error.account_status_invalid", "账户状态无效"),
    ACCOUNT_FROZEN("error.account_frozen", "账户已冻结"),
    ACCOUNT_CLOSED("error.account_closed", "账户已关闭"),
    ACCOUNT_BALANCE_INSUFFICIENT("error.account_balance_insufficient", "账户余额不足"),
    ACCOUNT_AMOUNT_INVALID("error.account_amount_invalid", "账户金额无效"),
    ACCOUNT_OPERATION_FAILED("error.account_operation_failed", "账户操作失败"),

    // 钱包相关错误
    WALLET_NOT_FOUND("error.wallet_not_found", "钱包不存在"),
    WALLET_ALREADY_EXISTS("error.wallet_already_exists", "钱包已存在"),
    INVALID_WALLET_TYPE("error.invalid_wallet_type", "无效的钱包类型"),
    WALLET_STATUS_INVALID("error.wallet_status_invalid", "钱包状态无效"),
    WALLET_FROZEN("error.wallet_frozen", "钱包已冻结"),
    WALLET_CLOSED("error.wallet_closed", "钱包已关闭"),
    WALLET_BALANCE_INSUFFICIENT("error.wallet_balance_insufficient", "钱包余额不足"),
    WALLET_AMOUNT_INVALID("error.wallet_amount_invalid", "钱包金额无效"),
    WALLET_OPERATION_FAILED("error.wallet_operation_failed", "钱包操作失败"),

    // 交易相关错误
    TRANSACTION_NOT_FOUND("error.transaction_not_found", "交易记录不存在"),
    TRANSACTION_ALREADY_EXISTS("error.transaction_already_exists", "交易记录已存在"),
    INVALID_TRANSACTION_TYPE("error.invalid_transaction_type", "无效的交易类型"),
    TRANSACTION_STATUS_INVALID("error.transaction_status_invalid", "交易状态无效"),
    TRANSACTION_AMOUNT_INVALID("error.transaction_amount_invalid", "交易金额无效"),
    TRANSACTION_OPERATION_FAILED("error.transaction_operation_failed", "交易操作失败"),
    TRANSACTION_TIMEOUT("error.transaction_timeout", "交易已超时"),
    TRANSACTION_DUPLICATE("error.transaction_duplicate", "交易重复"),

    // 商户相关错误
    MERCHANT_STATUS_INVALID("error.merchant_status_invalid", "商户状态无效"),
    MERCHANT_FROZEN("error.merchant_frozen", "商户已冻结"),
    MERCHANT_CLOSED("error.merchant_closed", "商户已关闭"),
    MERCHANT_NOT_AUTHORIZED("error.merchant_not_authorized", "商户未授权"),
    MERCHANT_PAYMENT_REGION_NOT_SUPPORTED("error.merchant_payment_region_not_supported", "商户不支持该支付区域"),
    MERCHANT_OPERATION_FAILED("error.merchant_operation_failed", "商户操作失败"),

    // 充值相关错误
    DEPOSIT_AMOUNT_INVALID("error.deposit_amount_invalid", "充值金额无效"),
    DEPOSIT_CHANNEL_INVALID("error.deposit_channel_invalid", "充值渠道无效"),
    DEPOSIT_FAILED("error.deposit_failed", "充值失败"),
    DEPOSIT_TIMEOUT("error.deposit_timeout", "充值超时"),
    DEPOSIT_DUPLICATE("error.deposit_duplicate", "充值重复"),

    // 提现相关错误
    WITHDRAW_AMOUNT_INVALID("error.withdraw_amount_invalid", "提现金额无效"),
    WITHDRAW_CHANNEL_INVALID("error.withdraw_channel_invalid", "提现渠道无效"),
    WITHDRAW_FAILED("error.withdraw_failed", "提现失败"),
    WITHDRAW_TIMEOUT("error.withdraw_timeout", "提现超时"),
    WITHDRAW_DUPLICATE("error.withdraw_duplicate", "提现重复"),
    WITHDRAW_LIMIT_EXCEEDED("error.withdraw_limit_exceeded", "提现限额超出"),

    // 转账相关错误
    TRANSFER_AMOUNT_INVALID("error.transfer_amount_invalid", "转账金额无效"),
    TRANSFER_FAILED("error.transfer_failed", "转账失败"),
    TRANSFER_TIMEOUT("error.transfer_timeout", "转账超时"),
    TRANSFER_DUPLICATE("error.transfer_duplicate", "转账重复"),
    TRANSFER_LIMIT_EXCEEDED("error.transfer_limit_exceeded", "转账限额超出"),

    // 扫码支付相关错误
    SCAN_PAY_AMOUNT_INVALID("error.scan_pay_amount_invalid", "支付金额无效"),
    SCAN_PAY_FAILED("error.scan_pay_failed", "支付失败"),
    SCAN_PAY_TIMEOUT("error.scan_pay_timeout", "支付超时"),
    SCAN_PAY_DUPLICATE("error.scan_pay_duplicate", "支付重复"),
    SCAN_PAY_LIMIT_EXCEEDED("error.scan_pay_limit_exceeded", "支付限额超出"),

    // 第三方支付相关错误
    THIRD_PARTY_PAYMENT_FAILED("error.third_party_payment_failed", "第三方支付失败"),
    THIRD_PARTY_PAYMENT_TIMEOUT("error.third_party_payment_timeout", "第三方支付超时"),
    THIRD_PARTY_PAYMENT_CHANNEL_INVALID("error.third_party_payment_channel_invalid", "第三方支付渠道无效"),
    THIRD_PARTY_PAYMENT_CONFIG_INVALID("error.third_party_payment_config_invalid", "第三方支付配置无效"),
    THIRD_PARTY_PAYMENT_SIGNATURE_INVALID("error.third_party_payment_signature_invalid", "第三方支付签名无效"),
    THIRD_PARTY_PAYMENT_ORDER_NOT_FOUND("error.third_party_payment_order_not_found", "第三方支付订单不存在"),
    THIRD_PARTY_PAYMENT_AMOUNT_MISMATCH("error.third_party_payment_amount_mismatch", "第三方支付金额不匹配"),

    // 必填字段错误
    MERCHANT_ID_REQUIRED("error.merchant_id_required", "商户ID不能为空"),
    USER_ID_REQUIRED("error.user_id_required", "用户ID不能为空"),
    ACCOUNT_ID_REQUIRED("error.account_id_required", "账户ID不能为空"),
    WALLET_ID_REQUIRED("error.wallet_id_required", "钱包ID不能为空"),
    TRANSACTION_ID_REQUIRED("error.transaction_id_required", "交易ID不能为空"),
    AMOUNT_REQUIRED("error.amount_required", "金额不能为空"),
    PAYMENT_REGION_REQUIRED("error.payment_region_required", "支付区域不能为空"),
    PAYMENT_CHANNEL_REQUIRED("error.payment_channel_required", "支付渠道不能为空"),
    FIELD_REQUIRED("error.field_required", "字段不能为空"),
    FIELD_INVALID("error.field_invalid", "字段值无效"),
    FIELD_LENGTH_EXCEEDED("error.field_length_exceeded", "字段长度超出限制"),
    FIELD_FORMAT_INVALID("error.field_format_invalid", "字段格式无效"),

    // 金额相关错误
    AMOUNT_INVALID("error.amount_invalid", "金额无效"),
    AMOUNT_NEGATIVE("error.amount_negative", "金额不能为负数"),
    AMOUNT_ZERO("error.amount_zero", "金额不能为零"),
    AMOUNT_EXCEEDED("error.amount_exceeded", "金额超出限制"),
    AMOUNT_MISMATCH("error.amount_mismatch", "金额不匹配"),
    INVALID_AMOUNT_CALCULATION("error.invalid_amount_calculation", "金额计算无效"),

    // 货币相关错误
    CURRENCY_REQUIRED("error.currency_required", "币种不能为空"),
    INVALID_CURRENCY("error.invalid_currency", "无效的币种"),
    CURRENCY_MISMATCH("error.currency_mismatch", "币种不匹配"),
    CURRENCY_NOT_SUPPORTED("error.currency_not_supported", "不支持的币种"),

    // 时间相关错误
    CREATE_TIME_REQUIRED("error.create_time_required", "创建时间不能为空"),
    PAYMENT_TIME_REQUIRED("error.payment_time_required", "支付时间不能为空"),
    COMPLETE_TIME_REQUIRED("error.complete_time_required", "完成时间不能为空"),
    INVALID_TIME_RANGE("error.invalid_time_range", "无效的时间范围"),
    TIME_REQUIRED("error.time_required", "时间不能为空"),
    TIME_INVALID("error.time_invalid", "时间无效"),
    TIME_EXPIRED("error.time_expired", "时间已过期"),

    // 状态相关错误
    STATUS_REQUIRED("error.status_required", "状态不能为空"),
    STATUS_INVALID("error.status_invalid", "状态无效"),
    STATUS_UPDATE_FAILED("error.status_update_failed", "状态更新失败"),

    // 查询相关错误
    QUERY_PARAMS_REQUIRED("error.query_params_required", "查询参数不能为空"),
    PAGE_NUMBER_INVALID("error.page_number_invalid", "页码无效"),
    PAGE_SIZE_INVALID("error.page_size_invalid", "每页大小无效");

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