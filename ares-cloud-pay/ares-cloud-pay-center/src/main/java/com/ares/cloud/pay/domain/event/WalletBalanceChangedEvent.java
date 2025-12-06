package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.Wallet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ares.cloud.common.model.Money;

/**
 * 钱包余额变更事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class WalletBalanceChangedEvent extends PaymentEvent {
    private final Wallet wallet;
    private final String ownerId;
    private final String ownerType;
    private final String paymentRegion;
    private final Money oldBalance;
    private final Money newBalance;
    private final Money changeAmount;
    private final String changeType; // INCREASE, DECREASE
    private final String transactionId;

    public WalletBalanceChangedEvent(Wallet wallet, Money oldBalance, Money changeAmount, 
                                   String changeType, String transactionId) {
        super(wallet.getId(), null);
        this.wallet = wallet;
        this.ownerId = wallet.getOwnerId();
        this.ownerType = wallet.getOwnerType();
        this.paymentRegion = wallet.getPaymentRegion();
        this.oldBalance = oldBalance;
        this.newBalance = wallet.getBalance();
        this.changeAmount = changeAmount;
        this.changeType = changeType;
        this.transactionId = transactionId;
    }
} 