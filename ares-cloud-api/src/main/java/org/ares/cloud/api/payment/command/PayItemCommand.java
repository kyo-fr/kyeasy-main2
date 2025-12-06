package org.ares.cloud.api.payment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 支付项命令
 * 用于表示发票中的支付记录信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "创建支付项命令对象", title = "创建支付项命令")
public class PayItemCommand {
    /**
     * 支付渠道ID
     * <p>
     * 关联的支付渠道标识，如支付宝、微信支付、银行转账等
     * 用于标识该笔支付使用的具体支付方式
     */
    @Schema(description = "支付渠道ID", example = "CH001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String channelId;

    /**
     * 交易流水号
     * <p>
     * 支付渠道返回的唯一交易标识
     * 用于与支付渠道对账和查询交易状态
     */
    @Schema(description = "交易流水号", example = "202410250001123456")
    private String tradeNo;

    /**
     * 支付金额
     * <p>
     * 该支付项的实际支付金额
     * 多个支付项的金额总和应等于发票的总金额
     */
    @Schema(description = "支付金额", example = "100.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    /**
     * 支付时间
     * <p>
     * 支付完成的时间戳，精确到毫秒
     * 用于记录支付的确切时间，便于后续查询和统计
     */
    @Schema(description = "支付时间", example = "1698220800000")
    private Long payTime;

    /**
     * 备注
     * <p>
     * 关于该支付项的额外说明或注释
     * 可用于记录特殊情况、支付异常原因或其他需要说明的内容
     */
    @Schema(description = "备注", example = "线上支付-微信支付")
    private String remark;
} 