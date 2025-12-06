package com.ares.cloud.order.domain.model.valueobject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import org.ares.cloud.common.model.Money;

/**
 * @author hugo
 * @version 1.0
 * @description: 支付项
 * @date 2025/1/19 11:22
 */
@Value
@Builder
public class PayItem {
    /**
     * 支付渠道ID
     */
    String channelId;
    /**
     * 交易流水号
     */
    String tradeNo;
    /**
     * 支付金额
     */
    Money amount;
    /**
     * 备注
     */
    String remark;
}
