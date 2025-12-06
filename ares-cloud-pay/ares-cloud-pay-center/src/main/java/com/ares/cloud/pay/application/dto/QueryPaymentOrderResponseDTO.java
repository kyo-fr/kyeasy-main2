package com.ares.cloud.pay.application.dto;

import lombok.Data;
import org.ares.cloud.common.model.Money;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 查询支付订单响应DTO
 * 封装查询支付订单后返回的所有信息，包括订单状态、支付时间等
 * <p>
 * 该DTO用于向商户返回支付订单的当前状态和详细信息
 * <p>
 * 使用场景：
 * 1. 商户查询订单状态
 * 2. 订单对账和核对
 * 3. 支付结果确认
 */
@Data
@Schema(description = "查询支付订单响应对象", title = "查询支付订单响应")
public class QueryPaymentOrderResponseDTO {
    
    /**
     * 系统订单号
     * <p>
     * 系统自动生成的唯一订单号，用于内部订单管理和追踪。
     * 格式通常为字母PAY开头加上时间戳和随机数，确保全局唯一性。
     */
    @Schema(description = "系统订单号", example = "PAY2024102512345678901234")
    private String orderNo;
    
    /**
     * 商户订单号
     * <p>
     * 商户系统传入的订单号，用于商户内部订单管理和对账。
     * 每个商户订单号在同一商户下必须唯一。
     */
    @Schema(description = "商户订单号", example = "ORD202410250001")
    private String merchantOrderNo;
    
    /**
     * 支付状态
     * <p>
     * 支付订单的当前状态，包括：WAITING（待支付）、PROCESSING（处理中）、SUCCESS（支付成功）、
     * FAILED（支付失败）、CLOSED（已关闭）、REFUNDED（已退款）、PARTIAL_REFUNDED（部分退款）。
     * 商户可以根据订单状态进行相应的业务处理。
     */
    @Schema(description = "支付状态", example = "SUCCESS")
    private String status;
    
    /**
     * 支付金额
     * <p>
     * 订单的支付金额，使用Money类型确保金额计算的精确性。
     * 包含金额数值、币种和精度信息，支持多种货币类型。
     */
    @Schema(description = "支付金额")
    private Money amount;
    
    /**
     * 币种
     * <p>
     * 支付订单的货币类型，决定支付金额的币种和汇率计算。
     * 支持的币种包括：CNY（人民币）、USD（美元）、EUR（欧元）、CHF（瑞士法郎）、GBP（英镑）。
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
     * 签名
     * <p>
     * 响应参数的签名，用于验证响应的完整性和来源的合法性。
     * 签名算法使用商户密钥对响应参数进行加密，确保数据传输安全。
     */
    @Schema(description = "响应签名")
    private String sign;
} 