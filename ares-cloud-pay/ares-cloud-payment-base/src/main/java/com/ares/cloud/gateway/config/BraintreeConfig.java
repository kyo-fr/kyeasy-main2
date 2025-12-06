package com.ares.cloud.gateway.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0.0
 * @description TODO
 * @date 2024-03-08 11:27
 */
@Configuration
@Slf4j
public class BraintreeConfig {
    /**
     * 商户id
     */
    @Value("${ares.cloud.pay.braintree.merchant_id:''}")
    private  String merchantId;
    /**
     * 公钥
     */
    @Value("${ares.cloud.pay.braintree.public_key:''}")
    private  String publicKey;
    /**
     * 私钥
     */
    @Value("${ares.cloud.pay.braintree.private_key:''}")
    private  String privateKey;
    /**
     *
     * Braintree沙盒测试网管
     *
     */
    @Bean("sandBoxBraintreeGateway")
    public BraintreeGateway sandBoxBraintreeGateway() {
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                this.merchantId,
                this.publicKey,
                this.privateKey
        );
        return gateway;
    }
    /**
     *
     * Braintree网关
     *
     */
    @Bean("braintreeGateway")
    public BraintreeGateway braintreeGateway() {
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.PRODUCTION,
                this.merchantId,
                this.publicKey,
                this.privateKey
        );
        return gateway;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
