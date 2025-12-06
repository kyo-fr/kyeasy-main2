package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 查询支付订单命令
 * 封装查询支付订单所需的所有参数，用于在系统中查询指定的支付订单信息
 * <p>
 * 该命令用于商户查询订单的当前状态、金额、支付时间等详细信息
 * <p>
 * 使用场景：
 * 1. 商户查询订单状态
 * 2. 订单对账和核对
 * 3. 支付结果确认
 */
@Data
@Schema(description = "查询支付订单命令对象", title = "查询支付订单命令")
public class QueryPaymentOrderCommand {
    
    /**
     * 商户ID
     * <p>
     * 商户ID是支付订单关联的商户标识，系统通过该ID验证商户权限，
     * 确保只有订单所属商户才能查询该订单信息。
     */
    @Schema(description = "商户ID", example = "M202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantId;
    
    /**
     * 系统订单号
     * <p>
     * 要查询的支付订单的系统订单号，用于定位具体的订单记录。
     * 系统会验证该订单是否属于指定的商户，确保查询的安全性。
     */
    @Schema(description = "系统订单号", example = "PAY2024102512345678901234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderNo;
    
    /**
     * 签名
     * <p>
     * 请求参数的签名，用于验证请求的完整性和来源的合法性。
     * 签名算法使用商户密钥对请求参数进行加密，确保数据传输安全。
     */
    @Schema(description = "请求签名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sign;
} 