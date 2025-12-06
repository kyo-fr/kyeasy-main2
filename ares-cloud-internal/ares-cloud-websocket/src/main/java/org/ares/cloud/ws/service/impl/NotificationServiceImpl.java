package org.ares.cloud.ws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.JsonUtils;
import org.ares.cloud.api.msg_center.command.SendNotificationCommand;
import org.ares.cloud.ws.dto.NotificationDto;
import org.ares.cloud.ws.entity.NotificationMessage;
import org.ares.cloud.ws.enums.MessageType;
import org.ares.cloud.ws.enums.WebSocketError;
import org.ares.cloud.ws.mapper.NotificationMessageMapper;
import org.ares.cloud.ws.query.NotificationQuery;
import org.ares.cloud.ws.service.NotificationService;
import org.ares.cloud.ws.service.WsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息通知服务实现类
 * @date 2024/11/11 16:30
 */
@Slf4j
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMessageMapper, NotificationMessage> implements NotificationService {
    @Override
    public NotificationMessageMapper getBaseMapper() {
        return super.getBaseMapper();
    }
    final String sysUser = "system";

    @Resource
    private WsService wsService;
    
    @Resource
    private NotificationMessageMapper notificationMessageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendTextMessage(String userId, String message, String senderId) {
        try {
            // 先保存消息到数据库
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setType(MessageType.TEXT); // 修改为文本类型
            notificationMessage.setContent(message);
            notificationMessage.setReceiverId(userId);
            notificationMessage.setSenderId(senderId);
            notificationMessage.setIsRead(0); // 0-未读
            notificationMessage.setCreateTime(LocalDateTime.now());
            notificationMessage.setUpdateTime(LocalDateTime.now());
            save(notificationMessage);
            
            // 再发送WebSocket消息
            try {
                // 创建WebSocket消息
               wsService.sendTextMessage(userId,message);
            } catch (Exception e) {
                log.error("WebSocket消息发送失败，但数据库已保存: {}", e.getMessage());
                // 不抛出异常，确保事务不回滚
            }
        } catch (Exception e) {
            log.error("发送文本消息失败: {}", e.getMessage(), e);
            throw new BusinessException(WebSocketError.SEND_MESSAGE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendNotification(SendNotificationCommand notification) {
        try {
            // 先保存消息到数据库
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setType(MessageType.NOTIFICATION);
            notificationMessage.setNotificationType(notification.getType());
            notificationMessage.setContent(notification.getContent());
            
            // 处理时间戳
            Long timestamp = notification.getTimestamp() != null ? 
                    notification.getTimestamp() : System.currentTimeMillis();
            notificationMessage.setTimestamp(timestamp);
            
            if (notification.getData() != null) {
                // 如果有附加数据，将其序列化为JSON字符串并保存到extraData字段
                String dataJson = JsonUtils.toJsonString(notification.getData());
                notificationMessage.setExtraData(dataJson);
            }
            
            notificationMessage.setReceiverId(notification.getReceiver());
            notificationMessage.setSenderId(sysUser);
            notificationMessage.setIsRead(0); // 0-未读
            notificationMessage.setCreateTime(LocalDateTime.now());
            notificationMessage.setUpdateTime(LocalDateTime.now());

            save(notificationMessage);
            
            // 再发送WebSocket消息
            try {
                // 确保DTO中的timestamp字段已设置
                if (notification.getTimestamp() == null) {
                    notification.setTimestamp(timestamp);
                }
                
                // 创建WebSocket消息
                wsService.sendNotification(convertToNotificationDto(notificationMessage));
            } catch (Exception e) {
                log.error("WebSocket通知消息发送失败，但数据库已保存: {}", e.getMessage());
                // 不抛出异常，确保事务不回滚
            }
        } catch (Exception e) {
            log.error("发送通知消息失败: {}", e.getMessage(), e);
            throw new BusinessException(WebSocketError.SEND_MESSAGE_ERROR);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean readMessage(Long messageId) {
        return notificationMessageMapper.updateReadStatus(messageId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchReadMessages(List<Long> messageIds) {
        return notificationMessageMapper.batchUpdateReadStatus(messageIds) > 0;
    }

    @Override
    public List<NotificationDto> getUnreadMessages(String receiverId) {
        LambdaQueryWrapper<NotificationMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NotificationMessage::getReceiverId, receiverId)
                .eq(NotificationMessage::getIsRead, 0)
                .orderByDesc(NotificationMessage::getCreateTime);
        List<NotificationMessage> messages = list(queryWrapper);
        return convertToNotificationDtoList(messages);
    }
    

    
    @Override
    public PageResult<NotificationDto> loadList(NotificationQuery query) {
        // 创建分页对象
        Page<NotificationMessage> page = new Page<>(query.getPage(), query.getLimit());
        Page<NotificationMessage> messagePage;
        
        // 构建查询条件
        LambdaQueryWrapper<NotificationMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NotificationMessage::getReceiverId, query.getReceiverId())
                .orderByDesc(NotificationMessage::getCreateTime);
        
        // 根据消息类型过滤
        if (query.getMessageType() != null) {
            queryWrapper.eq(NotificationMessage::getType, query.getMessageType());
        }
        
        // 根据通知类型过滤（如果需要，可能需要额外处理）
        // 这里假设通知类型是存储在extraData中的一个字段
        
        // 根据已读状态过滤
        if (query.getIsRead() != null) {
            queryWrapper.eq(NotificationMessage::getIsRead, query.getIsRead());
        }
        
        // 执行查询
        messagePage = page(page, queryWrapper);
        
        // 转换为DTO分页结果
        Page<NotificationDto> dtoPage = convertToNotificationDtoPage(messagePage);
        return new PageResult<>(dtoPage.getRecords(),dtoPage.getTotal());
    }
    /**
     * 将NotificationMessage转换为NotificationDto
     * @param message NotificationMessage实体
     * @return NotificationDto
     */
    private NotificationDto convertToNotificationDto(NotificationMessage message) {
        if (message == null) {
            return null;
        }
        NotificationDto dto = new NotificationDto();
        dto.setType(message.getType());
        dto.setNotificationType(message.getNotificationType());
        dto.setSender(message.getSenderId());
        dto.setReceiver(message.getReceiverId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());

        // 优先使用extraData字段的附加数据
        try {
            if (message.getExtraData() != null && !message.getExtraData().isEmpty()) {
                dto.setData(JsonUtils.toJsonString(message.getExtraData()));
                // 如果timestamp为空，但有extraData，则设置当前时间戳
                if (dto.getTimestamp() == null) {
                    dto.setTimestamp(System.currentTimeMillis());
                }
            }
        } catch (Exception e) {
            log.warn("解析消息附加数据失败: {}", e.getMessage());
        }
        return dto;
    }

    /**
     * 将NotificationMessage列表转换为NotificationDto列表
     * @param messages NotificationMessage列表
     * @return NotificationDto列表
     */
    private List<NotificationDto> convertToNotificationDtoList(List<NotificationMessage> messages) {
        if (messages == null || messages.isEmpty()) {
            return new ArrayList<>();
        }
        return messages.stream()
                .map(this::convertToNotificationDto)
                .collect(Collectors.toList());
    }

    /**
     * 将NotificationMessage分页结果转换为NotificationDto分页结果
     * @param messagePage NotificationMessage分页结果
     * @return NotificationDto分页结果
     */
    private Page<NotificationDto> convertToNotificationDtoPage(Page<NotificationMessage> messagePage) {
        Page<NotificationDto> dtoPage = new Page<>();
        dtoPage.setCurrent(messagePage.getCurrent());
        dtoPage.setSize(messagePage.getSize());
        dtoPage.setTotal(messagePage.getTotal());
        dtoPage.setRecords(convertToNotificationDtoList(messagePage.getRecords()));
        return dtoPage;
    }
}