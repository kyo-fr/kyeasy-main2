package org.ares.cloud.ws.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.ws.dto.NotificationDto;
import org.ares.cloud.ws.dto.WsMessage;
import org.ares.cloud.ws.enums.MessageType;
import org.ares.cloud.ws.handler.AresWebSocketHandler;
import org.ares.cloud.ws.service.WsService;
import org.springframework.stereotype.Service;

/**
 * @author hugo
 * @version 1.0
 * @description: WebSocket服务实现类
 * @date 2024/11/11 01:21
 */
@Slf4j
@Service
public class WsServiceImpl implements WsService {

    @Resource
    private AresWebSocketHandler aresWebSocketHandler;

    @Override
    public void sendTextMessage(String userId, String message) {
        try {
            // 创建WebSocket消息
            WsMessage<String> msg = new WsMessage<>(MessageType.TEXT, message);
            // 通过WebSocketHandler发送消息
            aresWebSocketHandler.sendMessage(userId, msg);
        } catch (Exception e) {
            log.error("发送文本消息失败: {}", e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Override
    public  void sendNotification(NotificationDto notification) {
        try {
            // 统一调用NotificationService的sendNotification方法
            WsMessage<NotificationDto> msg = new WsMessage<>(MessageType.NOTIFICATION, notification);
            aresWebSocketHandler.sendMessage(notification.getReceiver(),msg);
        } catch (Exception e) {
            log.error("发送通知失败: {}", e.getMessage(), e);
            throw new BusinessException(e);
        }
    }
}
