package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.Account;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 账户状态变更事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountStatusChangedEvent extends PaymentEvent {
    private final Account account;
    private final String oldStatus;
    private final String newStatus;
    private final String userId;
    private final String phone;
    private final String accountNo;

    public AccountStatusChangedEvent(Account account, String oldStatus, String newStatus) {
        super(account.getId(), null);
        this.account = account;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.userId = account.getUserId();
        this.phone = account.getPhone();
        this.accountNo = account.getAccount();
        this.accountId = account.getId();
    }
} 