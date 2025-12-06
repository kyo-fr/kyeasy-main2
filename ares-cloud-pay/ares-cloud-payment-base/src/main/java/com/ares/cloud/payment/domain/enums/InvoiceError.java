package com.ares.cloud.payment.domain.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;
import lombok.Getter;

/**
 * 发票错误码
 */
@Getter
public enum InvoiceError implements BaseErrorInfoInterface {
    // 通用错误
    INVOICE_NOT_FOUND("error.invoice_not_found", "发票不存在"),
    INVALID_INVOICE_TYPE("error.invalid_invoice_type", "无效的发票类型"),
    INVALID_STATUS_TRANSITION("error.invalid_status_transition", "状态转换无效"),
    STATUS_TRANSITION_FAILED("error.status_transition_failed", "状态转换失败"),
    INVOICE_CHECK_FAILED("error.invoice_check_failed", "发票检查失败"),
    MERCHANT_NOT_EXIST("error.merchant_not_exist", "商户不存在"),
    INVOICE_TIMEOUT("error.invoice_timeout", "发票已超时"),

    // 发票生成相关错误
    INVOICE_ALREADY_GENERATED("error.invoice_generation_failed", "发票生成失败"),
    INVALID_INVOICE_AMOUNT("error.invalid_invoice_amount", "无效的发票金额"),
    INVALID_INVOICE_ITEMS("error.invalid_invoice_items", "无效的发票明细"),
    INVOICE_AMOUNT_EXCEEDS_ORDER("error.invoice_amount_exceeds_order", "发票金额超出订单金额"),
    ORDER_NOT_PAID("error.order_not_paid", "订单未支付"),
    INVALID_PARTY_COMBINATION("error.invalid_party_combination", "无效的交易方组合"),
    INVOICE_GENERATION_FAILED("error.invoice_generation_failed", "发票生成失败"),

    // 发票作废相关错误
    INVOICE_NOT_VOIDABLE("error.invoice_not_voidable", "发票不能作废"),
    INVOICE_ALREADY_VOIDED("error.invoice_already_voided", "发票已作废"),
    INVALID_VOID_REASON("error.invalid_void_reason", "作废原因无效"),
    INVALID_STATUS_FOR_VOID("error.invalid_status_for_void", "无效的状态用于作废"),
    VOID_REASON_REQUIRED("error.void_reason_required", "作废原因不能为空"),

    // 发票明细相关错误
    INVOICE_ITEM_NOT_FOUND("error.invoice_item_not_found", "发票明细不存在"),
    INVALID_TAX_RATE("error.invalid_tax_rate", "无效的税率"),
    INVALID_ITEM_AMOUNT("error.invalid_item_amount", "无效的明细金额"),
    ITEM_ALREADY_INVOICED("error.item_already_invoiced", "明细已开票"),
    INVALID_QUANTITY("error.invalid_quantity", "无效的数量"),
    INVALID_UNIT_PRICE("error.invalid_unit_price", "无效的单价"),
    INVOICE_ITEMS_EMPTY("error.invoice_items_empty", "发票明细不能为空"),

    // 交易方相关错误
    PAYER_NOT_FOUND("error.payer_not_found", "付款方不存在"),
    PAYEE_NOT_FOUND("error.payee_not_found", "收款方不存在"),
    INVALID_PARTY_TYPE("error.invalid_party_type", "无效的交易方类型"),
    TAX_ID_REQUIRED("error.tax_id_required", "税号不能为空"),
    INVALID_TAX_ID("error.invalid_tax_id", "无效的税号"),
    PARTY_INFO_INCOMPLETE("error.party_info_incomplete", "交易方信息不完整"),
    PARTY_ADDRESS_REQUIRED("error.party_address_required", "交易方地址不能为空"),
    PARTY_CONTACT_REQUIRED("error.party_contact_required", "交易方联系方式不能为空"),

    // 必填字段错误
    MERCHANT_ID_REQUIRED("error.merchant_id_required", "商户ID不能为空"),
    ORDER_ID_REQUIRED("error.order_id_required", "订单ID不能为空"),
    CONTRACT_ID_REQUIRED("error.contract_id_required", "合同ID不能为空"),
    TRANSACTION_ID_REQUIRED("error.transaction_id_required", "交易ID不能为空"),
    INVOICE_ITEMS_REQUIRED("error.invoice_items_required", "发票明细不能为空"),
    PAYMENT_ITEMS_REQUIRED("error.payment_items_required", "支付明细不能为空"),
    FIELD_REQUIRED("error.field_required", "字段不能为空"),
    FIELD_INVALID("error.field_invalid", "字段值无效"),
    FIELD_LENGTH_EXCEEDED("error.field_length_exceeded", "字段长度超出限制"),
    FIELD_FORMAT_INVALID("error.field_format_invalid", "字段格式无效"),

