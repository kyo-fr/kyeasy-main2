package org.ares.cloud.ws.service;

import org.ares.cloud.ws.dto.NotificationDto;

/**
 * @author hugo
 * @version 1.0
 * @description: WebSocket服务接口
 * @date 2024/11/11 01:19
 */
public interface WsService {
    /**
     * 发送文本消息
     * @param userId 用户id
     * @param message 消息
     */
    void sendTextMessage(String userId, String message);

    /**
     * 发送通知消息（统一处理订单通知和审批通知）
     * @param notification 通知对象
     */
    void sendNotification(NotificationDto notification);
}
