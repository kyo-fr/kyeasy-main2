package com.ares.cloud.pay.application.command;

import lombok.Data;
import org.ares.cloud.common.model.Money;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 退款命令
 * 封装退款操作所需的所有参数，用于在系统中对已支付的订单进行退款处理
 * <p>
 * 该命令用于商户对已成功支付的订单进行全额或部分退款，支持多种退款场景
 * <p>
 * 使用场景：
 * 1. 商品退货退款
 * 2. 服务取消退款
 * 3. 订单金额调整
 * 4. 系统异常退款
 */
@Data
@Schema(description = "退款命令对象", title = "退款命令")
public class RefundCommand {
    
    /**
     * 商户ID
     * <p>
     * 商户ID是支付订单关联的商户标识，系统通过该ID验证商户权限，
     * 确保只有订单所属商户才能对该订单进行退款操作。
     */
    @Schema(description = "商户ID", example = "M202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantId;
    
    /**
     * 系统订单号
     * <p>
     * 要退款的支付订单的系统订单号，用于定位具体的订单记录。
     * 系统会验证该订单是否属于指定的商户，且订单状态为已支付。
     */
    @Schema(description = "系统订单号", example = "PAY2024102512345678901234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderNo;
    
    /**
     * 退款金额
     * <p>
     * 退款的金额，使用Money类型确保金额计算的精确性。
     * 退款金额不能超过原订单的支付金额，支持全额退款和部分退款。
     */
    @Schema(description = "退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private Money refundAmount;
    
    /**
     * 退款原因
     * <p>
     * 退款的具体原因说明，用于记录退款的操作依据和业务背景。
     * 非必填项，但建议填写，便于后续的退款分析和问题排查。
     */
    @Schema(description = "退款原因", example = "商品质量问题，客户要求退货")
    private String reason;
    
    /**
     * 签名
     * <p>
     * 请求参数的签名，用于验证请求的完整性和来源的合法性。
     * 签名算法使用商户密钥对请求参数进行加密，确保数据传输安全。
     */
    @Schema(description = "请求签名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sign;
} 