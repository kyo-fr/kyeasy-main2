package com.ares.cloud.pay.infrastructure.listener;

import com.ares.cloud.pay.application.handlers.MerchantCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 商户信息变更监听器
 * 监听商户服务中的商户信息变更事件，自动同步到支付系统
 * 
 * 注意：目前商户信息变更事件还未定义，此监听器暂时注释
 * 当商户服务发布相应事件时，可以取消注释并实现具体逻辑
 */
@Slf4j
@Component
public class MerchantInfoChangeListener {

    @Autowired
    private MerchantCommandHandler merchantCommandHandler;

    /**
     * 监听商户信息变更事件
     * 异步处理，避免阻塞主流程
     * 
     * 注意：目前商户信息变更事件还未定义，此方法暂时注释
     * 当商户服务发布相应事件时，可以取消注释并实现具体逻辑
     * 
     * @param merchantId 商户ID
     */
    /*
    @Async
    @EventListener
    public void handleMerchantInfoChange(String merchantId) {
        try {
            log.info("收到商户信息变更事件，商户ID: {}", merchantId);
            
            // 调用商户命令处理器同步商户信息
            String result = merchantCommandHandler.syncMerchantInfo(merchantId);
            
            log.info("商户信息同步成功，商户ID: {}, 结果: {}", merchantId, result);
        } catch (Exception e) {
            log.error("商户信息同步失败，商户ID: {}, 错误信息: {}", merchantId, e.getMessage(), e);
        }
    }
    */
} 