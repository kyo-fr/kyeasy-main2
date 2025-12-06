package com.ares.cloud.pay.infrastructure.listener;

import com.ares.cloud.pay.domain.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 支付事件监听器
 * 处理支付相关的领域事件，记录日志
 */
@Slf4j
@Component
public class PaymentEventListener {

    /**
     * 支付订单创建事件
     */
    @Async
    @EventListener
    public void onPaymentOrderCreated(PaymentOrderCreatedEvent event) {
        log.info("支付订单创建事件: orderNo={}, merchantOrderNo={}, merchantId={}, amount={}, paymentRegion={}, subject={}, expireTime={}",
                event.getOrderNo(),
                event.getMerchantOrderNo(),
                event.getMerchantId(),
                event.getAmount(),
                event.getPaymentRegion(),
                event.getSubject(),
                event.getExpireTime());
    }

    /**
     * 支付订单状态变更事件
     */
    @Async
    @EventListener
    public void onPaymentOrderStatusChanged(PaymentOrderStatusChangedEvent event) {
        log.info("支付订单状态变更事件: orderNo={}, merchantOrderNo={}, merchantId={}, oldStatus={}, newStatus={}, amount={}, paymentRegion={}",
                event.getOrderNo(),
                event.getMerchantOrderNo(),
                event.getMerchantId(),
                event.getOldStatus(),
                event.getNewStatus(),
                event.getAmount(),
                event.getPaymentRegion());
    }

    /**
     * 支付成功事件
     */
    @Async
    @EventListener
    public void onPaymentSuccess(PaymentSuccessEvent event) {
        log.info("支付成功事件: orderNo={}, merchantOrderNo={}, merchantId={}, amount={}, paymentRegion={}, payTime={}, transactionId={}",
                event.getOrderNo(),
                event.getMerchantOrderNo(),
                event.getMerchantId(),
                event.getAmount(),
                event.getPaymentRegion(),
                event.getPayTime(),
                event.getTransactionId());
    }

    /**
     * 支付失败事件
     */
    @Async
    @EventListener
    public void onPaymentFailed(PaymentFailedEvent event) {
        log.warn("支付失败事件: orderNo={}, merchantOrderNo={}, merchantId={}, amount={}, paymentRegion={}, failureReason={}",
                event.getOrderNo(),
                event.getMerchantOrderNo(),
                event.getMerchantId(),
                event.getAmount(),
                event.getPaymentRegion(),
                event.getFailureReason());
    }
} 