package com.ares.cloud.pay.application.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付订单信息DTO
 * 封装支付订单的详细信息，用于在系统中传递订单的核心数据
 * <p>
 * 该DTO包含订单的标识信息、金额、状态、时间等，用于订单信息的展示和传递
 * <p>
 * 使用场景：
 * 1. 订单信息展示
 * 2. 订单详情查询
 * 3. 订单信息传递
 */
@Data
@Schema(description = "支付订单信息对象", title = "支付订单信息")
public class PaymentOrderInfoDTO {
    
    /**
     * 订单号
     * <p>
     * 系统自动生成的唯一订单号，用于内部订单管理和追踪。
     * 格式通常为字母PAY开头加上时间戳和随机数，确保全局唯一性。
     */
    @Schema(description = "订单号", example = "PAY2024102512345678901234")
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
     * 商户ID
     * <p>
     * 支付订单关联的商户标识，用于识别订单所属的商户。
     * 系统通过该ID获取商户信息和密钥，用于签名验证和权限控制。
     */
    @Schema(description = "商户ID", example = "merchant_123456")
    private String merchantId;
    
    /**
     * 支付金额
     * <p>
     * 订单的支付金额，以分为单位，确保金额计算的精确性。
     * 金额必须大于0，支持多种货币类型。
     */
    @Schema(description = "支付金额（分）", example = "10000")
    private BigDecimal amount;
    
    /**
     * 币种
     * <p>
     * 支付订单的货币类型，决定支付金额的币种和汇率计算。
     * 支持的币种包括：CNY（人民币）、USD（美元）、EUR（欧元）、CHF（瑞士法郎）、GBP（英镑）。
     */
    @Schema(description = "币种", example = "CNY", allowableValues = {"CNY", "USD", "EUR", "CHF", "GBP"})
    private String currency;
    
    /**
     * 支付区域
     * <p>
     * 支付订单的货币区域，决定支付金额的币种和汇率计算。
     * 支持的区域包括：CNY（人民币）、USD（美元）、EUR（欧元）、CHF（瑞士法郎）、GBP（英镑）。
     */
    @Schema(description = "支付区域", example = "CNY", allowableValues = {"CNY", "USD", "EUR", "CHF", "GBP"})
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
     * 过期时间
     * <p>
     * 订单的过期时间戳，超过该时间后订单将无法支付。
     * 时间戳格式为毫秒级Unix时间戳，建议设置为当前时间后30分钟。
     */
    @Schema(description = "过期时间（毫秒时间戳）", example = "1732521600000")
    private Long expireTime;
    
    /**
     * 支付状态
     * <p>
     * 支付订单的当前状态，用于标识订单的处理进度。
     * 支持的状态包括：WAITING（待支付）、PROCESSING（处理中）、SUCCESS（支付成功）、
     * FAILED（支付失败）、CLOSED（已关闭）、REFUNDED（已退款）、PARTIAL_REFUNDED（部分退款）。
     */
    @Schema(description = "支付状态", example = "WAITING", allowableValues = {"WAITING", "PROCESSING", "SUCCESS", "FAILED", "CLOSED", "REFUNDED", "PARTIAL_REFUNDED"})
    private String status;
    
    /**
     * 自定义参数
     * <p>
     * 商户自定义的额外参数，用于存储商户特定的业务信息。
     * 非必填项，可根据业务需要进行扩展使用。
     */
    @Schema(description = "自定义参数", example = "{\"key1\": \"value1\", \"key2\": \"value2\"}")
    private Map<String, String> customParams;
    
    /**
     * 创建时间
     * <p>
     * 订单创建的时间戳，格式为毫秒级Unix时间戳。
     * 用于记录订单的创建时间，便于后续的订单管理和分析。
     */
    @Schema(description = "创建时间（毫秒时间戳）", example = "1732521600000")
    private Long createTime;
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private PaymentOrderInfoDTO result = new PaymentOrderInfoDTO();
        
        public Builder orderNo(String orderNo) {
            result.orderNo = orderNo;
            return this;
        }
        
        public Builder merchantOrderNo(String merchantOrderNo) {
            result.merchantOrderNo = merchantOrderNo;
            return this;
        }
        
        public Builder merchantId(String merchantId) {
            result.merchantId = merchantId;
            return this;
        }
        
        public Builder amount(BigDecimal amount) {
            result.amount = amount;
            return this;
        }
        
        public Builder currency(String currency) {
            result.currency = currency;
            return this;
        }
        
        public Builder paymentRegion(String paymentRegion) {
            result.paymentRegion = paymentRegion;
            return this;
        }
        
        public Builder subject(String subject) {
            result.subject = subject;
            return this;
        }
        
        public Builder description(String description) {
            result.description = description;
            return this;
        }
        
        public Builder returnUrl(String returnUrl) {
            result.returnUrl = returnUrl;
            return this;
        }
        
        public Builder notifyUrl(String notifyUrl) {
            result.notifyUrl = notifyUrl;
            return this;
        }
        
        public Builder expireTime(Long expireTime) {
            result.expireTime = expireTime;
            return this;
        }
        
        public Builder status(String status) {
            result.status = status;
            return this;
        }
        
        public Builder customParams(Map<String, String> customParams) {
            result.customParams = customParams;
            return this;
        }
        
        public Builder createTime(Long createTime) {
            result.createTime = createTime;
            return this;
        }
        
        public PaymentOrderInfoDTO build() {
            return result;
        }
    }
} 