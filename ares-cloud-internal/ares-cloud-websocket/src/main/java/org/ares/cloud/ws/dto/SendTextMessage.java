package org.ares.cloud.ws.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/11/11 01:45
 */

@Schema(description = "发送消息")
public class SendTextMessage {

    @Schema(description = "用户id")
    private String userId;
    @Schema(description = "发送消息")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
