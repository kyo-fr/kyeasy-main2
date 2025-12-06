package com.ares.cloud.pay.domain.enums;

import java.math.BigDecimal;

/**
 * 交易类型枚举
 * 根据优惠点支付类型分类体系定义
 * 注意：手续费需要写死，税率会变动
 */
public enum TransactionType {
    
    // ==================== 用户类型 ====================
    
    /**
     * 购物支付
     * 用户购物时支付优惠点，包括：
     * 1. 子结算时对应线下结算单的礼物点支付金额
     * 2. 线上订单礼物点支付金额
     */
    SHOPPING("SHOPPING", "购物支付", ParticipantType.USER, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "用户购物支付"),
    
    /**
     * 活动返利
     * 商户活动满额返利
     */
    ACTIVITY_REBATE("ACTIVITY_REBATE", "活动返利", ParticipantType.USER, 
            new BigDecimal("5.00"), FeeDeductionTiming.AFTER_RECEIPT, "商户活动满额返利"),
    
    /**
     * 用户转账
     * 用户之间的转账，手续费0.10%由转出方承担（提前扣除）
     * 注意：通过账户流水的flowType区分转账收入(IN)和转账支出(OUT)
     */
    USER_TRANSFER("USER_TRANSFER", "用户转账", ParticipantType.USER, 
            new BigDecimal("0.10"), FeeDeductionTiming.IN_ADVANCE, "用户之间转账"),
    
    /**
     * 商户优惠减免
     * 商户结算时给的百分比优惠减免
     */
    MERCHANT_DISCOUNT_REDUCTION("MERCHANT_DISCOUNT_REDUCTION", "商户优惠减免", ParticipantType.USER, 
            new BigDecimal("5.00"), FeeDeductionTiming.AFTER_RECEIPT, "结算时商户给的百分比优惠减免"),
    
    /**
     * 商户减价
     * 商户结算时给的具体金额减价
     */
    MERCHANT_PRICE_REDUCTION("MERCHANT_PRICE_REDUCTION", "商户减价", ParticipantType.USER, 
            new BigDecimal("5.00"), FeeDeductionTiming.AFTER_RECEIPT, "结算时商户给的具体金额减价"),
    
    // ==================== 商户类型 ====================
    
    /**
     * 系统购买
     * 商户向平台购买礼物点
     */
    MERCHANT_PURCHASE("MERCHANT_PURCHASE", "系统购买", ParticipantType.MERCHANT, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "商户向平台购买礼物点"),
    
    /**
     * 出售给系统
     * 商户向平台出售/归还礼物点
     */
    MERCHANT_SELL("MERCHANT_SELL", "出售给系统", ParticipantType.MERCHANT, 
            new BigDecimal("1.50"), FeeDeductionTiming.IN_ADVANCE, "商户提交礼物点给平台归还/出售"),
    
    /**
     * 活动赠送
     * 商户满额赠送活动
     */
    MERCHANT_ACTIVITY_GIFT("MERCHANT_ACTIVITY_GIFT", "活动赠送", ParticipantType.MERCHANT, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "用户发票满额商户自动赠送，礼物点不足自动关闭满额赠送"),
    
    /**
     * 优惠减免（商户侧）
     * 商户结算时给的百分比优惠减免
     */
    MERCHANT_DISCOUNT_GRANT("MERCHANT_DISCOUNT_GRANT", "优惠减免", ParticipantType.MERCHANT, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "商户结算时给的百分比优惠减免"),
    
    /**
     * 减价（商户侧）
     * 商户结算时给的具体金额减价
     */
    MERCHANT_PRICE_GRANT("MERCHANT_PRICE_GRANT", "减价", ParticipantType.MERCHANT, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "商户结算时给的具体金额减价"),
    
    /**
     * 商户收款
     * 包括：
     * 1. 商户结算时收款
     * 2. 顾客下单用礼物点支付
     */
    MERCHANT_COLLECTION("MERCHANT_COLLECTION", "商户收款", ParticipantType.MERCHANT, 
            new BigDecimal("0.50"), FeeDeductionTiming.AFTER_INCOME, "商户结算收款或顾客下单支付"),
    
    // ==================== 系统类型 ====================
    
