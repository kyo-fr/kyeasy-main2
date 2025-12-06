package com.ares.cloud.pay.domain.model;

import lombok.Data;

/**
 * 账户领域模型
 */
@Data
public class Account {
    
    /**
     * 账户ID
     */
    private String id;
    
    /**
     * 租户ID
     */
    private String tenantId;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 支付密码
     */
    private String payPassword;
    
    /**
     * 国家代码
     */
    private String countryCode;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 账号
     */
    private String account;
    
    /**
     * 账户状态（ACTIVE:激活, FROZEN:冻结, CLOSED:关闭）
     */
    private String status;
    
    /**
     * 创建者
     */
    private String creator;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新者
     */
    private String updater;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 版本号
     */
    private Integer version;
    
    /**
     * 删除标记
     */
    private Integer deleted;
    
    /**
     * 创建账户
     */
    public static Account create(String userId, String password, String countryCode, 
                               String phone, String account) {
        Account accountObj = new Account();
        accountObj.setId(userId);
        accountObj.setUserId(userId);
        accountObj.setPassword(password);
        accountObj.setCountryCode(countryCode);
        accountObj.setPhone(phone);
        accountObj.setAccount(account);
        accountObj.setStatus("ACTIVE");
        accountObj.setCreateTime(System.currentTimeMillis());
        accountObj.setUpdateTime(System.currentTimeMillis());
        accountObj.setVersion(1);
        accountObj.setDeleted(0);
        return accountObj;
    }
    
    /**
     * 检查账户是否激活
     */
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    /**
     * 检查账户是否冻结
     */
    public boolean isFrozen() {
        return "FROZEN".equals(status);
    }
    
    /**
     * 检查账户是否关闭
     */
    public boolean isClosed() {
        return "CLOSED".equals(status);
    }
    
    /**
     * 激活账户
     */
    public void activate() {
        this.status = "ACTIVE";
        this.updateTime = System.currentTimeMillis();
    }
    
    /**
     * 冻结账户
     */
    public void freeze() {
        this.status = "FROZEN";
        this.updateTime = System.currentTimeMillis();
    }
    
    /**
     * 关闭账户
     */
    public void close() {
        this.status = "CLOSED";
        this.updateTime = System.currentTimeMillis();
    }
} 