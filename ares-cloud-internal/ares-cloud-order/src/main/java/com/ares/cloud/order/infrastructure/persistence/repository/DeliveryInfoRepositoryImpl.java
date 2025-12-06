package com.ares.cloud.order.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.utils.DateUtils;
import com.ares.cloud.order.domain.model.valueobject.DeliveryInfo;
import com.ares.cloud.order.domain.repository.DeliveryInfoRepository;
import com.ares.cloud.order.infrastructure.persistence.converter.DeliveryInfoConverter;
import com.ares.cloud.order.infrastructure.persistence.entity.DeliveryInfoDO;
import com.ares.cloud.order.infrastructure.persistence.mapper.DeliveryInfoMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryInfoRepositoryImpl implements DeliveryInfoRepository {

    private final DeliveryInfoMapper deliveryInfoMapper;
    private final DeliveryInfoConverter deliveryInfoConverter;
    
    @Override
    public void save(String orderId, DeliveryInfo deliveryInfo) {
        DeliveryInfoDO deliveryInfoDO = deliveryInfoConverter.toDeliveryInfoDO(deliveryInfo);
        deliveryInfoDO.setOrderId(orderId);
        deliveryInfoDO.setDeliveryStartTime(DateUtils.getCurrentTimestampInUTC());
        deliveryInfoDO.setDeleted(0);
        deliveryInfoMapper.insert(deliveryInfoDO);
    }
    
    @Override
    public DeliveryInfo findByOrderId(String orderId) {
        DeliveryInfoDO deliveryInfoDO = deliveryInfoMapper.selectOne(
            new LambdaQueryWrapper<DeliveryInfoDO>()
                .eq(DeliveryInfoDO::getOrderId, orderId)
                .eq(DeliveryInfoDO::getDeleted, 0)
        );
        
        if (deliveryInfoDO == null) {
            return null;
        }
        
        return deliveryInfoConverter.toDeliveryInfo(deliveryInfoDO);
    }
    
    @Override
    public void update(String orderId, DeliveryInfo deliveryInfo) {
        DeliveryInfoDO deliveryInfoDO = deliveryInfoConverter.toDeliveryInfoDO(deliveryInfo);
        deliveryInfoDO.setOrderId(orderId);
        
        deliveryInfoMapper.update(deliveryInfoDO,
            new LambdaQueryWrapper<DeliveryInfoDO>()
                .eq(DeliveryInfoDO::getOrderId, orderId)
                .eq(DeliveryInfoDO::getDeleted, 0)
        );
    }
} 