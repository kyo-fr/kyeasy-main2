package org.ares.cloud.ws.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.query.PageQuery;
import org.ares.cloud.ws.enums.MessageType;
import org.ares.cloud.api.msg_center.enums.NotificationType;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息通知查询参数
 * @date 2025/5/18 14:03
 */
@Data
public class NotificationQuery extends PageQuery {

    @Schema(description = "消息类型")
    private MessageType messageType;
    
    @Schema(description = "通知类型")
    private NotificationType notificationType;
    
    @Schema(description = "是否已读 0-未读 1-已读")
    private Integer isRead;
    
    @Schema(description = "接收者ID")
    private String receiverId;
}
