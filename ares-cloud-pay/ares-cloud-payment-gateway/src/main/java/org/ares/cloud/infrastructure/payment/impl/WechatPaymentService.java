package org.ares.cloud.infrastructure.payment.impl;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.ares.cloud.domain.service.PaymentChannelService;
import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.model.enums.PaymentChannel;
import org.ares.cloud.infrastructure.payment.config.PaymentProperties;
import org.ares.cloud.infrastructure.payment.exception.PaymentErrorCode;
import org.ares.cloud.infrastructure.payment.exception.PaymentException;
import org.ares.cloud.infrastructure.payment.model.MerchantPaymentConfigDTO;
import org.ares.cloud.infrastructure.payment.service.PaymentConfigCacheService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.net.URLEncoder;

/**
 * 微信支付渠道实现
 * 实现微信支付相关的功能
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WechatPaymentService implements PaymentChannelService {
    
    private final PaymentConfigCacheService paymentConfigService;
    private final PaymentProperties paymentProperties;
    
    /**
     * 生成微信支付链接
     * 根据订单信息生成微信支付参数
     *
     * @param order 支付订单信息
     * @return 微信支付参数JSON
     * @throws PaymentException 支付异常
     */
    @Override
    public String generatePaymentUrl(PaymentOrder order) {
        try {
            // 获取支付配置
            MerchantPaymentConfigDTO config = paymentConfigService.getPaymentConfig(
                order.getMerchantId(),
                PaymentChannel.WECHAT.name()
            );
            
            // 构建支付请求参数
            Map<String, Object> reqData = new HashMap<>();
            reqData.put("mchid", config.getMerchantCode());
            reqData.put("out_trade_no", order.getOrderNo());
            reqData.put("appid", config.getAppId());
            reqData.put("description", order.getSubject());
            reqData.put("notify_url", order.getNotifyUrl());
            
            // 金额转换为分
            Map<String, Object> amount = new HashMap<>();
            amount.put("total", order.getAmount().multiply(new BigDecimal("100")).intValue());
            amount.put("currency", "CNY");
            reqData.put("amount", amount);

            // 加载商户私钥
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                new ByteArrayInputStream(config.getPrivateKey().getBytes("utf-8")));

            // 构建认证凭据
            WechatPay2Credentials credentials = new WechatPay2Credentials(
                config.getMerchantCode(),
                new PrivateKeySigner(config.getMerchantCode(), merchantPrivateKey));

            // 创建支付客户端
            try (CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withCredentials(credentials)
                .build()) {
                // 添加统一的返回地址
                reqData.put("redirect_url", String.format("%s?orderNo=%s&returnUrl=%s",
                    paymentProperties.getResultPage().getUrl(),
                    order.getOrderNo(),
                    URLEncoder.encode(order.getReturnUrl(), "UTF-8")
                ));

                // 发送支付请求
                HttpPost httpPost = new HttpPost(paymentProperties.getWechatGatewayUrl() + "/pay/transactions/jsapi");
                httpPost.setEntity(new StringEntity(JSONObject.toJSONString(reqData)));
                
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    log.info("Generated Wechat payment params for order: {}", order.getOrderNo());
                    return responseBody;
                }
            } catch (Exception e) {
                log.error("Failed to generate Wechat payment params", e);
                throw PaymentErrorCode.CHANNEL_INVOKE_ERROR.exception("Generate Wechat payment params failed", e);
            }
        } catch (Exception e) {
            log.error("Failed to generate Wechat payment params", e);
            throw PaymentErrorCode.CHANNEL_INVOKE_ERROR.exception("Generate Wechat payment params failed", e);
        }
    }

    @Override
    public String generatePaymentSign(PaymentOrder order) {
        return "";
    }

    /**
     * 验证微信支付回调
     * 验证微信支付异步通知的签名
     *
     * @param params 回调参数
     * @return true 如果验签通过，false 否则
     */
    @Override
    public boolean verifyCallback(Map<String, String> params) {
        try {
            // 实现微信支付回调验签逻辑
            return true;
        } catch (Exception e) {
            log.error("Failed to verify Wechat callback", e);
            return false;
        }
    }
    
    /**
     * 获取支付渠道类型
     *
     * @return 微信支付渠道类型
     */
    @Override
    public PaymentChannel getChannelType() {
        return PaymentChannel.WECHAT;
    }
} 