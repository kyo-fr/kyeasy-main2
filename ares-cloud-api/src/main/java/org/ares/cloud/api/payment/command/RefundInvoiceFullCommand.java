package org.ares.cloud.api.payment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发票全额退款命令
 * 封装发票全额退款所需的所有参数
 * <p>
 * 该命令用于处理发票的全额退款操作，包含发票ID和退款原因等信息
 * <p>
 * 使用场景：
 * 1. 客户申请全额退款
 * 2. 订单取消需要全额退款
 * 3. 服务完全不满意需要全额退款
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发票全额退款命令对象", title = "发票全额退款命令")
public class RefundInvoiceFullCommand {
    /**
     * 发票ID
     * <p>
     * 需要全额退款的发票唯一标识，系统通过该ID查找对应的发票记录
     * 进行全额退款操作。格式通常为字母I开头加上日期和序号。
     */
    @Schema(description = "发票ID", example = "I202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String invoiceId;
    
    /**
     * 退款原因
     * <p>
     * 全额退款的具体原因说明，用于记录退款操作的依据和背景。
     * 该信息将被记录在退款记录中，便于后续查询和审计。
     */
    @Schema(description = "退款原因", example = "订单取消", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;
}