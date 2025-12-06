package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.PaymentOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ares.cloud.common.model.Money;

/**
 * 支付成功事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class PaymentSuccessEvent extends PaymentEvent {
    private final PaymentOrder paymentOrder;
    private final String orderNo;
    private final String merchantOrderNo;
    private final Money amount;
    private final String paymentRegion;
    private final Long payTime;
    private final String transactionId;

    public PaymentSuccessEvent(PaymentOrder paymentOrder, String transactionId) {
        super(paymentOrder.getId(), paymentOrder.getMerchantId());
        this.paymentOrder = paymentOrder;
        this.orderNo = paymentOrder.getOrderNo();
        this.merchantOrderNo = paymentOrder.getMerchantOrderNo();
        this.amount = paymentOrder.getAmount();
        this.paymentRegion = paymentOrder.getPaymentRegion();
        this.payTime = paymentOrder.getPayTime();
        this.transactionId = transactionId;
    }
} 