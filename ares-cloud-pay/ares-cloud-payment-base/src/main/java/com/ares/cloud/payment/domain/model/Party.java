package com.ares.cloud.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易方领域模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Party {
    
    /**
     * 主键ID
     */
    private String id;
    
    /**
     * 交易方ID
     */
    private String partyId;
    
    /**
     * 关联发票ID
     */
    private String invoiceId;
    
    /**
     * 交易方名称
     */
    private String name;
    
    /**
     * 交易方类型(1:商户,2:个人)
     */
    private Integer partyType;
    
    /**
     * 用户状态(0:未注册,1:已注册)
     */
    private Integer userStatus;
    
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
     * 联系电话
     */
    private String phone;
    
    /**
     * 电子邮箱
     */
    private String email;
    
    /**
     * 国家代码
     */
    private String countryCode;
    
    /**
     * 国家
     */
    private String country;
    
    /**
     * 城市
     */
    private String city;
} 