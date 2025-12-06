package org.ares.cloud.ws.dto;

import lombok.Builder;
import lombok.Data;
import org.ares.cloud.ws.enums.MessageType;

import java.io.Serializable;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息
 * @date 2024/11/11 16:30
 */
@Data
@Builder
public class WsMessage<T> implements Serializable {
    /**
     * 消息类型
     */
    private MessageType type;
    /**
     * 消息类型
     */
    private T msg;

    public WsMessage(MessageType type, T msg) {
        this.type = type;
        this.msg = msg;
    }
}
