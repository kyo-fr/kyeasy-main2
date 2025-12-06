package com.ares.cloud.order.domain.service;

/**
 * 通知服务接口
 */
public interface NotificationService {
    /**
     * 通知商户
     */
    void notifyMerchant(String merchantId,String orderId, String title, String content);
    
    /**
     * 通知骑手
     */
    void notifyRider(String riderId,String orderId, String title, String content);
    
    /**
     * 通知用户
     */
    void notifyUser(String orderId, String title, String content);
} 