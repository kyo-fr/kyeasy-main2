package org.ares.cloud.ws.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.ws.dto.SendTextMessage;
import org.ares.cloud.ws.service.WsService;
import org.springframework.web.bind.annotation.*;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/11/11 01:25
 */
@RestController
@RequestMapping("/api/v1/msg")
@Tag(name="用户")
public class MessageController {
    @Resource
    private WsService wsService;

    @PostMapping("send_text_msg")
    @Operation(summary = "发送文字消息")
    public Result<String> get(@RequestBody SendTextMessage sendTextMessage){
        wsService.sendTextMessage(sendTextMessage.getUserId(),sendTextMessage.getMessage());
        return Result.success("发送成功");
    }
}
