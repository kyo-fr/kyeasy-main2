package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.PaymentOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ares.cloud.common.model.Money;

/**
 * 支付订单状态变更事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class PaymentOrderStatusChangedEvent extends PaymentEvent {
    private final PaymentOrder paymentOrder;
    private final String orderNo;
    private final String merchantOrderNo;
    private final String oldStatus;
    private final String newStatus;
    private final Money amount;
    private final String paymentRegion;
    private final Long payTime;

    public PaymentOrderStatusChangedEvent(PaymentOrder paymentOrder, String oldStatus, String newStatus) {
        super(paymentOrder.getId(), paymentOrder.getMerchantId());
        this.paymentOrder = paymentOrder;
        this.orderNo = paymentOrder.getOrderNo();
        this.merchantOrderNo = paymentOrder.getMerchantOrderNo();
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.amount = paymentOrder.getAmount();
        this.paymentRegion = paymentOrder.getPaymentRegion();
        this.payTime = paymentOrder.getPayTime();
    }
} 