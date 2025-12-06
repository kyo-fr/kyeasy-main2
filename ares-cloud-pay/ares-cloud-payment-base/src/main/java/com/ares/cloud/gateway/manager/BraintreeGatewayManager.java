package com.ares.cloud.gateway.manager;

import com.ares.cloud.base.dto.MerchantBraintreePaymentConfigDto;
import com.ares.cloud.base.service.MerchantPaymentChannelConfigService;
import com.ares.cloud.gateway.config.BraintreeConfig;
import com.ares.cloud.gateway.manager.model.BraintreeMerchantConfig;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/5/13 12:12
 */
@Component
@Slf4j
public class BraintreeGatewayManager {
    private final ConcurrentHashMap<String, GatewayHolder> gatewayCache = new ConcurrentHashMap<>();
    @Resource
    private MerchantPaymentChannelConfigService merchantPaymentChannelConfigService;
    /**
     * 平台默认配置
     */
    @Resource
    private BraintreeConfig platformConfig;

    public BraintreeGateway getGateway(String tenantId, Environment environment) {
        // 非空校验 platformConfig
        if (platformConfig == null) {
            throw new IllegalStateException("platformConfig is not initialized");
        }

        BraintreeMerchantConfig merchantConfig = null;
        if (StringUtils.isNotBlank(tenantId)) {
            MerchantBraintreePaymentConfigDto config = merchantPaymentChannelConfigService.getBraintreePaymentConfig(tenantId);
            if (config != null) {
                merchantConfig = BraintreeMerchantConfig.builder()
                        .environment(environment)
                        .merchantId(config.getBraintreeMerchantId())
                        .publicKey(config.getBraintreePublicKey())
                        .privateKey(config.getBraintreePrivateKey())
                        .tenantId(tenantId)
                        .build();
            }
        }

        // 如果 merchantConfig 为 null，则使用平台默认配置
        if (merchantConfig == null) {
            merchantConfig = BraintreeMerchantConfig.builder()
                    .environment(environment)
                    .merchantId(platformConfig.getMerchantId())
                    .publicKey(platformConfig.getPublicKey())
                    .privateKey(platformConfig.getPrivateKey())
                    .tenantId(tenantId)
                    .build();
        }
        if (StringUtils.isBlank(tenantId)) {
            tenantId = "platform";
        }

        // 先尝试快速读取缓存中的网关
        GatewayHolder existing = gatewayCache.get(tenantId);
        if (existing != null && existing.getConfig().equals(merchantConfig)) {
            return existing.getGateway();
        }

        // 缓存缺失或配置不一致时再进行 compute 构建
        BraintreeMerchantConfig finalMerchantConfig = merchantConfig;
        String finalTenantId = tenantId;
        return gatewayCache.compute(tenantId, (k, holder) -> {
            if (holder == null || !holder.getConfig().equals(finalMerchantConfig)) {
                try {
                    BraintreeGateway gateway = buildGateway(finalMerchantConfig);
                    return new GatewayHolder(finalMerchantConfig, gateway);
                } catch (Exception e) {
                    log.error("Failed to build BraintreeGateway for tenant: {}", finalTenantId, e);
                    throw e;
                }
            }
            return holder;
        }).getGateway();
    }

    /**
     * 刷新缓存
     *
     * @param tenantId 租户ID
     */
    public void refresh(String tenantId) {
        gatewayCache.remove(tenantId);
    }

    /**
     * 构建网关
     *
     * @param config 配置信息
     * @return 网关
     */
    private BraintreeGateway buildGateway(BraintreeMerchantConfig config) {
        return new BraintreeGateway(
                config.getEnvironment(),
                config.getMerchantId(),
                config.getPublicKey(),
                config.getPrivateKey()
        );
    }

    private static class GatewayHolder {
        private final BraintreeMerchantConfig config;
        private final BraintreeGateway gateway;

        public GatewayHolder(BraintreeMerchantConfig config, BraintreeGateway gateway) {
            this.config = config;
            this.gateway = gateway;
        }

        public BraintreeMerchantConfig getConfig() {
            return config;
        }

        public BraintreeGateway getGateway() {
            return gateway;
        }
    }
}
