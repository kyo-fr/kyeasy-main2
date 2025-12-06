package org.ares.cloud.infrastructure.payment.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.ares.cloud.domain.service.PaymentChannelService;
import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.model.enums.PaymentChannel;
import org.ares.cloud.infrastructure.payment.exception.PaymentException;
import org.ares.cloud.infrastructure.payment.exception.PaymentErrorCode;
import org.ares.cloud.infrastructure.payment.model.MerchantPaymentConfigDTO;
import org.ares.cloud.infrastructure.payment.service.PaymentConfigCacheService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.infrastructure.payment.config.PaymentProperties;
import com.alipay.api.internal.util.AlipaySignature;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付渠道实现
 * 实现支付宝支付相关的功能
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlipayPaymentService implements PaymentChannelService {
    
    private final PaymentConfigCacheService paymentConfigService;
    private final PaymentProperties paymentProperties;
    
    /**
     * 生成支付宝支付链接
     * 根据订单信息生成支付宝支付表单
     *
     * @param order 支付订单信息
     * @return 支付宝支付表单HTML
     * @throws PaymentException 支付异常
     */
    @Override
    public String generatePaymentUrl(PaymentOrder order) {
        try {
            // 获取支付配置
            MerchantPaymentConfigDTO config = paymentConfigService.getPaymentConfig(
                order.getMerchantId(),
                PaymentChannel.ALIPAY.name()
            );
            
            // 创建支付宝客户端
            AlipayClient alipayClient = new DefaultAlipayClient(
                paymentProperties.getAlipayGatewayUrl(),
                config.getAppId(),
                config.getPrivateKey(),
                "json",
                "UTF-8",
                config.getPublicKey(),
                "RSA2"
            );

            // 创建支付请求
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            String resultUrl = String.format("%s?orderNo=%s&returnUrl=%s",
                paymentProperties.getResultPage().getUrl(),
                order.getOrderNo(),
                URLEncoder.encode(order.getReturnUrl(), "UTF-8")
            );
            alipayRequest.setReturnUrl(resultUrl);
            alipayRequest.setNotifyUrl(order.getNotifyUrl());
            
            // 构建业务参数
            Map<String, Object> bizContent = new HashMap<>();
            bizContent.put("out_trade_no", order.getOrderNo());
            bizContent.put("total_amount", order.getAmount().toString());
            bizContent.put("subject", order.getSubject());
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
            
            alipayRequest.setBizContent(JSONObject.toJSONString(bizContent));
            
            // 执行请求
            AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()) {
                log.info("Generated Alipay payment form for order: {}", order.getOrderNo());
                return response.getBody();
            }
            
            log.error("Failed to generate Alipay payment form: {}", response.getSubMsg());
            throw PaymentErrorCode.CHANNEL_INVOKE_ERROR.exception(response.getSubMsg(), null);
        } catch (Exception e) {
            log.error("Failed to generate Alipay payment form", e);
            throw new PaymentException("Generate Alipay payment form failed", e);
        }
    }

    @Override
    public String generatePaymentSign(PaymentOrder order) {
        return "";
    }

    /**
     * 验证支付宝回调
     * 验证支付宝异步通知的签名
     *
     * @param params 回调参数
     * @return true 如果验签通过，false 否则
     */
    @Override
    public boolean verifyCallback(Map<String, String> params) {
        try {
            MerchantPaymentConfigDTO config = paymentConfigService.getPaymentConfig(
                params.get("merchantId"),
                PaymentChannel.ALIPAY.name()
            );
            
            return AlipaySignature.rsaCheckV1(
                params,
                config.getPublicKey(),
                "UTF-8",
                "RSA2"
            );
        } catch (Exception e) {
            log.error("Failed to verify Alipay callback", e);
            return false;
        }
    }
    
    /**
     * 获取支付渠道类型
     *
     * @return 支付宝渠道类型
     */
    @Override
    public PaymentChannel getChannelType() {
        return PaymentChannel.ALIPAY;
    }
} 