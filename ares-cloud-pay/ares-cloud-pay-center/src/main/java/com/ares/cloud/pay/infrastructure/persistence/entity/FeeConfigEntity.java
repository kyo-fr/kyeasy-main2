package com.ares.cloud.pay.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;

/**
 * 手续费配置实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("fee_config")
public class FeeConfigEntity extends BaseEntity {
    
    /**
     * 交易类型
     */
    private String transactionType;
    
    /**
     * 支付区域
     */
    private String paymentRegion;
    
    /**
     * 手续费百分比（以万分比为单位，如100表示1%）
     */
    private Integer feeRate;
    
    /**
     * 最低手续费金额（以分为单位）
     */
    private Long minFeeAmount;
    
    /**
     * 最高手续费金额（以分为单位）
     */
    private Long maxFeeAmount;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
} 