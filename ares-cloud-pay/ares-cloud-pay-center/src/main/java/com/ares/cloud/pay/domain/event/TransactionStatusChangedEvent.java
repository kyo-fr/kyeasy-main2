package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.Transaction;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 交易状态变更事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionStatusChangedEvent extends PaymentEvent {
    private final Transaction transaction;
    private final String oldStatus;
    private final String newStatus;
    private final String transactionType;
    private final String paymentRegion;
    private final Long amount;
    private final Long feeAmount;

    public TransactionStatusChangedEvent(Transaction transaction, String oldStatus, String newStatus) {
        super(transaction.getId(), transaction.getFromAccountId());
        this.transaction = transaction;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.transactionType = transaction.getType();
        this.paymentRegion = transaction.getPaymentRegion();
        this.amount = transaction.getAmount().getAmount();
        this.feeAmount = transaction.getFeeAmount().getAmount();
        this.transactionId = transaction.getId();
    }
} 