package org.ares.cloud.ws.internal;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.exception.RpcCallException;
import org.ares.cloud.api.msg_center.command.SendNotificationCommand;
import org.ares.cloud.ws.service.NotificationService;
import org.ares.cloud.ws.service.WsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hugo
 * @version 1.0
 * @description: 通知内部服务
 * @date 2024/11/11 17:03
 */
@RestController
@RequestMapping("/internal/v1/notification")
public class NotificationInternalController {

    @Resource
    private WsService wsService;
    
    @Resource
    private NotificationService notificationService;

    /**
     * 统一通知接口 - 处理各类通知
     * @param notification 通知对象
     * @return 处理结果
     */
    @Hidden
    @PostMapping("/send")
    public String sendNotification(@RequestBody SendNotificationCommand notification) {
        try {
            notificationService.sendNotification(notification);
            return "success";
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }
}
