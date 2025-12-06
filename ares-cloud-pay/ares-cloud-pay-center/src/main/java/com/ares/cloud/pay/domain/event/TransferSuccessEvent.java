package com.ares.cloud.pay.domain.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ares.cloud.common.model.Money;

/**
 * 转账成功事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class TransferSuccessEvent extends PaymentEvent {
    private final String fromAccountId;
    private final String toAccountId;
    private final Money amount;
    private final String paymentRegion;
    private final String transferType; // USER_TO_USER, USER_TO_MERCHANT, MERCHANT_TO_USER, PLATFORM_RECHARGE, PLATFORM_DEDUCTION
    private final String description;

    public TransferSuccessEvent(String transactionId, String fromAccountId, String toAccountId, 
                              Money amount, String paymentRegion, String transferType, String description) {
        super(transactionId, null);
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.paymentRegion = paymentRegion;
        this.transferType = transferType;
        this.description = description;
        this.transactionId = transactionId;
    }
} 