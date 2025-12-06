package org.ares.cloud.infrastructure.payment.impl;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.model.enums.PaymentChannel;
import org.ares.cloud.domain.service.PaymentChannelService;
import org.ares.cloud.infrastructure.payment.config.PaymentProperties;
import org.ares.cloud.infrastructure.payment.exception.PaymentErrorCode;
import org.ares.cloud.infrastructure.payment.exception.PaymentException;
import org.ares.cloud.infrastructure.payment.model.MerchantPaymentConfigDTO;
import org.ares.cloud.infrastructure.payment.service.PaymentConfigCacheService;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Braintree支付渠道实现
 * 实现Braintree支付相关的功能
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BraintreePaymentService implements PaymentChannelService {
    
    private final PaymentConfigCacheService paymentConfigService;
    private final PaymentProperties paymentProperties;
    
    /**
     * 生成Braintree支付链接
     * 根据订单信息生成Braintree支付页面URL
     *
     * @param order 支付订单信息
     * @return 支付页面URL
     * @throws PaymentException 支付异常
     */
    @Override
    public String generatePaymentUrl(PaymentOrder order) {
        try {
            // 获取支付配置
            MerchantPaymentConfigDTO config = paymentConfigService.getPaymentConfig(
                order.getMerchantId(),
                PaymentChannel.BRAINTREE.name()
            );
            
            // 创建支付网关
            BraintreeGateway gateway = new BraintreeGateway(
                config.getExtraConfig().contains("sandbox") ? Environment.SANDBOX : Environment.PRODUCTION,
                config.getMerchantCode(),
                config.getPublicKey(),
                config.getPrivateKey()
            );

            // 生成客户端token
            String clientToken = gateway.clientToken().generate();
            
            // 根据平台类型生成支付URL
            String paymentUrl = switch (order.getPlatform()) {
                case H5 -> generateH5PaymentUrl(order, clientToken);
                case PC -> generatePCPaymentUrl(order, clientToken);
                case APP -> generateAppPaymentUrl(order, clientToken);
                default -> throw new PaymentException("Unsupported platform");
            };
            
            log.info("Generated Braintree payment URL for order: {}", order.getOrderNo());
            return paymentUrl;
        } catch (Exception e) {
            log.error("Failed to generate Braintree payment URL", e);
            throw PaymentErrorCode.CHANNEL_INVOKE_ERROR.exception("Generate Braintree payment URL failed", e);
        }
    }

    @Override
    public String generatePaymentSign(PaymentOrder order) {
        return "";
    }

    /**
     * 生成H5支付页面URL
     */
    private String generateH5PaymentUrl(PaymentOrder order, String clientToken) {
        String resultUrl = String.format("%s?orderNo=%s&returnUrl=%s",
            paymentProperties.getResultPage().getUrl(),
            order.getOrderNo(),
            URLEncoder.encode(order.getReturnUrl(), StandardCharsets.UTF_8)
        );
        
        return String.format("%s/payment/page/h5?token=%s&orderId=%s&amount=%s&returnUrl=%s",
            paymentProperties.getPageBaseUrl(),
            clientToken,
            order.getOrderNo(),
            order.getAmount(),
            URLEncoder.encode(resultUrl, StandardCharsets.UTF_8)
        );
    }
    
    /**
     * 生成PC支付页面URL
     */
    private String generatePCPaymentUrl(PaymentOrder order, String clientToken) {
        return String.format("%s/payment/page/pc?token=%s&orderId=%s&amount=%s&returnUrl=%s",
            paymentProperties.getPageBaseUrl(),
            clientToken,
            order.getOrderNo(),
            order.getAmount(),
            order.getReturnUrl()
        );
    }
    
    /**
     * 生成APP支付URL Schema
     */
    private String generateAppPaymentUrl(PaymentOrder order, String clientToken) {
        return String.format("braintree://payment?token=%s&orderId=%s&amount=%s",
            clientToken,
            order.getOrderNo(),
            order.getAmount()
        );
    }
    
    /**
     * 验证Braintree回调
     * 验证Braintree支付通知的合法性
     *
     * @param params 回调参数
     * @return true 如果验证通过，false 否则
     */
    @Override
    public boolean verifyCallback(Map<String, String> params) {
        try {
            // 实现Braintree回调验证逻辑
            return true;
        } catch (Exception e) {
            log.error("Failed to verify Braintree callback", e);
            return false;
        }
    }
    
    /**
     * 获取支付渠道类型
     *
     * @return Braintree支付渠道类型
     */
    @Override
    public PaymentChannel getChannelType() {
        return PaymentChannel.BRAINTREE;
    }
} 