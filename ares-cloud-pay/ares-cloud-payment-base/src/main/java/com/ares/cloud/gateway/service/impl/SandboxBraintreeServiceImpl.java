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
 * @description Braintree沙盒环境支付服务实现
 * @date 2024-03-08
 */
@Slf4j
@Service("sandboxBraintreeService")
public class SandboxBraintreeServiceImpl extends AbstractBraintreeService implements IBraintreeService {
    
    @Resource
    private BraintreeGatewayManager braintreeGatewayManager;

    @Override
    public String generateClientToken() {
        log.info("沙盒环境-获取客户端token");
        BraintreeGateway gateway = getGateway();
        return gateway.clientToken().generate();
    }

    @Override
    public PaymentResponse processTransaction(PaymentTransactionRequest request) {
        log.info("沙盒环境-处理支付交易: {}", request);
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(new BigDecimal(request.getTotalAmount()))
                .paymentMethodNonce(request.getNonce())
                .orderId(request.getOrderId())
                .deviceData(request.getDeviceData())
                .options()
                .submitForSettlement(true)
                .done();

        BraintreeGateway gateway = getGateway();
        Result<Transaction> result = gateway.transaction().sale(transactionRequest);

        if (result.isSuccess()) {
            PaymentResponse paymentResponse = buildPaymentResponse(result.getTarget());
            log.info("沙盒环境-支付交易处理成功: {}", paymentResponse);
            return paymentResponse;
        } else {
            throw new BusinessException(result.getMessage());
        }
    }

    @Override
    public PaymentCallbackResult handleWebhook(String signature, String payload) {
        log.info("沙盒环境-处理支付回调: signature={}, payload={}", signature, payload);
        try {
            BraintreeGateway gateway = getGateway();
            WebhookNotification notification = gateway.webhookNotification()
                    .parse(signature, payload);
//            // 2. 处理不同类型的Webhook事件
//            switch (webhookNotification.getKind()) {
//                case SUBSCRIPTION_CANCELED:
//                    // 订阅取消
//                    break;
//                case TRANSACTION_SETTLED:
//                    // 交易清算成功
//                    String transactionId = webhookNotification.getTransaction().getId();
//                    System.out.println("交易清算成功，交易ID：" + transactionId);
//                    break;
//                case TRANSACTION_SETTLEMENT_DECLINED:
//                    // 交易清算失败
//                    break;
//                default:
//                    System.out.println("收到其他类型的通知: " + webhookNotification.getKind());
//                    break;
//            }

            return buildCallbackResult(notification);
        } catch (Exception e) {
            log.error("沙盒环境-处理支付回调失败", e);
            throw new BusinessException(MessageUtils.get("pay_webhook_failed"));
        }
    }

    @Override
    public PaymentResponse queryTransaction(String orderId) {
        log.info("沙盒环境-查询交易状态: orderId={}", orderId);
        BraintreeGateway gateway = getGateway();
        ResourceCollection<Transaction> transactions = gateway.transaction()
                .search(new TransactionSearchRequest()
                        .orderId().is(orderId));

        if (transactions.getMaximumSize() > 0) {
            Transaction transaction = transactions.getFirst();
            if (transaction == null) {
                throw new BusinessException(MessageUtils.get("pay_transaction_not_found"));
            }
            PaymentResponse paymentResponse = buildPaymentResponse(transaction);
            log.info("沙盒环境-查询交易状态成功: {}", paymentResponse);
            return paymentResponse;
        } else {
            throw new BusinessException(MessageUtils.get("pay_transaction_not_found"));
        }
    }

    @Override
    public PaymentResponse processRefund(String transactionId, String amount) {
        log.info("沙盒环境-处理退款请求: transactionId={}, amount={}", transactionId, amount);
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
        return braintreeGatewayManager.getGateway(ApplicationContext.getTenantId(), Environment.SANDBOX);
    }
}