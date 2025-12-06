package com.ares.cloud.pay.infrastructure.listener;

import com.ares.cloud.pay.domain.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 商户事件监听器
 * 处理商户相关的领域事件，记录日志
 */
@Slf4j
@Component
public class MerchantEventListener {

    /**
     * 商户创建事件
     */
    @Async
    @EventListener
    public void onMerchantCreated(MerchantCreatedEvent event) {
        log.info("商户创建事件: merchantId={}, merchantNo={}, merchantName={}, merchantType={}, supportedRegions={}, status={}",
                event.getPaymentId(),
                event.getMerchantNo(),
                event.getMerchantName(),
                event.getMerchantType(),
                event.getSupportedRegions(),
                event.getMerchant().getStatus());
    }

    /**
     * 商户状态变更事件
     */
    @Async
    @EventListener
    public void onMerchantStatusChanged(MerchantStatusChangedEvent event) {
        log.info("商户状态变更事件: merchantId={}, merchantNo={}, merchantName={}, merchantType={}, oldStatus={}, newStatus={}",
                event.getPaymentId(),
                event.getMerchantNo(),
                event.getMerchantName(),
                event.getMerchantType(),
                event.getOldStatus(),
                event.getNewStatus());
    }
} 