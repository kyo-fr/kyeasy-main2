package org.ares.cloud.api.payment.command;

import org.ares.cloud.common.model.Money;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发票退款命令
 * 封装发票退款所需的所有参数
 * <p>
 * 该命令用于处理发票的部分退款操作，包含发票ID、退款金额和退款原因等信息
 * <p>
 * 使用场景：
 * 1. 客户申请部分退款
 * 2. 商品部分退货
 * 3. 服务部分不满意需要退款
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发票退款命令对象", title = "发票退款命令")
public class RefundInvoiceCommand {
    /**
     * 发票ID
     * <p>
     * 需要退款的发票唯一标识，系统通过该ID查找对应的发票记录
     * 进行退款操作。格式通常为字母I开头加上日期和序号。
     */
    @Schema(description = "发票ID", example = "I202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String invoiceId;
    
    /**
     * 退款金额
     * <p>
     * 指定的退款金额，必须小于或等于发票的原始金额。
     * 系统会根据该金额计算相应的退款比例和税额。
     */
    @Schema(description = "退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private Money refundAmount;
    
    /**
     * 退款原因
     * <p>
     * 退款的具体原因说明，用于记录退款操作的依据和背景。
     * 该信息将被记录在退款记录中，便于后续查询和审计。
     */
    @Schema(description = "退款原因", example = "商品质量问题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;
}