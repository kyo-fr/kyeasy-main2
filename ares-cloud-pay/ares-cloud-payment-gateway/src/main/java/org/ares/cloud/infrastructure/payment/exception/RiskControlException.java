package org.ares.cloud.infrastructure.payment.exception;

/**
 * 风控异常
 */
public class RiskControlException extends PaymentException {
    
    public RiskControlException(String message) {
        super("200", message);
    }

    public RiskControlException(String message, Throwable cause) {
        super("200", message, cause);
    }
} 