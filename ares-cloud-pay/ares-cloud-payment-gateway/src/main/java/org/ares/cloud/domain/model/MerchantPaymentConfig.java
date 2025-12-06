package org.ares.cloud.domain.model;

import lombok.Data;
import java.util.Map;

/**
 * MerchantPaymentConfig类用于存储商户支付配置信息。
 * 包含商户的基本信息以及各个支付渠道的具体配置。
 */
@Data
public class MerchantPaymentConfig {
    /**
     * 商户ID，用于唯一标识一个商户。
     */
    private String merchantId;

    /**
     * 商户名称，表示商户的名称。
     */
    private String merchantName;

    /**
     * 签名密钥，用于生成签名以确保支付请求的安全性。
     */
    private String signKey;

    /**
     * 渠道配置映射，包含不同支付类型的渠道配置信息。
     * 键为支付类型，值为对应的ChannelConfig对象。
     */
    private Map<PaymentTransaction.PaymentType, ChannelConfig> channelConfigs;

    /**
     * ChannelConfig类用于存储每个支付渠道的具体配置信息。
     */
    @Data
    public static class ChannelConfig {
        /**
         * 渠道ID，用于标识具体的支付渠道。
         */
        private String channelId;

        /**
         * 应用ID，通常由第三方支付平台提供。
         */
        private String appId;

        /**
         * 第三方支付平台的商户号，用于在支付平台上标识商户身份。
         */
        private String merchantCode;

        /**
         * 私钥，用于加密和签名操作。
         */
        private String privateKey;

        /**
         * 公钥，用于解密和验证签名。
         */
        private String publicKey;

        /**
         * 是否启用该支付渠道。
         */
        private boolean enabled;

        /**
         * 额外配置信息，用于存储其他自定义配置项。
         */
        private Map<String, String> extraConfig;
    }
}
