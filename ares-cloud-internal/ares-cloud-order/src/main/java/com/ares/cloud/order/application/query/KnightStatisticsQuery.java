package com.ares.cloud.order.application.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 骑士统计查询对象
 * 
 * 支持四种查询方式：
 * 1. 按天查询（DAY）：
 *    - 可选提供 startTime 和 endTime（时间戳，毫秒）
 *    - 如果未提供时间参数，则自动使用当天的开始时间和结束时间
 * 
 * 2. 按周查询（WEEK）：
 *    - 可选提供 queryValue，格式为 "yyyy-Www"（如：2025-W10）
 *    - 如果未提供 queryValue，则自动使用当前周
 * 
 * 3. 按月查询（MONTH）：
 *    - 可选提供 queryValue，格式为 "yyyy-MM"（如：2025-02）
 *    - 如果未提供 queryValue，则自动使用当前月份
 * 
 * 4. 按年查询（YEAR）：
 *    - 可选提供 queryValue，格式为 "yyyy"（如：2025）
 *    - 如果未提供 queryValue，则自动使用当前年份
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/27
 */
@Data
@Schema(description = "骑士统计查询对象")
public class KnightStatisticsQuery implements Serializable {

    @Schema(description = "骑士ID，不传则统计当前登录骑士", example = "knight123")
    private String knightId;
    
    @Schema(description = "支付区域/货币，可选，不传则查询所有币种", 
            allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"},
            example = "EUR")
    private String paymentRegion;
    
    @Schema(description = "查询类型：DAY-按天，WEEK-按周，MONTH-按月，YEAR-按年", 
            allowableValues = {"DAY", "WEEK", "MONTH", "YEAR"},
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "DAY")
    private String queryType;
    
    @Schema(description = "查询值，按周查询时格式为'yyyy-Www'（如：2025-W10），按月查询时格式为'yyyy-MM'（如：2025-02），按年查询时格式为'yyyy'（如：2025）。如果不传则自动使用当前周/月/年",
            example = "2025-02")
    @JsonProperty("queryValue")
    private String queryValue;
    
    @Schema(description = "开始时间（时间戳，毫秒），按天查询时使用。如果不传则自动使用当天的开始时间",
            example = "1740441600000")
    @JsonProperty(value = "startTime")
    private Long startTime;
    
    @Schema(description = "结束时间（时间戳，毫秒），按天查询时使用。如果不传则自动使用当天的结束时间",
            example = "1740527999999")
    @JsonProperty(value = "endTime")
    private Long endTime;
}

