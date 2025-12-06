package com.ares.cloud.order.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomMoneySerializer;

import java.io.Serializable;

/**
 * 按支付渠道分组的统计信息
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/27
 */
@Data
@Schema(description = "按支付渠道分组的统计")
public class PaymentChannelStatistics implements Serializable {
    
    @Schema(description = "支付渠道ID", example = "CREDIT_CARD")
    private String channelId;
    
    @Schema(description = "支付渠道名称", example = "信用卡")
    private String channelName;
    
    @Schema(description = "该渠道的支付金额")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money amount;
    
    @Schema(description = "该渠道的订单数量")
    private Integer orderCount;
}

