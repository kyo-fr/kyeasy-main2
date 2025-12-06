package com.ares.cloud.pay.infrastructure.persistence.entity;

import lombok.Data;

/**
 * 账户统计查询结果
 */
@Data
public class AccountStatisticsResult {
    
    /**
     * 交易总数
     */
    private Integer total;
    
    /**
     * 收入金额（以最小货币单位为单位）
     */
    private Long income;
    
    /**
     * 支出金额（以最小货币单位为单位）
     */
    private Long outcome;
    
    /**
     * 购物支付（SHOPPING + OUT）（以最小货币单位为单位）
     */
    private Long shoppingPayment;
    
    /**
     * 活动返利收入（ACTIVITY_REBATE + IN）（以最小货币单位为单位）
     */
    private Long activityRebateIncome;
    
    /**
     * 用户转账收入（USER_TRANSFER + IN）（以最小货币单位为单位）
     */
    private Long userTransferIncome;
    
    /**
     * 用户转账支出（USER_TRANSFER + OUT）（以最小货币单位为单位）
     */
    private Long userTransferExpense;
    
    /**
     * 商户优惠减免收入（MERCHANT_DISCOUNT_REDUCTION + IN）（以最小货币单位为单位）
     */
    private Long merchantDiscountIncome;
    
    /**
     * 商户减价收入（MERCHANT_PRICE_REDUCTION + IN）（以最小货币单位为单位）
     */
    private Long merchantPriceReductionIncome;
    
    /**
     * 手续费支出（所有交易的手续费总和）（以最小货币单位为单位）
     */
    private Long feeExpense;
}
