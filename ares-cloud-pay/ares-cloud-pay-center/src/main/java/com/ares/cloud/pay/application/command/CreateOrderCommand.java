package com.ares.cloud.pay.application.command;

import lombok.Data;
import org.ares.cloud.common.model.Money;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 创建支付订单命令
 * 封装创建支付订单所需的所有参数，用于在系统中生成新的支付订单记录
 * <p>
 * 该命令包含商户信息、订单信息、支付参数等，用于第三方商户接入支付系统
 * <p>
 * 使用场景：
 * 1. 商户创建支付订单
 * 2. 第三方系统接入支付
 * 3. 移动端扫码支付
 */
@Data
@Schema(description = "创建支付订单命令对象", title = "创建支付订单命令")
public class CreateOrderCommand {
    
    /**
     * 商户ID
     * <p>
     * 商户ID是支付订单关联的商户标识，系统通过该ID获取商户信息和密钥，
     * 用于签名验证和权限控制。格式通常为字母M开头加上日期和序号。
     */
    @Schema(description = "商户ID", example = "M202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantId;
    
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
     * 每个商户订单号在同一商户下必须唯一，系统会进行重复性检查。
     */
    @Schema(description = "商户订单号", example = "ORD202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantOrderNo;
    
    /**
     * 支付金额
     * <p>
     */
    @Schema(description = "支付金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long amount;
    
    /**
     * 支付区域
     * <p>
     * 支付订单的货币区域，决定支付金额的币种和汇率计算。
     * 支持的区域包括：CNY（人民币）、USD（美元）、EUR（欧元）、CHF（瑞士法郎）、GBP（英镑）。
     */
    @Schema(description = "支付区域", example = "CNY", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paymentRegion;
    
    /**
     * 订单标题
     * <p>
     * 支付订单的标题，用于在支付页面和支付记录中显示。
     * 建议使用简洁明了的商品或服务名称，便于用户识别。
     */
    @Schema(description = "订单标题", example = "商品购买", requiredMode = Schema.RequiredMode.REQUIRED)
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
     * 支付完成后的跳转地址
     * <p>
     * 用户支付完成后，系统将用户重定向到该地址。
     * 通常为商户的订单详情页面或支付成功页面，用于展示支付结果。
     */
    @Schema(description = "支付完成后的跳转地址", example = "https://example.com/payment/success")
    private String returnUrl;
    
    /**
     * 支付结果通知地址
     * <p>
     * 支付状态变更时，系统向该地址发送异步通知。
     * 商户需要在该地址处理支付结果，更新订单状态。
     */
    @Schema(description = "支付结果通知地址", example = "https://example.com/payment/notify")
    private String notifyUrl;
    
    /**
     * 订单过期时间
     * <p>
     * 订单的过期时间戳，超过该时间后订单将无法支付。
     * 时间戳格式为毫秒级Unix时间戳，建议设置为当前时间后30分钟。
     */
    @Schema(description = "订单过期时间（毫秒时间戳）", example = "1732521600000")
    private Long expireTime;
    
    /**
     * 扩展字段1
     * <p>
     * 预留的扩展字段，用于存储商户自定义的额外信息。
     * 非必填项，可根据业务需要进行扩展使用。
     */
    @Schema(description = "扩展字段1")
    private String ext1;
    
    /**
     * 扩展字段2
     * <p>
     * 预留的扩展字段，用于存储商户自定义的额外信息。
     * 非必填项，可根据业务需要进行扩展使用。
     */
    @Schema(description = "扩展字段2")
    private String ext2;
    
    /**
     * 签名
     * <p>
     * 请求参数的签名，用于验证请求的完整性和来源的合法性。
     * 签名算法使用商户密钥对请求参数进行加密，确保数据传输安全。
     */
    @Schema(description = "请求签名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sign;
} 