package com.ares.cloud.payment.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.TenantEntity;

import java.math.BigDecimal;

/**
 * 发票明细实体类
 */

@Data
@TableName("pay_invoice_item")
public class InvoiceItemEntity {
    
    /**
     * 明细项ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    
    /**
     * 发票ID
     */
    private String invoiceId;
    
    /**
     * 商品ID
     */
    private String productId;
    
    /**
     * 订单项ID
     */
    private String orderItemId;
    
    /**
     * 商品名称
     */
    private String productName;
    
    /**
     * 数量
     */
    private Integer quantity;
    
    /**
     * 原价
     */
    private Long originalPrice;
    
    /**
     * 单价
     */
    private Long unitPrice;
    
    /**
     * 税率
     */
    private BigDecimal taxRate;
    
    /**
     * 税额
     */
    private Long taxAmount;
    
    /**
     * 总价（含税）
     */
    private Long totalAmount;
    
    /**
     * 备注
     */
    private String remark;
}