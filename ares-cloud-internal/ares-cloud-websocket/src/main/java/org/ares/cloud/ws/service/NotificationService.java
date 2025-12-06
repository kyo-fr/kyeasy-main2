package org.ares.cloud.ws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.api.msg_center.command.SendNotificationCommand;
import org.ares.cloud.ws.dto.NotificationDto;
import org.ares.cloud.ws.entity.NotificationMessage;
import org.ares.cloud.ws.query.NotificationQuery;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息通知服务接口
 * @date 2024/11/11 16:30
 */
public interface NotificationService extends IService<NotificationMessage> {

    /**
     * 发送文本消息
     * @param userId 用户id
     * @param message 消息
     * @param senderId 发送者ID
     */
    void sendTextMessage(String userId, String message, String senderId);

    /**
     * 发送统一通知
     * @param notification 通知DTO
     */
    void sendNotification(SendNotificationCommand notification);


    /**
     * 查询消息
     * @param query 接收者ID
     */
    PageResult<NotificationDto> loadList(NotificationQuery query);

    /**
     * 标记消息为已读
     * @param messageId 消息ID
     * @return 是否成功
     */
    boolean readMessage(Long messageId);

    /**
     * 批量标记消息为已读
     * @param messageIds 消息ID列表
     * @return 是否成功
     */
    boolean batchReadMessages(List<Long> messageIds);

    /**
     * 获取未读消息列表
     * @param receiverId 接收者ID
     * @return 未读消息列表
     */
    List<NotificationDto> getUnreadMessages(String receiverId);
}