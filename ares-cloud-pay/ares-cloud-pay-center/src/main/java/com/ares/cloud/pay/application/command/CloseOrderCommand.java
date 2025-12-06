package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 关闭支付订单命令
 * 封装关闭支付订单所需的所有参数，用于在系统中关闭指定的支付订单
 * <p>
 * 该命令用于商户关闭未支付的订单，防止订单过期后仍可支付
 * <p>
 * 使用场景：
 * 1. 商户主动关闭订单
 * 2. 订单超时自动关闭
 * 3. 库存不足关闭订单
 */
@Data
@Schema(description = "关闭支付订单命令对象", title = "关闭支付订单命令")
public class CloseOrderCommand {
    
    /**
     * 商户ID
     * <p>
     * 商户ID是关闭订单操作关联的商户标识，系统通过该ID验证商户权限，
     * 确保只有订单所属商户才能关闭该订单。
     */
    @Schema(description = "商户ID", example = "M202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantId;
    
    /**
     * 系统订单号
     * <p>
     * 要关闭的支付订单的系统订单号，用于定位具体的订单记录。
     * 系统会验证该订单是否属于指定的商户，确保关闭操作的安全性。
     */
    @Schema(description = "系统订单号", example = "PAY2024102512345678901234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderNo;
    
    /**
     * 关闭原因
     * <p>
     * 关闭订单的原因说明，用于记录订单关闭的具体原因。
     * 非必填项，可用于后续的订单分析和问题排查。
     */
    @Schema(description = "关闭原因", example = "用户主动取消")
    private String reason;
    
    /**
     * 操作人ID
     * <p>
     * 执行关闭操作的操作人ID，用于记录操作日志和审计追踪。
     * 非必填项，在系统自动关闭时可为空。
     */
    @Schema(description = "操作人ID", example = "USER001")
    private String operatorId;
    
    /**
     * 签名
     * <p>
     * 请求参数的签名，用于验证请求的完整性和来源的合法性。
     * 签名算法使用商户密钥对请求参数进行加密，确保数据传输安全。
     */
    @Schema(description = "请求签名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sign;
} 