package org.ares.cloud.infrastructure.payment.service;

import org.ares.cloud.infrastructure.payment.model.MerchantPaymentConfigDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付配置缓存服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConfigCacheService {
    

    
    /**
     * 获取商户支付配置
     * 使用缓存提高性能，避免频繁调用商户服务
     *
     * @param merchantId 商户ID
     * @param channelType 支付渠道类型
     * @return 支付配置
     */
    @Cacheable(
        cacheNames = "payment_config",
        key = "#merchantId + ':' + #channelType",
        unless = "#result == null"
    )
    public MerchantPaymentConfigDTO getPaymentConfig(String merchantId, String channelType) {
        log.debug("Fetching payment config for merchant: {}, channel: {}", merchantId, channelType);
        return new MerchantPaymentConfigDTO();
       // return merchantPaymentConfigClient.getPaymentConfig(merchantId, channelType);
    }
} 