package org.ares.cloud.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.domain.model.PaymentNotifyRecord;
import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.repository.PaymentNotifyRecordRepository;
import org.ares.cloud.domain.repository.PaymentOrderRepository;
import org.ares.cloud.domain.service.PaymentChannelService;
import org.ares.cloud.domain.service.PaymentDomainService;
import org.ares.cloud.infrastructure.client.NotifyClient;
import org.ares.cloud.infrastructure.payment.exception.PaymentException;
import org.ares.cloud.infrastructure.payment.factory.PaymentChannelFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支付领域服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentDomainServiceImpl implements PaymentDomainService {

    private final PaymentOrderRepository orderRepository;
    private final PaymentNotifyRecordRepository notifyRepository;
    private final PaymentChannelFactory channelFactory;
    private final NotifyClient notifyClient;
    
    @Override
    @Transactional
    public String createPayment(PaymentOrder order) {
        // 保存订单
        orderRepository.save(order);
        
        // 获取支付渠道服务
        PaymentChannelService paymentChannel = channelFactory.getPaymentChannel(order.getChannel());

        // 生成支付链接
        return order.generatePaymentUrl(paymentChannel);
    }
    
    @Override
    @Transactional
    public void handlePaymentNotify(PaymentNotifyRecord notify) {
        // 保存通知记录
        notifyRepository.save(notify);
        
        // 更新订单状态
        PaymentOrder order = orderRepository.findByOrderNo(notify.getOrderNo())
            .orElseThrow(() -> new PaymentException("Order not found"));
        order.handlePaymentNotify(notify);
        orderRepository.save(order);
        
        // 发送通知
        retryPaymentNotify(notify);
    }
    
    @Override
    public void retryPaymentNotify(PaymentNotifyRecord notify) {
        try {
            // 调用商户通知接口
            String result = notifyClient.notify(notify.getMerchantId(), notify.getOrderNo(), notify.getNotifyParams());
            //notify.updateNotifyStatus(true, result);
        } catch (Exception e) {
            log.error("Failed to notify merchant", e);
           // notify.updateNotifyStatus(false, e.getMessage());
        }
        
        // 更新通知记录
        notifyRepository.save(notify);
    }
} 