package org.ares.cloud.api.payment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发票作废命令
 * 封装发票作废所需的所有参数
 * <p>
 * 该命令用于处理发票的作废操作，包含发票ID和作废原因等信息
 * <p>
 * 使用场景：
 * 1. 发票信息错误需要作废
 * 2. 业务取消需要作废发票
 * 3. 合规要求需要作废原发票并重新开具
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发票作废命令对象", title = "发票作废命令")
public class VoidInvoiceCommand {
    /**
     * 发票ID
     * <p>
     * 需要作废的发票唯一标识，系统通过该ID查找对应的发票记录
     * 进行作废操作。格式通常为字母I开头加上日期和序号。
     */
    @Schema(description = "发票ID", example = "I202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String invoiceId;
    
    /**
     * 作废原因
     * <p>
     * 发票作废的具体原因说明，用于记录作废操作的依据和背景。
     * 该信息将被记录在作废记录中，便于后续查询和审计。
     */
    @Schema(description = "作废原因", example = "发票信息错误", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;
}