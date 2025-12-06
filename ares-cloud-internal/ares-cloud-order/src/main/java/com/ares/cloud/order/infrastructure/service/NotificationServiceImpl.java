package com.ares.cloud.order.infrastructure.service;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import com.ares.cloud.order.domain.service.NotificationService;
import org.ares.cloud.api.msg_center.NotificationServerClient;
import org.ares.cloud.api.msg_center.command.SendNotificationCommand;
import org.ares.cloud.api.msg_center.enums.NotificationType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知服务实现类
 */
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    @Resource
    private NotificationServerClient notificationServerClient;

    @Override
    public void notifyMerchant(String merchantId,String orderId, String title, String content) {
        try  {
            // 参数校验
            if (merchantId == null || merchantId.isEmpty()) {
                log.warn("⚠️ merchantId 为空，无法发送通知: orderId={}, title={}", orderId, title);
                return;
            }
            
            log.info("📤 准备发送商户通知: merchantId={}, orderId={}, title={}", merchantId, orderId, title);
            
            Map<String,String> data = new HashMap<>();
            data.put("orderId", orderId);
            data.put("message", content);
            
            SendNotificationCommand command = new SendNotificationCommand();
            command.setType(NotificationType.USER_ORDER);
            command.setReceiver(merchantId);
            command.setTitle(title);
            command.setContent(JSON.toJSONString(data));
            
            log.debug("📤 发送通知命令: receiver={}, type={}, title={}, content={}", 
                    command.getReceiver(), command.getType(), command.getTitle(), command.getContent());
            
            notificationServerClient.sendNotification(command);
            
            log.info("✅ 商户通知发送成功: merchantId={}, orderId={}", merchantId, orderId);
        } catch (Exception e) {
            log.error("❌ 发送商户通知失败: merchantId={}, orderId={}, error={}", 
                    merchantId, orderId, e.getMessage(), e);
        }
    }

    @Override
    public void notifyRider(String riderId, String orderId,String title, String content) {
        log.debug("Notify rider: riderId={}, title={}, content={}", 
            riderId, title, content);
    }

    @Override
    public void notifyUser(String orderId, String title, String content) {
        log.debug("Notify user: orderId={}, title={}, content={}", 
            orderId, title, content);
    }
}