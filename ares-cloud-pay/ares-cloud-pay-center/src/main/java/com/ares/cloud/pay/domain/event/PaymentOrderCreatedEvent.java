package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.PaymentOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ares.cloud.common.model.Money;

/**
 * 支付订单创建事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class PaymentOrderCreatedEvent extends PaymentEvent {
    private final PaymentOrder paymentOrder;
    private final String orderNo;
    private final String merchantOrderNo;
    private final Money amount;
    private final String paymentRegion;
    private final String subject;
    private final String description;
    private final Long expireTime;

    public PaymentOrderCreatedEvent(PaymentOrder paymentOrder) {
        super(paymentOrder.getId(), paymentOrder.getMerchantId());
        this.paymentOrder = paymentOrder;
        this.orderNo = paymentOrder.getOrderNo();
        this.merchantOrderNo = paymentOrder.getMerchantOrderNo();
        this.amount = paymentOrder.getAmount();
        this.paymentRegion = paymentOrder.getPaymentRegion();
        this.subject = paymentOrder.getSubject();
        this.description = paymentOrder.getDescription();
        this.expireTime = paymentOrder.getExpireTime();
    }
} 