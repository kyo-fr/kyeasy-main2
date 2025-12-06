package com.ares.cloud.pay.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
 * 钱包实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("wallets")
public class WalletEntity extends BaseEntity {
    
    /**
     * 账户ID或商户ID
     */
    private String ownerId;
    
    /**
     * 所有者类型（ACCOUNT:账户, MERCHANT:商户）
     */
    private String ownerType;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    private String paymentRegion;
    
    /**
     * 余额（以分为单位）
     */
    private Long balance;
    
    /**
     * 冻结金额（以分为单位）
     */
    private Long frozenAmount;
    
    /**
     * 钱包状态（ACTIVE:激活, FROZEN:冻结, CLOSED:关闭）
     */
    private String status;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
} 