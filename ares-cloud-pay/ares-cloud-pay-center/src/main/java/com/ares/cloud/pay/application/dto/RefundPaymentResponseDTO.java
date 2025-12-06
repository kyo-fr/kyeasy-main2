package com.ares.cloud.pay.application.dto;

import lombok.Data;
import org.ares.cloud.common.model.Money;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 退款响应DTO
 * 封装退款操作后返回的所有信息，包括退款状态、退款时间等
 * <p>
 * 该DTO用于向商户返回退款操作的处理结果
 * <p>
 * 使用场景：
 * 1. 商户主动退款
 * 2. 客户申请退款
 * 3. 订单取消退款
 */
@Data
@Schema(description = "退款响应对象", title = "退款响应")
public class RefundPaymentResponseDTO {
    
    /**
     * 系统订单号
     * <p>
     * 要退款的支付订单的系统订单号，用于定位具体的订单记录。
     * 系统会验证该订单是否属于指定的商户，确保退款操作的安全性。
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
     * 退款金额
     * <p>
     * 退款的金额，使用Money类型确保金额计算的精确性。
     * 可以小于等于原订单金额，支持部分退款和全额退款。
     */
    @Schema(description = "退款金额")
    private Money refundAmount;
    
    /**
     * 币种
     * <p>
     * 退款金额的货币类型，决定退款金额的币种和汇率计算。
     * 支持的币种包括：CNY（人民币）、USD（美元）、EUR（欧元）、CHF（瑞士法郎）、GBP（英镑）。
     */
    @Schema(description = "币种", example = "CNY")
    private String currency;
    
    /**
     * 退款状态
     * <p>
     * 退款操作的当前状态，包括：REFUNDED（已退款）、PARTIAL_REFUNDED（部分退款）、FAILED（失败）。
     * 商户可以根据退款状态进行相应的业务处理。
     */
    @Schema(description = "退款状态", example = "REFUNDED")
    private String status;
    
    /**
     * 退款原因
     * <p>
     * 退款的具体原因，用于记录退款操作的业务背景。
     * 有助于后续的财务对账和客户服务。
     */
    @Schema(description = "退款原因", example = "客户取消订单")
    private String reason;
    
    /**
     * 签名
     * <p>
     * 响应参数的签名，用于验证响应的完整性和来源的合法性。
     * 签名算法使用商户密钥对响应参数进行加密，确保数据传输安全。
     */
    @Schema(description = "响应签名")
    private String sign;
} 