    /**
     * 系统出售
     * 系统向商户出售礼物点
     */
    SYSTEM_SALE("SYSTEM_SALE", "系统出售", ParticipantType.SYSTEM, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "系统向商户出售礼物点"),
    
    /**
     * 系统回收
     * 系统从商户回收礼物点
     */
    SYSTEM_RECYCLE("SYSTEM_RECYCLE", "系统回收", ParticipantType.SYSTEM, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "系统从商户回收礼物点"),
    
    /**
     * 退款
     */
    REFUND("REFUND", "退款", ParticipantType.USER, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "退款"),
    
    /**
     * 手续费记录
     */
    FEE("FEE", "手续费", ParticipantType.SYSTEM, 
            new BigDecimal("0.00"), FeeDeductionTiming.NONE, "手续费记录");
    
    /**
     * 交易代码
     */
    private final String code;
    
    /**
     * 交易描述
     */
    private final String description;
    
    /**
     * 参与方类型
     */
    private final ParticipantType participantType;
    
    /**
     * 手续费率（百分比）
     * 注意：手续费需要写死，税率会变动
     */
    private final BigDecimal feeRate;
    
    /**
     * 手续费扣除时机
     */
    private final FeeDeductionTiming feeDeductionTiming;
    
    /**
     * 详细说明
     */
    private final String detailedDescription;
    
    TransactionType(String code, String description, ParticipantType participantType,
                    BigDecimal feeRate, FeeDeductionTiming feeDeductionTiming, 
                    String detailedDescription) {
        this.code = code;
        this.description = description;
        this.participantType = participantType;
        this.feeRate = feeRate;
        this.feeDeductionTiming = feeDeductionTiming;
        this.detailedDescription = detailedDescription;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public ParticipantType getParticipantType() {
        return participantType;
    }
    
    public BigDecimal getFeeRate() {
        return feeRate;
    }
    
    public FeeDeductionTiming getFeeDeductionTiming() {
        return feeDeductionTiming;
    }
    
    public String getDetailedDescription() {
        return detailedDescription;
    }
    
    /**
     * 获取手续费率（万分比）
     * @return 手续费率的万分比表示，如 0.10% 返回 10
     */
    public Integer getFeeRateInBasisPoints() {
        return feeRate.multiply(new BigDecimal("100")).intValue();
    }
    
    /**
     * 是否需要收取手续费
     */
    public boolean hasFee() {
        return feeRate.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * 是否提前扣除手续费
     */
    public boolean isAdvanceDeduction() {
        return feeDeductionTiming == FeeDeductionTiming.IN_ADVANCE;
    }
    
    /**
     * 是否到账后扣除手续费
     */
    public boolean isAfterReceiptDeduction() {
        return feeDeductionTiming == FeeDeductionTiming.AFTER_RECEIPT 
                || feeDeductionTiming == FeeDeductionTiming.AFTER_INCOME;
    }
    
    public static TransactionType fromCode(String code) {
        for (TransactionType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * 参与方类型枚举
     */
    public enum ParticipantType {
        /**
         * 用户
         */
        USER("USER", "用户"),
        
        /**
         * 商户
         */
        MERCHANT("MERCHANT", "商户"),
        
        /**
         * 系统
         */
        SYSTEM("SYSTEM", "系统");
        
        private final String code;
        private final String description;
        
        ParticipantType(String code, String description) {
            this.code = code;
            this.description = description;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * 手续费扣除时机枚举
     */
    public enum FeeDeductionTiming {
        /**
         * 无手续费
         */
        NONE("NONE", "无手续费"),
        
        /**
         * 提前扣除（从支付方扣除）
         */
        IN_ADVANCE("IN_ADVANCE", "提前扣除"),
        
        /**
         * 到账后扣除（从接收方扣除）
         */
        AFTER_RECEIPT("AFTER_RECEIPT", "到账后扣除"),
        
        /**
         * 收入后扣除（从收入方扣除）
         */
        AFTER_INCOME("AFTER_INCOME", "收入后扣除");
        
        private final String code;
        private final String description;
        
        FeeDeductionTiming(String code, String description) {
            this.code = code;
            this.description = description;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getDescription() {
            return description;
        }
    }
}
