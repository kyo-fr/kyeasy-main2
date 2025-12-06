package org.ares.cloud.api.msg_center.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author hugo
 * @version 1.0
 * @description: 通知类型
 * @date 2024/11/11 16:31
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
@Schema(description = "通知类型枚举")
public enum NotificationType {
    @Schema(description = "用户下单通知")
    USER_ORDER,
    @Schema(description = "订单结算通知")
    ORDER_SETTLEMENT,
    @Schema(description = "订单退款通知")
    ORDER_REFUND,
    @Schema(description = "订单配送通知")
    ORDER_DELIVERY,

    @Schema(description = "商品过期提醒")
    PRODUCT_EXPIRATION,

    @Schema(description = "待审批通知")
    AWAITING_APPROVAL,
    
    @Schema(description = "商户完成结算获得奖票提醒")
    MERCHANT_SETTLEMENT_REWARD,
    
    @Schema(description = "获得会员礼物点选点提醒")
    MEMBER_GIFT_POINT,
    
    @Schema(description = "商户订阅到期提醒")
    MERCHANT_SUBSCRIBE_EXPIRATION,
    
    @Schema(description = "存储空间不足提醒（小于300MB）")
    STORAGE_INSUFFICIENT,
    @Schema(description = "SSL证书到期提醒（提前一个月）")
    SSL_EXPIRATION,
    
    @Schema(description = "新审批提醒")
    NEW_APPROVAL,

    @Schema(description = "商品到期提醒")
    PRODUCT_OVERDUE;
    
    /**
     * 将枚举序列化为小写字符串
     * @return 小写的枚举名称
     */
    @JsonValue
    public String getValue() {
        return this.name().toLowerCase();
    }
}