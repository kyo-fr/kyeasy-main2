package com.ares.cloud.pay.infrastructure.listener;

import com.ares.cloud.pay.domain.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 交易事件监听器
 * 处理交易相关的领域事件，记录日志
 */
@Slf4j
@Component
public class TransactionEventListener {

    /**
     * 交易创建事件
     */
    @Async
    @EventListener
    public void onTransactionCreated(TransactionCreatedEvent event) {
        log.info("交易创建事件: transactionId={}, fromAccountId={}, toAccountId={}, amount={}, paymentRegion={}, type={}, status={}",
                event.getTransactionId(),
                event.getTransaction().getFromAccountId(),
                event.getTransaction().getToAccountId(),
                event.getAmount(),
                event.getPaymentRegion(),
                event.getTransactionType(),
                event.getTransaction().getStatus());
    }

    /**
     * 交易状态变更事件
     */
    @Async
    @EventListener
    public void onTransactionStatusChanged(TransactionStatusChangedEvent event) {
        log.info("交易状态变更事件: transactionId={}, fromAccountId={}, toAccountId={}, amount={}, paymentRegion={}, oldStatus={}, newStatus={}",
                event.getTransactionId(),
                event.getTransaction().getFromAccountId(),
                event.getTransaction().getToAccountId(),
                event.getAmount(),
                event.getPaymentRegion(),
                event.getOldStatus(),
                event.getNewStatus());
    }

    /**
     * 转账成功事件
     */
    @Async
    @EventListener
    public void onTransferSuccess(TransferSuccessEvent event) {
        log.info("转账成功事件: transactionId={}, fromAccountId={}, toAccountId={}, amount={}, paymentRegion={}, transferType={}, description={}",
                event.getTransactionId(),
                event.getFromAccountId(),
                event.getToAccountId(),
                event.getAmount(),
                event.getPaymentRegion(),
                event.getTransferType(),
                event.getDescription());
    }
} 