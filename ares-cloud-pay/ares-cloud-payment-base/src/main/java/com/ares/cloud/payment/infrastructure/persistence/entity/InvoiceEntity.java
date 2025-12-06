package com.ares.cloud.payment.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.TenantEntity;

/**
 * 发票实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("pay_invoice")
public class InvoiceEntity extends TenantEntity {
    
    /**
     * 交易方类型
     */
    private String transactionPartyType;
    
    /**
     * 付款方ID
     */
    private String payerId;
    
    /**
     * 收款方ID
     */
    private String payeeId;
    
    /**
     * 合同ID
     */
    private String contractId;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 配送时间
     */
    private Long deliveryTime;
    
    /**
     * 完成时间
     */
    private Long completeTime;
    
    /**
     * 交易ID
     */
    private String transactionId;
    
    /**
     * 数字签名hash
     */
    private String digitalSignature;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 币种精度
     */
    private Integer scale;
    
    /**
     * 税前总价
     */
    private Long preTaxAmount;
    
    /**
     * 总价（含税）
     */
    private Long totalAmount;
    
    /**
     * 减免金额
     */
    private Long deductAmount;
    
    /**
     * 发票状态
     */
    private String status;
}