package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.Account;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 账户创建事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountCreatedEvent extends PaymentEvent {
    private final Account account;
    private final String userId;
    private final String phone;
    private final String accountNo;

    public AccountCreatedEvent(Account account) {
        super(account.getId(), null);
        this.account = account;
        this.userId = account.getUserId();
        this.phone = account.getPhone();
        this.accountNo = account.getAccount();
        this.accountId = account.getId();
    }
} 