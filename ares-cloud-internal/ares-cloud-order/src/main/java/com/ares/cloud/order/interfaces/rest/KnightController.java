package com.ares.cloud.order.interfaces.rest;

import com.ares.cloud.order.application.dto.KnightStatistics;
import com.ares.cloud.order.application.handlers.KnightQueryHandler;
import com.ares.cloud.order.application.query.KnightStatisticsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.ares.cloud.common.model.Result;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
 * 骑士管理控制器
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/27
 */
@RestController
@RequestMapping("/api/knights")
@Tag(name = "骑士管理", description = "骑士相关接口")
public class KnightController {

    @Resource
    private KnightQueryHandler knightQueryHandler;

    // ==================== 统计查询 ====================
    
    @GetMapping("/statistics")
    @Operation(summary = "查询骑士统计信息", 
               description = "查询骑士的配送统计信息，包括收入、订单数量、各支付渠道统计、超时订单等。支持按天/周/月/年查询，可按支付区域筛选")
    public Result<KnightStatistics> getKnightStatistics(@ParameterObject @Valid KnightStatisticsQuery query) {
        KnightStatistics result = knightQueryHandler.getKnightStatistics(query);
        return Result.success(result);
    }
}

