package org.ares.cloud.api.msg_center;

import org.ares.cloud.api.msg_center.command.SendNotificationCommand;
import org.ares.cloud.api.msg_center.fallback.WsServerClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息客户端
 * @date 2024/11/11 17:16
 */
@FeignClient(name = "message-center-server",configuration = FeignConfig.class,fallback = WsServerClientFallback.class)
public interface NotificationServerClient {

    /**
     * 发送通知
     * @param notification 通知
     * @return id
     */
    @PostMapping("/internal/v1/notification/send")
    String sendNotification(@RequestBody SendNotificationCommand notification);
}
