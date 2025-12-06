package org.ares.cloud.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.ares.cloud.domain.model.MerchantPaymentChannel;
import org.ares.cloud.domain.repository.MerchantPaymentChannelRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 商户支付渠道仓储实现类
 */
@Repository
@RequiredArgsConstructor
public class MerchantPaymentChannelRepositoryImpl implements MerchantPaymentChannelRepository {


    /**
     * 根据商户ID和渠道类型查询支付渠道配置
     *
     * @param merchantId 商户ID
     * @param channelType 支付渠道类型
     * @return 支付渠道配置，如果不存在则返回空
     */
    @Override
    public Optional<MerchantPaymentChannel> findByMerchantIdAndChannelType(String merchantId, String channelType) {
//        MerchantPaymentChannelEntity entity = channelMapper.selectByMerchantIdAndChannelType(merchantId, channelType);
//        return Optional.ofNullable(entity)
//            .map(channelConverter::toDomain);
        MerchantPaymentChannel channel = MerchantPaymentChannel.builder().build();
        return Optional.of(channel);
    }

    /**
     * 保存商户支付渠道配置
     *
     * @param channel 支付渠道配置
     * @return 保存后的支付渠道配置
     */
    @Override
    public MerchantPaymentChannel save(MerchantPaymentChannel channel) {
       // MerchantPaymentChannelEntity entity = channelConverter.toEntity(channel);
//        if (channelMapper.selectByMerchantIdAndChannelType(channel.getMerchantId(), channel.getChannelType()) == null) {
//            channelMapper.insert(entity);
//        } else {
//            channelMapper.updateByMerchantIdAndChannelType(entity);
//        }
//        return channel;
        return null;
    }

    /**
     * 删除商户支付渠道配置
     *
     * @param merchantId 商户ID
     * @param channelType 支付渠道类型
     */
    @Override
    public void deleteByMerchantIdAndChannelType(String merchantId, String channelType) {
       // channelMapper.deleteByMerchantIdAndChannelType(merchantId, channelType);
    }
} 