package org.ares.cloud.infrastructure.persistence.converter;

import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.model.enums.PaymentChannel;
import org.ares.cloud.domain.model.enums.PaymentStatus;
import org.ares.cloud.domain.model.enums.PlatformType;
import org.ares.cloud.infrastructure.persistence.entity.PaymentOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentOrderConverter {

    public PaymentOrderEntity toEntity(PaymentOrder order) {
        if (order == null) {
            return null;
        }
        
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setOrderNo(order.getOrderNo());
        entity.setMerchantId(order.getMerchantId());
        entity.setChannel(order.getChannel().name());
        entity.setAmount(order.getAmount());
        entity.setSubject(order.getSubject());
        entity.setStatus(order.getStatus().name());
        entity.setPlatform(order.getPlatform().name());
        entity.setReturnUrl(order.getReturnUrl());
        entity.setNotifyUrl(order.getNotifyUrl());
        entity.setChannelTradeNo(order.getChannelTradeNo());
        entity.setExpireTime(order.getExpireTime());
        entity.setCreateTime(order.getCreateTime());
        entity.setPayTime(order.getPayTime());
        return entity;
    }

    public PaymentOrder toDomain(PaymentOrderEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return PaymentOrder.builder()
            .orderNo(entity.getOrderNo())
            .merchantId(entity.getMerchantId())
            .channel(PaymentChannel.valueOf(entity.getChannel()))
            .amount(entity.getAmount())
            .subject(entity.getSubject())
            .status(PaymentStatus.valueOf(entity.getStatus()))
            .platform(PlatformType.valueOf(entity.getPlatform()))
            .returnUrl(entity.getReturnUrl())
            .notifyUrl(entity.getNotifyUrl())
            .channelTradeNo(entity.getChannelTradeNo())
            .expireTime(entity.getExpireTime())
            .createTime(entity.getCreateTime())
            .payTime(entity.getPayTime())
            .build();
    }
} 