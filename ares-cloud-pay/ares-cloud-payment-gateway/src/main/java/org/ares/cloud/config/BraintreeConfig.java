package org.ares.cloud.config;

/**
 * @author hugo
 * @version 1.0
 * @description: braintree 配置
 * @date 2024/12/24 15:36
 */
public class BraintreeConfig {
    /**
     * 商户id
     */
   //  @Value("${ares.cloud.pay.braintree.merchant_id:''}")
    private  String merchantId;
    /**
     * 公钥
     */
    // @Value("${ares.cloud.pay.braintree.public_key:''}")
    private  String publicKey;
    /**
     * 私钥
     */
    // @Value("${ares.cloud.pay.braintree.private_key:''}")
    private  String privateKey;

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
