package com.ares.cloud.pay.application.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 关闭支付订单响应DTO
 * 封装关闭支付订单后返回的所有信息，包括关闭状态、关闭时间等
 * <p>
 * 该DTO用于向商户返回关闭支付订单操作的处理结果
 * <p>
 * 使用场景：
 * 1. 商户主动关闭订单
 * 2. 订单超时自动关闭
 * 3. 库存不足关闭订单
 */
@Data
@Schema(description = "关闭支付订单响应对象", title = "关闭支付订单响应")
public class ClosePaymentOrderResponseDTO {
    
    /**
     * 系统订单号
     * <p>
     * 要关闭的支付订单的系统订单号，用于定位具体的订单记录。
     * 系统会验证该订单是否属于指定的商户，确保关闭操作的安全性。
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
     * 关闭状态
     * <p>
     * 关闭操作的当前状态，包括：SUCCESS（成功）、FAILED（失败）。
     * 商户可以根据关闭状态进行相应的业务处理。
     */
    @Schema(description = "关闭状态", example = "SUCCESS")
    private String status;
    
    /**
     * 关闭时间
     * <p>
     * 订单关闭的时间戳，格式为毫秒级Unix时间戳。
     * 仅在关闭状态为SUCCESS时有效。
     */
    @Schema(description = "关闭时间（毫秒时间戳）", example = "1732521600000")
    private Long closeTime;
    
    /**
     * 关闭失败原因
     * <p>
     * 关闭失败的具体原因，用于记录关闭失败的业务背景。
     * 仅在关闭状态为FAILED时有效。
     */
    @Schema(description = "关闭失败原因", example = "订单已支付，无法关闭")
    private String failReason;
    
    /**
     * 签名
     * <p>
     * 响应参数的签名，用于验证响应的完整性和来源的合法性。
     * 签名算法使用商户密钥对响应参数进行加密，确保数据传输安全。
     */
    @Schema(description = "响应签名")
    private String sign;
} 