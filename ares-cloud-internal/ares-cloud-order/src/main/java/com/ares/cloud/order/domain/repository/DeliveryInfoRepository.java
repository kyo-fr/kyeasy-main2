package com.ares.cloud.order.domain.repository;

import com.ares.cloud.order.domain.model.valueobject.DeliveryInfo;

public interface DeliveryInfoRepository {
    
    void save(String orderId, DeliveryInfo deliveryInfo);
    
    DeliveryInfo findByOrderId(String orderId);
    
    void update(String orderId, DeliveryInfo deliveryInfo);
} 