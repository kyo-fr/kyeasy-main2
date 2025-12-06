package com.ares.cloud.order.infrastructure.persistence.entity;

import lombok.Data;

/**
 * 骑士统计查询结果
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/27
 */
@Data
public class KnightStatisticsResult {
    
    /**
     * 收入金额（配送订单的总金额，以最小货币单位为单位）
     */
    private Long income;
    
    /**
     * 订单数量（配送完成的订单数）
     */
    private Integer orderCount;
    
    /**
     * 超时订单数量
     */
    private Integer overdueOrderCount;
    
    /**
     * 超时总时长（秒）
     */
    private Long overdueTotalSeconds;
}

