package com.ares.cloud.pay.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
 * 商户实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("merchants")
public class MerchantEntity extends BaseEntity {
    
    /**
     * 商户号
     */
    private String merchantNo;
    
    /**
     * 商户名称
     */
    private String merchantName;
    
    /**
     * 商户类型
     */
    private String merchantType;
    
    /**
     * 商户状态
     */
    private String status;
    
    /**
     * 支付密钥
     */
    private String paySecret;
    
    /**
     * 支持的支付区域（JSON格式，如：["EUR","USD","CNY"]）
     */
    private String supportedRegions;
    
    /**
     * 联系人
     */
    private String contactPerson;
    
    /**
     * 联系电话
     */
    private String contactPhone;
    
    /**
     * 联系邮箱
     */
    private String contactEmail;
    
    /**
     * 商户地址
     */
    private String address;
    
    /**
     * 营业执照号
     */
    private String businessLicense;
    
    /**
     * 法人代表
     */
    private String legalRepresentative;
    
    /**
     * 付款密码
     */
    private String payPassword;

} 