package org.ares.cloud.domain.repository;

import org.ares.cloud.domain.model.MerchantPaymentChannel;

import java.util.Optional;

/**
 * 商户支付渠道仓储接口
 * 提供商户支付渠道配置的数据访问能力
 */
public interface MerchantPaymentChannelRepository {
    
    /**
     * 根据商户ID和渠道类型查询支付渠道配置
     *
     * @param merchantId 商户ID
     * @param channelType 支付渠道类型
     * @return 支付渠道配置，如果不存在则返回空
     */
    Optional<MerchantPaymentChannel> findByMerchantIdAndChannelType(String merchantId, String channelType);
    
    /**
     * 保存商户支付渠道配置
     *
     * @param channel 支付渠道配置
     * @return 保存后的支付渠道配置
     */
    MerchantPaymentChannel save(MerchantPaymentChannel channel);
    
    /**
     * 删除商户支付渠道配置
     *
     * @param merchantId 商户ID
     * @param channelType 支付渠道类型
     */
    void deleteByMerchantIdAndChannelType(String merchantId, String channelType);
} 