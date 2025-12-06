package com.ares.cloud.pay.domain.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 支付常量类
 */
public class PaymentConstants {
    
    /**
     * 支持的支付区域列表
     */
    public static final List<String> SUPPORTED_REGIONS = Arrays.asList("EUR", "USD", "CNY", "CHF", "GBP");
    
    /**
     * 账户类型
     */
    public static class AccountType {
        public static final String USER = "USER";
        public static final String MERCHANT = "MERCHANT";
    }
    
    /**
     * 账户状态
     */
    public static class AccountStatus {
        public static final String ACTIVE = "ACTIVE";
        public static final String FROZEN = "FROZEN";
        public static final String CLOSED = "CLOSED";
    }
    
    /**
     * 商户状态
     */
    public static class MerchantStatus {
        public static final String PENDING = "PENDING";
        public static final String ACTIVE = "ACTIVE";
        public static final String FROZEN = "FROZEN";
        public static final String CLOSED = "CLOSED";
        public static final String REJECTED = "REJECTED";
    }

    public static final String PLATFORM_MERCHANT_ID = "PLATFORM_MERCHANT_001";
} 