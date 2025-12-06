package com.ares.cloud.pay.application.dto;

import lombok.Data;
import org.ares.cloud.common.model.Money;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 支付状态DTO
 * 封装支付订单状态查询返回的信息
 * <p>
 * 该DTO用于向支付页面返回订单的当前状态和详细信息
 * <p>
 * 使用场景：
 * 1. 支付页面轮询查询订单状态
 * 2. 支付结果确认
 * 3. 订单状态展示
 */
@Data
@Schema(description = "支付状态对象", title = "支付状态")
public class PaymentStatusDTO {
    
    /**
     * 系统订单号
     * <p>
     * 系统自动生成的唯一订单号，用于内部订单管理和追踪。
     */
    @Schema(description = "系统订单号", example = "PAY2024102512345678901234")
    private String orderNo;
    
    /**
     * 商户订单号
     * <p>
     * 商户系统传入的订单号，用于商户内部订单管理和对账。
     */
    @Schema(description = "商户订单号", example = "ORD202410250001")
    private String merchantOrderNo;
    
    /**
     * 支付状态
     * <p>
     * 支付订单的当前状态，包括：WAITING（待支付）、PROCESSING（处理中）、SUCCESS（支付成功）、
     * FAILED（支付失败）、CLOSED（已关闭）、REFUNDED（已退款）、PARTIAL_REFUNDED（部分退款）。
     */
    @Schema(description = "支付状态", example = "SUCCESS")
    private String status;
    
    /**
     * 支付金额
     * <p>
     * 订单的支付金额，使用Money类型确保金额计算的精确性。
     */
    @Schema(description = "支付金额")
    private Money amount;
    
    /**
     * 币种
     * <p>
     * 支付订单的货币类型，决定支付金额的币种和汇率计算。
     */
    @Schema(description = "币种", example = "CNY")
    private String currency;
    
    /**
     * 支付时间
     * <p>
     * 订单支付完成的时间戳，格式为毫秒级Unix时间戳。
     * 仅在订单状态为SUCCESS时有效。
     */
    @Schema(description = "支付时间（毫秒时间戳）", example = "1732521600000")
    private Long payTime;
    
    /**
     * 支付完成后的跳转地址
     * <p>
     * 用户支付完成后，系统将用户重定向到该地址。
     * 通常为商户的订单详情页面或支付成功页面。
     */
    @Schema(description = "支付完成后的跳转地址", example = "https://example.com/payment/success")
    private String returnUrl;
    
    /**
     * 订单标题
     * <p>
     * 支付订单的标题，用于在支付页面和支付记录中显示。
     */
    @Schema(description = "订单标题", example = "商品购买")
    private String subject;
    
    /**
     * 订单描述
     * <p>
     * 支付订单的详细描述，用于提供订单的额外信息。
     */
    @Schema(description = "订单描述", example = "购买iPhone 15 Pro Max 256GB")
    private String description;
} 