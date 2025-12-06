package com.ares.cloud.order.infrastructure.persistence.entity;

import lombok.Data;

/**
 * 按支付渠道分组的统计查询结果
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/27
 */
@Data
public class PaymentChannelStatisticsResult {
    
    /**
     * 支付渠道ID
     */
    private String channelId;
    
    /**
     * 该渠道的支付金额（以最小货币单位为单位）
     */
    private Long amount;
    
    /**
     * 该渠道的订单数量
     */
    private Integer orderCount;
}

