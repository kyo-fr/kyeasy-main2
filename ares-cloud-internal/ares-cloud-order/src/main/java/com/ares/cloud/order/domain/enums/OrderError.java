package com.ares.cloud.order.domain.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * 订单错误码
 */
public enum OrderError implements BaseErrorInfoInterface {
    // 通用错误
    SYSTEM_ERROR("system_error"),
    INVALID_PARAMETER("invalid_parameter"),
    ORDER_NOT_FOUND("order_not_found"),
    INVALID_ORDER_TYPE("invalid_order_type"),
    INVALID_STATUS_TRANSITION("invalid_status_transition"),
    STATUS_TRANSITION_FAILED("status_transition_failed"),
    ORDER_CHECK_FAILED("order_check_failed"),
    MERCHANT_NOT_EXIST("merchant_not_exist"),
    CANNOT_SETTLE_ACCOUNTS_ACROSS_MERCHANTS("cannot_settle_accounts_across_merchants"),

    // 支付相关错误
    ORDER_NOT_PAID("order_not_paid"),
    PAYMENT_TIMEOUT("payment_timeout"),
    INVALID_PAYMENT_AMOUNT("invalid_payment_amount"),
    INVALID_PAYMENT_ITEMS("invalid_payment_items"),
    PAYMENT_AMOUNT_EXCEEDS_REMAINING("payment_amount_exceeds_remaining"),
    ORDER_ALREADY_PAID("order_already_paid"),

    // 退款相关错误
    ORDER_NOT_REFUNDABLE("order_not_refundable"),
    
    /**
     * 不能删除已支付的订单项
     */
    CANNOT_DELETE_PAID_ITEM("cannot_delete_paid_item"),

    // 订单项相关错误
    ORDER_ITEM_NOT_FOUND("order_item_not_found"),
    INVALID_QUANTITY("invalid_quantity"),
    INVALID_UNIT_PRICE("invalid_unit_price"),
    ITEM_ALREADY_PAID("item_already_paid"),

    // 时间相关错误
    INVALID_DELIVERY_TIME("invalid_delivery_time"),
    INVALID_PAYMENT_TIME("invalid_payment_time"),

    // 排队相关错误
    INVALID_STATUS_FOR_QUEUING("invalid_status_for_queuing"),
    NOT_IN_QUEUE("not_in_queue"),
    QUEUE_NOT_ALLOWED("queue_not_allowed"),

    // 结算相关错误
    INVALID_STATUS_FOR_SETTLEMENT("invalid_status_for_settlement"),
    SETTLEMENT_AMOUNT_EXCEEDS_REMAINING("settlement_amount_exceeds_remaining"),
    CANNOT_COMPLETE_SETTLE("cannot_complete_settle"),
    INVALID_SETTLEMENT_AMOUNT("invalid_settlement_amount"),
    ITEM_ALREADY_SETTLED("item_already_settled"),
    ORDER_NOT_SETTLED("order_not_settled"),

    // 用户相关错误
    USER_ID_REQUIRED("user_id_required"),

    // 骑手相关错误
    RIDER_ID_REQUIRED("rider_id_required"),
    RIDER_ALREADY_ASSIGNED("rider_already_assigned"),

    // 必填字段错误
    MERCHANT_ID_REQUIRED("merchant_id_required"),
    ORDER_ITEMS_REQUIRED("order_items_required"),
    TABLE_NO_REQUIRED("table_no_required"),
    RESERVATION_TIME_REQUIRED("reservation_time_required"),
    PICKUP_TIME_REQUIRED("pickup_time_required"),

    // 配送相关错误
    DELIVERY_TYPE_REQUIRED("delivery_type_required"),
    DELIVERY_COMPANY_REQUIRED("delivery_company_required"),
    TRACKING_NO_REQUIRED("tracking_no_required"),
    DELIVERY_PHONE_REQUIRED("delivery_phone_required"),
    ORDER_NOT_DELIVERY("order_not_delivery"),
    ORDER_NOT_CONFIRMED("order_not_confirmed"),
    DELIVERY_ALREADY_STARTED("delivery_already_started"),
    DELIVERY_INFO_REQUIRED("delivery_info_required"),
    DELIVERY_INFO_NOT_FOUND("delivery_info_not_found"),