    // 金额相关错误
    PRE_TAX_AMOUNT_REQUIRED("error.pre_tax_amount_required", "未税金额不能为空"),
    TOTAL_AMOUNT_REQUIRED("error.total_amount_required", "总金额不能为空"),
    TAX_AMOUNT_REQUIRED("error.tax_amount_required", "税额不能为空"),
    INVALID_AMOUNT_CALCULATION("error.invalid_amount_calculation", "金额计算无效"),
    AMOUNT_MISMATCH("error.amount_mismatch", "金额不匹配"),
    INVALID_DEDUCT_AMOUNT("error.invalid_deduct_amount", "无效的扣除金额"),
    REFUND_AMOUNT_EXCEEDS_TOTAL("error.refund_amount_exceeds_total", "退款金额超出总金额"),
    AMOUNT_REQUIRED("error.amount_required", "金额不能为空"),
    AMOUNT_INVALID("error.amount_invalid", "金额无效"),
    AMOUNT_NEGATIVE("error.amount_negative", "金额不能为负数"),
    AMOUNT_ZERO("error.amount_zero", "金额不能为零"),
    AMOUNT_EXCEEDED("error.amount_exceeded", "金额超出限制"),

    // 支付相关错误
    INVALID_PAYMENT_TYPE("error.invalid_payment_type", "无效的支付类型"),
    PAYMENT_AMOUNT_MISMATCH("error.payment_amount_mismatch", "支付金额不匹配"),
    INVALID_PAYMENT_STATUS("error.invalid_payment_status", "支付状态无效"),
    PAYMENT_CHANNEL_REQUIRED("error.payment_channel_required", "支付渠道不能为空"),
    INVALID_PAYMENT_CHANNEL("error.invalid_payment_channel", "无效的支付渠道"),
    PAYMENT_FAILED("error.payment_failed", "支付失败"),
    INVALID_TRADE_NO("error.invalid_trade_no", "无效的交易号"),
    INVALID_PAY_TIME("error.invalid_pay_time", "支付时间无效"),
    PAYMENT_NOT_FOUND("error.payment_not_found", "支付记录不存在"),
    PAYMENT_STATUS_INVALID("error.payment_status_invalid", "支付状态无效"),
    PAYMENT_TIME_INVALID("error.payment_time_invalid", "支付时间无效"),

    // 货币相关错误
    CURRENCY_REQUIRED("error.currency_required", "币种不能为空"),
    SCALE_REQUIRED("error.scale_required", "比例不能为空"),
    INVALID_CURRENCY("error.invalid_currency", "无效的币种"),
    INVALID_SCALE("error.invalid_scale", "无效的比例"),
    CURRENCY_MISMATCH("error.currency_mismatch", "币种不匹配"),
    CURRENCY_NOT_SUPPORTED("error.currency_not_supported", "不支持的币种"),

    // 时间相关错误
    CREATE_TIME_REQUIRED("error.create_time_required", "创建时间不能为空"),
    DELIVERY_TIME_REQUIRED("error.delivery_time_required", "交付时间不能为空"),
    COMPLETE_TIME_REQUIRED("error.complete_time_required", "完成时间不能为空"),
    INVALID_TIME_RANGE("error.invalid_time_range", "无效的时间范围"),
    INVALID_DELIVERY_TIME("error.invalid_delivery_time", "无效的交付时间"),
    INVALID_PAYMENT_TIME("error.invalid_payment_time", "支付时间无效"),
    TIME_REQUIRED("error.time_required", "时间不能为空"),
    TIME_INVALID("error.time_invalid", "时间无效"),
    TIME_EXPIRED("error.time_expired", "时间已过期"),

    // 状态相关错误
    INVALID_STATUS_FOR_GENERATION("error.invalid_status_for_generation", "无效的状态用于生成"),
    INVALID_STATUS_FOR_PAYMENT("error.invalid_status_for_payment", "无效的状态用于支付"),
    INVALID_STATUS_FOR_DELIVERY("error.invalid_status_for_delivery", "无效的状态用于交付"),
    INVALID_STATUS_FOR_REFUND("error.invalid_status_for_refund", "无效的状态用于退款"),
    INVALID_STATUS_FOR_COMPLETION("error.invalid_status_for_completion", "无效的状态用于完成"),
    STATUS_REQUIRED("error.status_required", "状态不能为空"),
    STATUS_INVALID("error.status_invalid", "状态无效"),
    STATUS_UPDATE_FAILED("error.status_update_failed", "状态更新失败"),

    // 退款相关错误
    INVOICE_NOT_REFUNDABLE("error.invoice_not_refundable", "发票不能退款"),
    INVALID_REFUND_REASON("error.invalid_refund_reason", "退款原因无效"),
    REFUND_AMOUNT_REQUIRED("error.refund_amount_required", "退款金额不能为空"),
    PARTIAL_REFUND_NOT_SUPPORTED("error.partial_refund_not_supported", "部分退款不支持"),
    REFUND_NOT_FOUND("error.refund_not_found", "退款记录不存在"),
    REFUND_AMOUNT_INVALID("error.refund_amount_invalid", "退款金额无效"),
    REFUND_REASON_REQUIRED("error.refund_reason_required", "退款原因不能为空"),
    REFUND_FAILED("error.refund_failed", "退款失败"),
    REFUND_STATUS_INVALID("error.refund_status_invalid", "退款状态无效"),

    // 查询相关错误
    QUERY_PARAMS_REQUIRED("error.query_params_required", "查询参数不能为空"),
    PAGE_NUMBER_INVALID("error.page_number_invalid", "页码无效"),
    PAGE_SIZE_INVALID("error.page_size_invalid", "每页大小无效"),
    USER_ID_REQUIRED("error.user_id_required", "用户ID不能为空");

    private final Integer code;
    private final String messageKey;
    private final String message;

    InvoiceError(String messageKey, String message) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = messageKey;
        this.message = message;
    }

    InvoiceError(Integer code, String messageKey, String message) {
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