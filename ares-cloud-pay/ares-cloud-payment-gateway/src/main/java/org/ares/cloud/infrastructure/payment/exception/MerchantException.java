package org.ares.cloud.infrastructure.payment.exception;

/**
 * 商户异常
 */
public class MerchantException extends PaymentException {
    
    public MerchantException(String message) {
        super(PaymentErrorCode.MERCHANT_NOT_FOUND.getCode(), message);
    }
    
    public MerchantException(String message, Throwable cause) {
        super(PaymentErrorCode.MERCHANT_NOT_FOUND.getCode(), message, cause);
    }
} 