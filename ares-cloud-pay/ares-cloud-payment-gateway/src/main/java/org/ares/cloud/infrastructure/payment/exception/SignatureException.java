package org.ares.cloud.infrastructure.payment.exception;

/**
 * 签名异常
 */
public class SignatureException extends PaymentException {
    
    public SignatureException(String message) {
        super(PaymentErrorCode.SIGN_INVALID.getCode(), message);
    }
    
    public SignatureException(String message, Throwable cause) {
        super(PaymentErrorCode.SIGN_INVALID.getCode(), message, cause);
    }
} 