    // 支付相关错误
    PAYMENT_MODE_REQUIRED("payment_mode_required"),
    INVALID_PAYMENT_MODE("invalid_payment_mode"),
    PAYMENT_MODE_NOT_SUPPORTED("payment_mode_not_supported"),
    PAYMENT_CHANNEL_REQUIRED("payment_channel_required"),
    INVALID_PAYMENT_CHANNEL("invalid_payment_channel"),
    DEFAULT_PAYMENT_CHANNEL_NOT_FOUND("default_payment_channel_not_found"),

    // 取消相关错误
    ORDER_NOT_CANCELLABLE("order_not_cancellable"),
    CANNOT_CANCEL_DELIVERING_ORDER("cannot_cancel_delivering_order"),

    // 状态转换相关错误
    INVALID_STATUS_FOR_CANCEL("invalid_status_for_cancel"),
    INVALID_STATUS_FOR_REFUND("invalid_status_for_refund"),
    INVALID_STATUS_FOR_PAYMENT("invalid_status_for_payment"),
    INVALID_STATUS_FOR_DELIVERY("invalid_status_for_delivery"),
    CONFIRMATION_INVALID_ORDER_TYPE("confirmation_invalid_order_type"),

    // 结算金额相关错误
    SETTLEMENT_AMOUNT_EXCEEDS_PAID("settlement_amount_exceeds_paid"),
    PAYMENT_AMOUNT_EXCEEDS_ITEM_AMOUNT("payment_amount_exceeds_item_amount"),

    // 配送相关错误
    DELIVERY_NOT_ACCEPTED("delivery_not_accepted"),
    INVALID_DELIVERY_INFO("invalid_delivery_info"),
    ORDER_ALREADY_ACCEPTED("order_already_accepted"),
    RECEIVER_NAME_REQUIRED("receiver_name_required"),
    RECEIVER_PHONE_REQUIRED("receiver_phone_required"),
    DELIVERY_ADDRESS_REQUIRED("delivery_address_required"),
    DELIVERY_LOCATION_REQUIRED("delivery_location_required"),
    INVALID_DELIVERY_DISTANCE("invalid_delivery_distance"),
    DELIVERY_FEE_REQUIRED("delivery_fee_required"),
    INVALID_DELIVERY_FEE_CURRENCY("invalid_delivery_fee_currency"),
    INVALID_DELIVERY_FEE_SCALE("invalid_delivery_fee_scale"),

    // 预订相关错误
    RESERVATION_INFO_REQUIRED("reservation_info_required"),
    INVALID_RESERVATION_TIME("invalid_reservation_time"),
    CANNOT_UPDATE_COMPLETED_ORDER("cannot_update_completed_order"),
    RESERVER_NAME_REQUIRED("reserver_name_required"),
    RESERVER_PHONE_REQUIRED("reserver_phone_required"),
    DINING_NUMBER_REQUIRED("dining_number_required"),
    GET_USER_ERROR("query_user_err"),

    // 商品库存相关错误
    PRODUCT_NOT_FOUND("product_not_found"),
    PRODUCT_NOT_ENABLED("product_not_enabled"),
    PRODUCT_SPEC_NOT_FOUND("product_spec_not_found"),
    PRODUCT_SPEC_NOT_ENABLED("product_spec_not_enabled"),
    PRODUCT_AVAILABILITY_CHECK_FAILED("product_availability_check_failed"),
    PRODUCT_SPEC_AVAILABILITY_CHECK_FAILED("product_spec_availability_check_failed"),
    INSUFFICIENT_STOCK("insufficient_stock"),
    INVENTORY_RESERVE_FAILED("inventory_reserve_failed"),
    INVENTORY_RELEASE_FAILED("inventory_release_failed"),
    INVENTORY_DEDUCT_FAILED("inventory_deduct_failed"), 
    PRODUCT_DISABLED("product_disabled"),
    PRODUCT_PRICE_MISMATCH("product_price_mismatch"),
    PRODUCT_SPEC_PRICE_MISMATCH("product_spec_price_mismatch"),
    
    // 存储空间相关错误
    INSUFFICIENT_STORAGE_SPACE("insufficient_storage_space"),
    STORAGE_CONSUMPTION_FAILED("storage_consumption_failed");


    private final Integer code;
    private final String messageKey;

    OrderError(String messageKey) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = messageKey;
    }

    OrderError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
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
