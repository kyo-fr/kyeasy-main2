package org.ares.cloud.infrastructure.constants;

import lombok.Getter;

@Getter
public enum PaymentErrorCode {
    INVALID_MERCHANT("PAY001", "无效的商户"),
    INVALID_SIGN("PAY002", "无效的签名"),
    INVALID_AMOUNT("PAY003", "无效的金额"),
    CHANNEL_NOT_SUPPORTED("PAY004", "不支持的支付渠道"),
    PAYMENT_TIMEOUT("PAY005", "支付超时"),
    SYSTEM_ERROR("PAY999", "系统错误");

    private final String code;
    private final String message;

    PaymentErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
} 