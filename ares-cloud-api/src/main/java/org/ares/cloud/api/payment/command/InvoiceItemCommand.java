package org.ares.cloud.api.payment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 发票明细项命令
 * 用于表示发票中的商品或服务项目信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "创建发票明细项命令对象", title = "创建发票明细项命令")
public class InvoiceItemCommand {
    /**
     * 商品id
     * <p>
     * 商品id
     */
    @Schema(description = "商品id", example = "1001")
    private String productId;
    /**
     * 订单明细项id
     */
    @Schema(description = "订单明细项id", example = "1001")
    private String orderItemId;

    /**
     * 商品名称
     * <p>
     * 明细项中商品或服务的名称，将显示在发票的明细列表中
     * 应当简洁明了地描述商品或服务的内容
     */
    @Schema(description = "商品名称", example = "高级办公椅", requiredMode = Schema.RequiredMode.REQUIRED)
    private String productName;

    /**
     * 数量
     * <p>
     * 商品或服务的数量，用于计算总价
     * 整数类型，表示购买的商品数量或服务次数
     */
    @Schema(description = "数量", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    /**
     * 原价
     * <p>
     * 商品或服务的原始价格，未经任何折扣或调整
     * 用于显示价格变动情况，如促销、折扣等
     */
    @Schema(description = "原价", example = "1000.00")
    private BigDecimal originalPrice;

    /**
     * 单价
     * <p>
     * 商品或服务的实际单价，可能已经包含了折扣
     * 用于计算明细项的总价（单价 × 数量）
     */
    @Schema(description = "单价", example = "900.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal unitPrice;

    /**
     * 税率
     * <p>
     * 适用于该商品或服务的税率百分比
     * 用于计算税额，如增值税率、消费税率等
     * 非必填项，当不填写时表示免税
     */
    @Schema(description = "税率", example = "0.13")
    private BigDecimal taxRate;

    /**
     * 税额
     * <p>
     * 该明细项应缴纳的税金金额
     * 计算方式通常为：单价 × 数量 × 税率
     * 当税率为空时，税额应为0
     */
    @Schema(description = "税额", example = "234.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal taxAmount;

    /**
     * 总价（含税）
     * <p>
     * 该明细项的最终金额，包含税额
     * 计算方式为：单价 × 数量 + 税额
     */
    @Schema(description = "总价（含税）", example = "2034.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal totalAmount;

    /**
     * 备注
     * <p>
     * 关于该明细项的额外说明或注释
     * 可用于记录特殊情况、促销信息或其他需要说明的内容
     */
    @Schema(description = "备注", example = "促销活动折扣商品")
    private String remark;
} 