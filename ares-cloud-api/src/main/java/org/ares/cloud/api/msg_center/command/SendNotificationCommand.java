package org.ares.cloud.api.msg_center.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ares.cloud.api.msg_center.enums.NotificationType;

import java.io.Serializable;

/**
 * @author hugo
 * @version 1.0
 * @description: 通知DTO
 * @date 2024/11/11 16:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "通知DTO")
public class SendNotificationCommand implements Serializable {
    
    /**
     * 通知类型
     */
    @Schema(description = "通知类型", example = "USER_ORDER",  requiredMode = Schema.RequiredMode.REQUIRED)
    private NotificationType type;
    
    /**
     * 接收者ID
     */
    @Schema(description = "接收者ID", example = "user123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String receiver;
    
    /**
     * 消息内容
     */
    @Schema(description = "消息内容", example = "您有一条新的订单通知", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
    /**
     * 消息内容
     */
    @Schema(description = "消息头", example = "您有一条新的订单通知")
    private String title;
    /**
     * 附加数据
     */
    @Schema(description = "附加数据，必须是可序列化和反序列化的对象", example = "{\"orderId\": \"123456\", \"amount\": 100.00}")
    private Object data;

    /**
     * 时间戳（毫秒）
     */
    @Schema(description = "时间戳（毫秒）可选", example = "1636627200000")
    private Long timestamp;
}