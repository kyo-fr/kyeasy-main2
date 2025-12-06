package com.ares.cloud.payment.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * 商户信息领域模型
 * 包含商户的币种和精度信息
 */
@Data
@Builder
public class MerchantInfo {
    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 商户名称
     */
    private String name;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 精度
     */
    private Integer scale;
    /**
     * 税号
     */
    private String taxId;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;
}