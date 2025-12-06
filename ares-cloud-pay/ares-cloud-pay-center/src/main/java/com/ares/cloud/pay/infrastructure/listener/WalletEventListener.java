package com.ares.cloud.pay.infrastructure.listener;

import com.ares.cloud.pay.domain.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 钱包事件监听器
 * 处理钱包相关的领域事件，记录日志
 */
@Slf4j
@Component
public class WalletEventListener {
    /**
     * 钱包余额变更事件
     */
    @Async
    @EventListener
    public void onWalletBalanceChanged(WalletBalanceChangedEvent event) {
        log.info("钱包余额变更事件: walletId={}, ownerId={}, ownerType={}, paymentRegion={}, oldBalance={}, newBalance={}, changeAmount={}, changeType={}, transactionId={}",
                event.getPaymentId(),
                event.getOwnerId(),
                event.getOwnerType(),
                event.getPaymentRegion(),
                event.getOldBalance(),
                event.getNewBalance(),
                event.getChangeAmount(),
                event.getChangeType(),
                event.getTransactionId());
    }
} 