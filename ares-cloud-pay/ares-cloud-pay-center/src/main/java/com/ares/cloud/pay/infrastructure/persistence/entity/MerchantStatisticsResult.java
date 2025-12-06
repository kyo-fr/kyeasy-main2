package com.ares.cloud.pay.infrastructure.persistence.entity;

import lombok.Data;

/**
 * 商户统计查询结果
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/26
 */
@Data
public class MerchantStatisticsResult {
    
    // ==================== 顶部摘要统计 ====================
    
    /**
     * 手续费总支出（以分为单位）
     */
    private Long totalFeeExpenditure;
    
    /**
     * 手续费总支出对应的交易数量
     */
    private Integer totalFeeTransactionCount;
    
    /**
     * 商户手续费（以分为单位）
     */
    private Long merchantFee;
    
    /**
     * 商户手续费对应的交易数量
     */
    private Integer merchantFeeTransactionCount;
    
    // ==================== 详细统计 - 收入类 ====================
    
    /**
     * 商户收款收入（MERCHANT_COLLECTION - IN）（以分为单位）
     */
    private Long merchantCollectionIncome;
    
    /**
     * 商户收款交易数量
     */
    private Integer merchantCollectionTransactionCount;
    
    /**
     * 商户收款手续费（以分为单位）
     */
    private Long merchantCollectionFee;
    
    /**
     * 系统购买收入（MERCHANT_PURCHASE - IN）（以分为单位）
     */
    private Long merchantPurchaseIncome;
    
    /**
     * 系统购买交易数量
     */
    private Integer merchantPurchaseTransactionCount;
    
    /**
     * 系统购买手续费（以分为单位）
     */
    private Long merchantPurchaseFee;
    
    // ==================== 详细统计 - 支出类 ====================
    
    /**
     * 活动赠送支出（MERCHANT_ACTIVITY_GIFT - OUT）（以分为单位）
     */
    private Long activityGiftExpenditure;
    
    /**
     * 活动赠送交易数量
     */
    private Integer activityGiftTransactionCount;
    
    /**
     * 活动赠送手续费（以分为单位）
     */
    private Long activityGiftFee;
    
    /**
     * 优惠减免支出（MERCHANT_DISCOUNT_GRANT - OUT）（以分为单位）
     */
    private Long discountGrantExpenditure;
    
    /**
     * 优惠减免交易数量
     */
    private Integer discountGrantTransactionCount;
    
    /**
     * 优惠减免手续费（以分为单位）
     */
    private Long discountGrantFee;
    
    /**
     * 减价支出（MERCHANT_PRICE_GRANT - OUT）（以分为单位）
     */
    private Long priceGrantExpenditure;
    
    /**
     * 减价交易数量
     */
    private Integer priceGrantTransactionCount;
    
    /**
     * 减价手续费（以分为单位）
     */
    private Long priceGrantFee;
    
    /**
     * 出售给系统支出（MERCHANT_SELL - OUT）（以分为单位）
     */
    private Long merchantSellExpenditure;
    
    /**
     * 出售给系统交易数量
     */
    private Integer merchantSellTransactionCount;
    
    /**
     * 出售给系统手续费（以分为单位）
     */
    private Long merchantSellFee;
}

