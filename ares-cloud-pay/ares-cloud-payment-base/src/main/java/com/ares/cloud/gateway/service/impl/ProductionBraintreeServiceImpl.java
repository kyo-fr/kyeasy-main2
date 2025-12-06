package com.ares.cloud.gateway.service.impl;

import com.ares.cloud.gateway.manager.BraintreeGatewayManager;
import com.braintreegateway.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.BusinessException;
import com.ares.cloud.gateway.dto.PaymentCallbackResult;
import com.ares.cloud.gateway.dto.PaymentResponse;
import com.ares.cloud.gateway.dto.PaymentTransactionRequest;
import com.ares.cloud.gateway.service.IBraintreeService;
import org.ares.cloud.i18n.utils.MessageUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author hugo
 * @version 1.0.0
 * @description Braintree生产环境支付服务实现
 * @date 2024-03-08
 */
@Slf4j
@Service("productionBraintreeService")
public class ProductionBraintreeServiceImpl extends AbstractBraintreeService implements IBraintreeService {
    
    @Resource
    private BraintreeGatewayManager braintreeGatewayManager;

    @Override
    public String generateClientToken() {
        log.info("生产环境-获取客户端token");
        BraintreeGateway gateway = braintreeGatewayManager.getGateway(ApplicationContext.getTenantId(), Environment.PRODUCTION);
        return gateway.clientToken().generate();
    }

    @Override
    public PaymentResponse processTransaction(PaymentTransactionRequest request) {
        log.info("生产环境-处理支付交易: {}", request);
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(new BigDecimal(request.getTotalAmount()))
                .paymentMethodNonce(request.getNonce())
                .orderId(request.getOrderId())
                .deviceData(request.getDeviceData())
                .options()
                .submitForSettlement(true)
                .done();

        BraintreeGateway gateway = braintreeGatewayManager.getGateway(ApplicationContext.getTenantId(), Environment.PRODUCTION);
        Result<Transaction> result = gateway.transaction().sale(transactionRequest);

        if (result.isSuccess()) {
            return buildPaymentResponse(result.getTarget());
        } else {
            throw new BusinessException(result.getMessage());
        }
    }

    @Override
    public PaymentCallbackResult handleWebhook(String signature, String payload) {
        // todo 回调中要根据订单号获取订单的所属商户，然后再获取Braintree网关
        log.info("生产环境-处理支付回调: signature={}, payload={}", signature, payload);
        try {
            BraintreeGateway gateway = getGateway();
            WebhookNotification notification = gateway.webhookNotification()
                    .parse(signature, payload);
            
            return buildCallbackResult(notification);
        } catch (Exception e) {
            log.error("生产环境-处理支付回调失败", e);
            throw new BusinessException(MessageUtils.get("pay_webhook_failed"));
        }
    }

    @Override
    public PaymentResponse queryTransaction(String orderId) {
        log.info("生产环境-查询交易状态: orderId={}", orderId);
        BraintreeGateway gateway = getGateway();
        ResourceCollection<Transaction> transactions = gateway.transaction()
                .search(new TransactionSearchRequest()
                        .orderId().is(orderId));

        if (transactions.getMaximumSize() > 0) {
            Transaction transaction = transactions.getFirst();
            if (transaction == null) {
                throw new BusinessException(MessageUtils.get("pay_transaction_not_found"));
            }
            return buildPaymentResponse(transaction);
        } else {
            throw new BusinessException(MessageUtils.get("pay_transaction_not_found"));
        }
    }

    @Override
    public PaymentResponse processRefund(String transactionId, String amount) {
        log.info("生产环境-处理退款请求: transactionId={}, amount={}", transactionId, amount);
        BraintreeGateway gateway = getGateway();
        Result<Transaction> result = gateway.transaction()
                .refund(transactionId, new BigDecimal(amount));

        if (result.isSuccess()) {
            return buildPaymentResponse(result.getTarget());
        } else {
            throw new BusinessException(result.getMessage());
        }
    }

    /**
     * 获取生产环境Gateway
     * @return Gateway
     */
    private BraintreeGateway getGateway() {
        return braintreeGatewayManager.getGateway(ApplicationContext.getTenantId(), Environment.PRODUCTION);
    }
}