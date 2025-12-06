package org.ares.cloud.api.msg_center.fallback;

import org.ares.cloud.api.msg_center.NotificationServerClient;
import org.ares.cloud.api.msg_center.command.SendNotificationCommand;
import org.ares.cloud.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: NotificationServerClient 的降级处理
 * 当消息中心服务不可用时抛出 ServiceUnavailableException
 * @date 2024/11/11 17:20
 */
@Component
public class WsServerClientFallback implements NotificationServerClient {

    private static final String SERVICE_NAME = "message-center-server";

    @Override
    public String sendNotification(SendNotificationCommand notification) {
        throw new ServiceUnavailableException(SERVICE_NAME, "sendNotification");
    }
}
