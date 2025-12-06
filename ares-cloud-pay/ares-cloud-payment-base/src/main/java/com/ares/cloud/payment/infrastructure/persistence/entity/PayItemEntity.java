package com.ares.cloud.payment.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.TenantEntity;

/**
 * 支付明细实体类
 */
@Data
@TableName("pay_item")
public class PayItemEntity{
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    
    /**
     * 支付渠道ID
     */
    private String channelId;
    
    /**
     * 交易流水号
     */
    private String tradeNo;
    
    /**
     * 支付金额
     */
    private Long amount;
    
    /**
     * 支付时间
     */
    private Long payTime;
    
    /**
     * 支付状态(0:未支付,1:已支付,2:支付失败)
     */
    private Integer status;
    
    /**
     * 发票ID
     */
    private String invoiceId;
    
    /**
     * 支付类型(1:线上支付,2:线下支付)
     */
    private Integer payType;
    
    /**
     * 备注
     */
    private String remark;
}