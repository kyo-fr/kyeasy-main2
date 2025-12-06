package com.ares.cloud.pay.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 支付事件基类
 */
@Getter
public abstract class PaymentEvent extends ApplicationEvent {
    protected final String paymentId;
    protected final String merchantId;
    protected String userId;
    protected String accountId;
    protected String transactionId;

    public PaymentEvent(String paymentId, String merchantId) {
        super(paymentId);
        this.paymentId = paymentId;
        this.merchantId = merchantId;
    }
} 