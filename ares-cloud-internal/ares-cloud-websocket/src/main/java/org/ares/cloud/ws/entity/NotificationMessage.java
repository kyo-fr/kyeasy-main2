package org.ares.cloud.ws.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.ares.cloud.ws.enums.MessageType;
import org.ares.cloud.api.msg_center.enums.NotificationType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息通知实体
 * @date 2024/11/11 16:30
 */
@Data
@TableName("ws_notification_message")
public class NotificationMessage implements Serializable {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    
    /**
     * 消息类型
     */
    private MessageType type;
    
    /**
     * 通知类型
     */
    private NotificationType notificationType;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 接收者用户ID
     */
    private String receiverId;
    
    /**
     * 发送者用户ID
     */
    private String senderId;
    
    /**
     * 是否已读 0-未读 1-已读
     */
    private Integer isRead;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 附加数据时间戳（毫秒）
     */
    private Long timestamp;
    
    /**
     * 附加数据JSON字符串
     */
    private String extraData;
}