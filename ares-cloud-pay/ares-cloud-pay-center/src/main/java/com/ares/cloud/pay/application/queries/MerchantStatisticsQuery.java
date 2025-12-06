package com.ares.cloud.pay.application.queries;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商户统计查询对象
 * 
 * 支持三种查询方式：
 * 1. 按天查询（DAY）：
 *    - 可选提供 startTime 和 endTime（时间戳，毫秒）
 *    - 如果未提供时间参数，则自动使用当天的开始时间和结束时间
 *    - 示例：startTime=1740441600000, endTime=1740527999999
 * 
 * 2. 按月查询（MONTH）：
 *    - 可选提供 queryValue，格式为 "yyyy-MM"（如：2025-02）
 *    - 如果未提供 queryValue，则自动使用当前月份
 *    - 示例：queryValue="2025-02" 或不传（自动使用当前月份）
 * 
 * 3. 按年查询（YEAR）：
 *    - 可选提供 queryValue，格式为 "yyyy"（如：2025）
 *    - 如果未提供 queryValue，则自动使用当前年份
 *    - 示例：queryValue="2025" 或不传（自动使用当前年份）
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/26
 */
@Data
@Schema(description = "商户统计查询对象")
public class MerchantStatisticsQuery implements Serializable {

    @Schema(description = "支付区域/货币，可选，不传则查询所有币种", 
            allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"},
            example = "EUR")
    private String paymentRegion;
    
    @Schema(description = "查询类型：DAY-按天，MONTH-按月，YEAR-按年", 
            allowableValues = {"DAY", "MONTH", "YEAR"},
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "DAY")
    private String queryType;
    
    @Schema(description = "查询值，按月查询时格式为'yyyy-MM'（如：2025-02），按年查询时格式为'yyyy'（如：2025）。如果不传则自动使用当前月份或当前年份",
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

