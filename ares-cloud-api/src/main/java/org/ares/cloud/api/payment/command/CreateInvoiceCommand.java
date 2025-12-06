package org.ares.cloud.api.payment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建发票命令
 * 封装创建发票所需的所有参数，用于在系统中生成新的发票记录
 * <p>
 * 该命令包含发票的基本信息，如商户ID、交易方类型、付款方和收款方信息、
 * 发票明细项、关联的合同和订单信息、金额信息以及支付项信息等
 * <p>
 * 使用场景：
 * 1. 订单完成后生成发票
 * 2. 手动创建发票记录
 * 3. 导入外部系统发票数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "创建发票命令对象", title = "创建发票命令")
public class CreateInvoiceCommand implements Command {
    /**
     * 商户ID，用于获取币种和精度信息
     * <p>
     * 商户ID是发票关联的商户标识，系统通过该ID获取对应的币种和精度设置，
     * 确保发票金额计算的准确性。格式通常为字母M开头加上日期和序号。
     */
    @Schema(description = "商户ID", example = "M202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantId;
    /**
     * 付款方信息
     * <p>
     * 包含付款方的详细信息，如名称、税号、地址、联系方式等。
     * 在发票生成过程中，这些信息将被用于填充发票的付款方部分。
     */
    @Schema(description = "付款方信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private PartyCommand payer;
    
    /**
     * 收款方信息
     * <p>
     * 包含收款方的详细信息，如名称、税号、地址、联系方式等。
     * 在发票生成过程中，这些信息将被用于填充发票的收款方部分。
     */
    @Schema(description = "收款方信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private PartyCommand payee;
    
    /**
     * 发票明细项列表
     * <p>
     * 包含发票中的所有商品或服务项目，每个明细项包括商品名称、数量、单价、
     * 税率、税额和总价等信息。发票的总金额由所有明细项的总价累加得出。
     */
    @Schema(description = "发票明细项列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<InvoiceItemCommand> items;
    
    /**
     * 合同ID
     * <p>
     * 关联的合同标识，用于将发票与特定合同关联起来。
     * 非必填项，仅在发票与合同相关联时使用。格式通常为字母C开头加上日期和序号。
     */
    @Schema(description = "合同ID", example = "C202410250001")
    private String contractId;
    
    /**
     * 订单ID
     * <p>
     * 关联的订单标识，用于将发票与特定订单关联起来。
     * 每张发票必须关联一个订单。格式通常为字母O开头加上日期和序号。
     */
    @Schema(description = "订单ID", example = "O202410250001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderId;
    
    /**
     * 交易ID
     * <p>
     * 关联的交易标识，用于将发票与特定交易关联起来。
     * 非必填项，仅在发票与交易相关联时使用。格式通常为字母T开头加上日期和序号。
     */
    @Schema(description = "交易ID", example = "T202410250001")
    private String transactionId;
    
    /**
     * 税前总金额（BigDecimal）
     * <p>
     * 发票中所有商品或服务的税前总金额，不包含税额。
     * 计算方式为所有明细项的（单价 × 数量）之和。
     */
    @Schema(description = "税前总金额", example = "100.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal preTaxAmount;
    
    /**
     * 总金额（BigDecimal）
     * <p>
     * 发票的最终总金额，包含税额。
     * 计算方式为税前总金额加上所有明细项的税额之和，再减去减免金额（如有）。
     */
    @Schema(description = "总金额（含税）", example = "113.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal totalAmount;

    /**
     * 支付项列表
     * <p>
     * 包含所有支付的记录，如银行转账、支付宝、微信支付等电子支付方式。
     * 每个支付项包含支付渠道、金额、时间和状态等信息。
     */
    @Schema(description = "支付项列表")
    private List<PayItemCommand> payItems;

    /**
     * 减免金额
     * <p>
     * 发票中的优惠或折扣金额，将从总金额中扣除。
     * 可用于各种促销活动、会员折扣或特殊情况下的价格调整。
     */
    @Schema(description = "减免金额", example = "10.00")
    private BigDecimal deductAmount;

    // 加上币种和精度
    /**
     * 币种
     */
    @Schema(description = "币种", example = "CNY", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currency;
    /**
     * 精度
     */
    @Schema(description = "精度", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer scale;
}