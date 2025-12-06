package org.ares.cloud.ws.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: 认证的错误
 * @date 2024/9/30 18:46
 */
public enum WebSocketError implements BaseErrorInfoInterface {
    SEND_MESSAGE_ERROR("send_message_err"),
    USER_NOT_ONLINE("user_not_online"),
    MESSAGE_IS_EMPTY("message_is_empty");
    private final Integer code;
    private final String messageKey;
    WebSocketError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
    WebSocketError(String message) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = message;
    }
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
