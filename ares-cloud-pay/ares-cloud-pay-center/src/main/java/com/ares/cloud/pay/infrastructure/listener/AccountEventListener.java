package com.ares.cloud.pay.infrastructure.listener;

import com.ares.cloud.pay.domain.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 账户事件监听器
 * 处理账户相关的领域事件，记录日志
 */
@Slf4j
@Component
public class AccountEventListener {

    /**
     * 账户创建事件
     */
    @Async
    @EventListener
    public void onAccountCreated(AccountCreatedEvent event) {
        log.info("账户创建事件: accountId={}, userId={}, phone={}, accountNo={}, status={}",
                event.getAccountId(),
                event.getUserId(),
                event.getPhone(),
                event.getAccountNo(),
                event.getAccount().getStatus());
    }

    /**
     * 账户状态变更事件
     */
    @Async
    @EventListener
    public void onAccountStatusChanged(AccountStatusChangedEvent event) {
        log.info("账户状态变更事件: accountId={}, userId={}, phone={}, accountNo={}, oldStatus={}, newStatus={}",
                event.getAccountId(),
                event.getUserId(),
                event.getPhone(),
                event.getAccountNo(),
                event.getOldStatus(),
                event.getNewStatus());
    }
} 