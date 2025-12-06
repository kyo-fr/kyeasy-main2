package com.ares.cloud.order.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomMoneySerializer;

import java.io.Serializable;
import java.util.List;

/**
 * 骑士统计信息
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/27
 */
@Data
@Schema(description = "骑士统计")
public class KnightStatistics implements Serializable {
    
    @Schema(description = "统计开始时间（时间戳，毫秒）")
    private Long startTime;
    
    @Schema(description = "统计结束时间（时间戳，毫秒）")
    private Long endTime;
    
    @Schema(description = "统计开始时间（格式化字符串，如：25/06/2024）")
    private String startTimeStr;
    
    @Schema(description = "统计结束时间（格式化字符串，如：02/07/2024）")
    private String endTimeStr;
    
    @Schema(description = "日期范围描述", example = "25/06/2024 - 02/07/2024")
    private String dateRange;
    
    @Schema(description = "支付区域/货币", example = "EUR")
    private String paymentRegion;
    
    // ==================== 基础统计 ====================
    
    @Schema(description = "收入（配送订单的总金额）")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money income;
    
    @Schema(description = "订单数量（配送完成的订单数）")
    private Integer orderCount;
    
    // ==================== 按支付渠道分组统计 ====================
    
    @Schema(description = "按支付渠道分组的统计列表")
    private List<PaymentChannelStatistics> paymentChannelStatistics;
    
    // ==================== 超时订单统计 ====================
    
    @Schema(description = "超时订单数量")
    private Integer overdueOrderCount;
    
    @Schema(description = "超时时长（格式：HH:mm:ss）", example = "01:30:30")
    private String overdueTime;
}

