package com.ares.cloud.gateway.service.impl;

import com.braintreegateway.*;
import com.ares.cloud.gateway.dto.PaymentCallbackResult;
import com.ares.cloud.gateway.dto.PaymentResponse;

/**
 * @author hugo
 * @version 1.0.0
 * @description Braintree支付服务抽象基类
 * @date 2024-03-08
 */
public abstract class AbstractBraintreeService {
    
    /**
     * 构建支付响应
     */
    protected PaymentResponse buildPaymentResponse(Transaction transaction) {
        return PaymentResponse.builder()
                .transactionId(transaction.getId())
                .orderId(transaction.getOrderId())
                .amount(transaction.getAmount().toString())
                .status(transaction.getStatus().toString())
                .rawResponse(transaction)
                .build();
    }

    /**
     * 构建回调结果
     */
    protected PaymentCallbackResult buildCallbackResult(WebhookNotification notification) {
        Transaction transaction = notification.getTransaction();
        return PaymentCallbackResult.builder()
                .transactionId(transaction.getId())
                .orderId(transaction.getOrderId())
                .amount(transaction.getAmount().toString())
                .status(notification.getKind().toString())
                .message("回调处理成功")
                .build();
    }
} 