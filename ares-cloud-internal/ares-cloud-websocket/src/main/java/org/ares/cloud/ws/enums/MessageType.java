package org.ares.cloud.ws.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息类型
 * @date 2024/11/11 16:31
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
@Schema(description = "消息类型枚举")
public enum MessageType {
    @Schema(description = "文本消息")
    TEXT,
    
    @Schema(description = "通知消息")
    NOTIFICATION,
    
    @Schema(description = "心跳消息")
    HEARTBEAT,
    
    @Schema(description = "客户端发送的心跳检测")
    PING,
    
    @Schema(description = "服务端响应的心跳检测")
    PONG;
    
    /**
     * 将枚举序列化为小写字符串
     * @return 小写的枚举名称
     */
    @JsonValue
    public String getValue() {
        return this.name().toLowerCase();
    }
}
