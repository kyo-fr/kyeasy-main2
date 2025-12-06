package com.ares.cloud.pay.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomMoneySerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商户统计信息
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/26
 */
@Data
@Schema(description = "商户统计")
public class MerchantStatistics implements Serializable {
    
    @Schema(description = "统计开始时间（时间戳，毫秒）")
    private Long startTime;
    
    @Schema(description = "统计结束时间（时间戳，毫秒）")
    private Long endTime;
    
    @Schema(description = "统计开始时间（格式化字符串，如：25/02/2025）")
    private String startTimeStr;
    
    @Schema(description = "统计结束时间（格式化字符串，如：25/02/2025）")
    private String endTimeStr;
    
    @Schema(description = "支付区域/货币", example = "EUR")
    private String paymentRegion;
    
    // ==================== 顶部摘要统计 ====================
    
    @Schema(description = "手续费总支出金额", type = "string", example = "100.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money totalFeeExpenditure;
    
    @Schema(description = "手续费总支出对应的税额", type = "string", example = "20.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money totalFeeTax;
    
    @Schema(description = "手续费总支出对应的税率（百分比）", example = "20.00")
    private BigDecimal totalFeeTaxRate;
    
    @Schema(description = "手续费总支出对应的交易数量")
    private Integer totalFeeTransactionCount;
    
    @Schema(description = "商户手续费金额", type = "string", example = "50.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantFee;
    
    @Schema(description = "商户手续费对应的税额", type = "string", example = "10.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantFeeTax;
    
    @Schema(description = "商户手续费对应的税率（百分比）", example = "20.00")
    private BigDecimal merchantFeeTaxRate;
    
    @Schema(description = "商户手续费对应的交易数量")
    private Integer merchantFeeTransactionCount;
    
    // ==================== 详细统计 - 收入类 ====================
    
    @Schema(description = "商户收款收入金额（MERCHANT_COLLECTION）", type = "string", example = "1000.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantCollectionIncome;
    
    @Schema(description = "商户收款交易数量")
    private Integer merchantCollectionTransactionCount;
    
    @Schema(description = "商户收款手续费金额", type = "string", example = "5.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantCollectionFee;
    
    @Schema(description = "商户收款手续费率（百分比）", example = "0.50")
    private BigDecimal merchantCollectionFeeRate;
    
    @Schema(description = "系统购买收入金额（MERCHANT_PURCHASE）", type = "string", example = "500.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantPurchaseIncome;
    
    @Schema(description = "系统购买交易数量")
    private Integer merchantPurchaseTransactionCount;
    
    @Schema(description = "系统购买手续费金额", type = "string", example = "0.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantPurchaseFee;
    
    @Schema(description = "系统购买手续费率（百分比）", example = "0.00")
    private BigDecimal merchantPurchaseFeeRate;
    
    // ==================== 详细统计 - 支出类 ====================
    
    @Schema(description = "活动赠送支出金额（MERCHANT_ACTIVITY_GIFT）", type = "string", example = "200.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money activityGiftExpenditure;
    
    @Schema(description = "活动赠送交易数量")
    private Integer activityGiftTransactionCount;
    
    @Schema(description = "活动赠送手续费金额", type = "string", example = "0.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money activityGiftFee;
    
    @Schema(description = "活动赠送手续费率（百分比）", example = "0.00")
    private BigDecimal activityGiftFeeRate;
    
    @Schema(description = "优惠减免支出金额（MERCHANT_DISCOUNT_GRANT）", type = "string", example = "50.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money discountGrantExpenditure;
    
    @Schema(description = "优惠减免交易数量")
    private Integer discountGrantTransactionCount;
    
    @Schema(description = "优惠减免手续费金额", type = "string", example = "0.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money discountGrantFee;
    
    @Schema(description = "优惠减免手续费率（百分比）", example = "0.00")
    private BigDecimal discountGrantFeeRate;
    
    @Schema(description = "减价支出金额（MERCHANT_PRICE_GRANT）", type = "string", example = "30.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money priceGrantExpenditure;
    
    @Schema(description = "减价交易数量")
    private Integer priceGrantTransactionCount;
    
    @Schema(description = "减价手续费金额", type = "string", example = "0.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money priceGrantFee;
    
    @Schema(description = "减价手续费率（百分比）", example = "0.00")
    private BigDecimal priceGrantFeeRate;
    
    @Schema(description = "出售给系统支出金额（MERCHANT_SELL）", type = "string", example = "100.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantSellExpenditure;
    
    @Schema(description = "出售给系统交易数量")
    private Integer merchantSellTransactionCount;
    
    @Schema(description = "出售给系统手续费金额", type = "string", example = "1.50")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantSellFee;
    
    @Schema(description = "出售给系统手续费率（百分比）", example = "1.50")
    private BigDecimal merchantSellFeeRate;
    
    // ==================== 按交易类型分组的完整统计（供前端灵活展示和扩展） ====================
    
    @Schema(description = "按交易类型分组的统计列表，包含所有交易类型的详细数据")
    private List<TransactionTypeStatistics> transactionTypeStatistics;
}

