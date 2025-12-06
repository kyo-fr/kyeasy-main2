package org.ares.cloud.ws.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.ws.dto.NotificationDto;
import org.ares.cloud.ws.entity.NotificationMessage;
import org.ares.cloud.ws.query.NotificationQuery;
import org.ares.cloud.ws.service.NotificationService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: 消息通知控制器
 * @date 2024/11/11 16:30
 */
@RestController
@RequestMapping("/api/v1/notification")
@Tag(name = "消息通知")
public class NotificationController {

    @Resource
    private NotificationService notificationService;

    @GetMapping("/list")
    @Operation(summary = "查询用户消息列表")
    public Result<PageResult<NotificationDto>> getMessageList(@ParameterObject @Valid NotificationQuery query) {
        // 如果查询参数中没有设置接收者ID，则从上下文中获取
        if (StringUtils.isEmpty(query.getReceiverId())) {
            query.setReceiverId(ApplicationContext.getUserId());
        }
        PageResult<NotificationDto> notificationDtoPageResult = notificationService.loadList(query);
        return Result.success(notificationDtoPageResult);
    }

    @GetMapping("/unread")
    @Operation(summary = "查询用户未读消息列表")
    public Result<List<NotificationDto>> getUnreadMessages() {
        // 从上下文中获取用户ID
        String userId = ApplicationContext.getUserId();
        List<NotificationDto> messages = notificationService.getUnreadMessages(userId);
        return Result.success(messages);
    }
    
    @GetMapping("/unread/count")
    @Operation(summary = "查询用户未读消息数量")
    public Result<Integer> getUnreadMessageCount() {
        // 从上下文中获取用户ID
        String userId = ApplicationContext.getUserId();
        List<NotificationDto> messages = notificationService.getUnreadMessages(userId);
        return Result.success(messages != null ? messages.size() : 0);
    }

    @PutMapping("/read/{messageId}")
    @Operation(summary = "标记消息为已读")
    public Result<String> readMessage(
            @Parameter(description = "消息ID") @PathVariable Long messageId) {
       notificationService.readMessage(messageId);
        return Result.success();
    }

    @PutMapping("/read/batch")
    @Operation(summary = "批量标记消息为已读")
    public Result<Boolean> batchReadMessages(
            @Parameter(description = "消息ID列表") @RequestBody List<Long> messageIds) {
        boolean success = notificationService.batchReadMessages(messageIds);
        return Result.success(success);
    }
}