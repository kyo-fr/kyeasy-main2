package com.ares.cloud.order.interfaces.rest;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/4/2 02:06
 */

import com.ares.cloud.order.application.dto.OrderOperationLogDTO;
import com.ares.cloud.order.application.query.OrderOperationLogQuery;
import com.ares.cloud.order.application.service.OrderOperationLogQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单查询接口
 */
@Tag(name = "订单日志")
@RestController
@RequestMapping("/api/v1/orders/log")
@RequiredArgsConstructor
public class OrderLogController {
    private final OrderOperationLogQueryService orderOperationLogQueryService;
    @Operation(summary = "查询订单日志列表")
    @GetMapping("/operation")
    public Result<PageResult<OrderOperationLogDTO>> getOrderOperationLogs(@ParameterObject OrderOperationLogQuery query){
        PageResult<OrderOperationLogDTO> result = orderOperationLogQueryService.queryOperationLogs(query);
        return Result.success(result);
    }
}
