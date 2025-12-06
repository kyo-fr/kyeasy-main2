package com.ares.cloud.gateway.service;

import com.ares.cloud.gateway.dto.PaymentCallbackResult;
import com.ares.cloud.gateway.dto.PaymentResponse;
import com.ares.cloud.gateway.dto.PaymentTransactionRequest;

/**
 * @author hugo
 * @version 1.0.0
 * @description Braintree支付服务接口
 * @date 2024-03-08
 */
public interface IBraintreeService {
    
    /**
     * 获取客户端token
     * @return 客户端token字符串
     */
    String generateClientToken();

    /**
     * 发起支付交易
     * @param request 支付交易请求
     * @return 支付响应结果
     */
    PaymentResponse processTransaction(PaymentTransactionRequest request);

    /**
     * 处理支付回调
     * @param signature 回调签名
     * @param payload 回调数据
     * @return 回调处理结果
     */
    PaymentCallbackResult handleWebhook(String signature, String payload);

    /**
     * 查询交易状态
     * @param orderId 订单ID
     * @return 支付响应结果
     */
    PaymentResponse queryTransaction(String orderId);

    /**
     * 退款处理
     * @param transactionId 交易ID
     * @param amount 退款金额
     * @return 支付响应结果
     */
    PaymentResponse processRefund(String transactionId, String amount);
}
