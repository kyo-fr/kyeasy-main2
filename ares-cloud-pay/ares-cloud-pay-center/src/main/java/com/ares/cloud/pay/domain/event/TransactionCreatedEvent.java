package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.Transaction;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 交易创建事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionCreatedEvent extends PaymentEvent {
    private final Transaction transaction;
    private final String transactionType;
    private final String paymentRegion;
    private final Long amount;
    private final Long feeAmount;

    public TransactionCreatedEvent(Transaction transaction) {
        super(transaction.getId(), transaction.getFromAccountId());
        this.transaction = transaction;
        this.transactionType = transaction.getType();
        this.paymentRegion = transaction.getPaymentRegion();
        this.amount = transaction.getAmount().getAmount();
        this.feeAmount = transaction.getFeeAmount().getAmount();
        this.transactionId = transaction.getId();
    }
} 