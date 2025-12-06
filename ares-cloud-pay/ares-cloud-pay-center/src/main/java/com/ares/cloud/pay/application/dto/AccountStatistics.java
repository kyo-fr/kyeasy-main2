package com.ares.cloud.pay.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomMoneySerializer;

import java.io.Serializable;
import java.util.List;

/**
 * 账户统计信息
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/9/7 10:17
 */
@Data
@Schema(description = "账户统计")
public class AccountStatistics implements Serializable {
    
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
    
    // ==================== 基础统计 ====================
    
    @Schema(description = "交易数量（总数）")
    private Integer total;
    
    @Schema(description = "收入金额（总收入）", type = "string", example = "100.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money income;
    
    @Schema(description = "支出金额（总支出）", type = "string", example = "50.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money outcome;
    
    // ==================== 详细分类统计 ====================
    
    @Schema(description = "购物支付金额（SHOPPING类型的OUT流水）", type = "string", example = "30.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money shoppingPayment;
    
    @Schema(description = "活动返利收入金额（ACTIVITY_REBATE类型的IN流水）", type = "string", example = "10.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money activityRebateIncome;
    
    @Schema(description = "用户转账收入金额（USER_TRANSFER类型的IN流水）", type = "string", example = "20.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money userTransferIncome;
    
    @Schema(description = "用户转账支出金额（USER_TRANSFER类型的OUT流水）", type = "string", example = "15.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money userTransferExpense;
    
    @Schema(description = "商户优惠减免收入金额（MERCHANT_DISCOUNT_REDUCTION类型的IN流水）", type = "string", example = "5.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantDiscountIncome;
    
    @Schema(description = "商户减价收入金额（MERCHANT_PRICE_REDUCTION类型的IN流水）", type = "string", example = "3.00")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money merchantPriceReductionIncome;
    
    @Schema(description = "手续费支出金额（所有交易的手续费总和）", type = "string", example = "1.50")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money feeExpense;
    
    // ==================== 按交易类型分组的完整统计（供前端灵活展示） ====================
    
    @Schema(description = "按交易类型分组的统计列表")
    private List<TransactionTypeStatistics> transactionTypeStatistics;
}
