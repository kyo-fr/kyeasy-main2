package com.ares.cloud.pay.application.dto;

import lombok.Data;
import org.ares.cloud.common.model.Money;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 创建支付订单响应DTO
 * 封装创建支付订单后返回的所有信息，包括订单号、支付地址等
 * <p>
 * 该DTO用于向商户返回支付订单的创建结果，包含后续支付所需的关键信息
 * <p>
 * 使用场景：
 * 1. 商户创建订单后的响应
 * 2. 第三方系统接入支付
 * 3. 移动端扫码支付
 */
@Data
@Schema(description = "创建支付订单响应对象", title = "创建支付订单响应")
public class CreatePaymentOrderResponseDTO {
    
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
     * 支付金额
     * <p>
     * 订单的支付金额，使用Money类型确保金额计算的精确性。
     * 包含金额数值、币种和精度信息，支持多种货币类型。
     */
    @Schema(description = "支付金额")
    private Money amount;
    
    /**
     * 支付区域
     * <p>
     * 支付订单的货币区域，决定支付金额的币种和汇率计算。
     * 支持的区域包括：CNY（人民币）、USD（美元）、EUR（欧元）、CHF（瑞士法郎）、GBP（英镑）。
     */
    @Schema(description = "支付区域", example = "CNY")
    private String paymentRegion;
    
    /**
     * 订单标题
     * <p>
     * 支付订单的标题，用于在支付页面和支付记录中显示。
     * 建议使用简洁明了的商品或服务名称，便于用户识别。
     */
    @Schema(description = "订单标题", example = "商品购买")
    private String subject;
    
    /**
     * 订单描述
     * <p>
     * 支付订单的详细描述，用于提供订单的额外信息。
     * 非必填项，可用于记录订单的详细信息、商品规格、服务内容等。
     */
    @Schema(description = "订单描述", example = "购买iPhone 15 Pro Max 256GB")
    private String description;
    
    /**
     * 支付地址
     * <p>
     * 用户进行支付的页面地址，包含票据信息。
     * 用户访问该地址后可以进行在线支付操作。
     */
    @Schema(description = "支付地址", example = "https://pay.example.com/pay?ticket=abc123")
    private String payUrl;
    
    /**
     * 二维码支付地址
     * <p>
     * 用于生成支付二维码的地址，包含票据信息。
     * 移动端用户可以通过扫描二维码进行支付。
     */
    @Schema(description = "二维码支付地址", example = "https://pay.example.com/qrcode?ticket=abc123")
    private String qrcodeUrl;
    
    /**
     * 订单过期时间
     * <p>
     * 订单的过期时间戳，超过该时间后订单将无法支付。
     * 时间戳格式为毫秒级Unix时间戳。
     */
    @Schema(description = "订单过期时间（毫秒时间戳）", example = "1732521600000")
    private Long expireTime;
    
    /**
     * 签名
     * <p>
     * 响应参数的签名，用于验证响应的完整性和来源的合法性。
     * 签名算法使用商户密钥对响应参数进行加密，确保数据传输安全。
     */
    @Schema(description = "响应签名")
    private String sign;
} 