package org.ares.cloud.domain.service;

import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.model.PaymentNotifyRecord;

/**
 * 支付领域服务接口
 */
public interface PaymentDomainService {
    
    /**
     * 创建支付订单
     *
     * @param order 支付订单
     * @return 支付链接
     */
    String createPayment(PaymentOrder order);
    
    /**
     * 处理支付通知
     *
     * @param notify 支付通知记录
     */
    void handlePaymentNotify(PaymentNotifyRecord notify);
    
    /**
     * 重试支付通知
     *
     * @param notify 支付通知记录
     */
    void retryPaymentNotify(PaymentNotifyRecord notify);
} 