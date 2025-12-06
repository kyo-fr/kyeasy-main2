package org.ares.cloud.infrastructure.payment.exception;

/**
 * 支付错误码枚举
 */
public enum PaymentErrorCode {
    
    CHANNEL_INVOKE_ERROR("CHANNEL_INVOKE_ERROR", "调用支付渠道失败"),
    SIGN_INVALID("SIGN_INVALID", "签名无效"),
    MERCHANT_NOT_FOUND("MERCHANT_NOT_FOUND", "商户不存在");

    private final String code;
    private final String message;

    PaymentErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public PaymentException exception(String detailMessage, Throwable cause) {
        return new PaymentException(this.code, detailMessage, cause);
    }
} 