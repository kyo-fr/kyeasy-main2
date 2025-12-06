package com.ares.cloud.pay.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;
import com.ares.cloud.pay.domain.constant.PaymentConstants;

import java.util.List;

/**
 * 商户领域模型
 */
@Data
@Accessors(chain = true)
public class Merchant {
    
    /**
     * 商户ID
     */
    private String id;
    
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
     * 支持的支付区域
     */
    private List<String> supportedRegions;
    
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
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 付款密码
     */
    private String payPassword;
    
    /**
     * 检查商户是否可用
     */
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    /**
     * 检查商户是否支持指定支付区域
     */
    public boolean supportsRegion(String region) {
        return supportedRegions != null && supportedRegions.contains(region);
    }
    
    /**
     * 检查商户支持的支付区域是否都在系统支持的范围内
     * 
     * @return 如果所有区域都在支持范围内返回true，否则返回false
     */
    public boolean areRegionsValid() {
        if (supportedRegions == null || supportedRegions.isEmpty()) {
            return false;
        }
        
        return supportedRegions.stream()
                .allMatch(region -> PaymentConstants.SUPPORTED_REGIONS.contains(region));
    }
    
    /**
     * 获取无效的支付区域列表
     * 
     * @return 返回不在系统支持范围内的区域列表
     */
    public List<String> getInvalidRegions() {
        if (supportedRegions == null || supportedRegions.isEmpty()) {
            return List.of();
        }
        
        return supportedRegions.stream()
                .filter(region -> !PaymentConstants.SUPPORTED_REGIONS.contains(region))
                .toList();
    }
} 