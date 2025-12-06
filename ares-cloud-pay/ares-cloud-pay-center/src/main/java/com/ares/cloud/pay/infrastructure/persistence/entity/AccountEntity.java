package com.ares.cloud.pay.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
 * 账户实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("accounts")
public class AccountEntity extends BaseEntity {
    
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 密码
     */
    private String password;
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
} 