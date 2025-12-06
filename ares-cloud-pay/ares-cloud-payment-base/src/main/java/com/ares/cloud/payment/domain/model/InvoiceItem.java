package com.ares.cloud.payment.domain.model;

import org.ares.cloud.common.model.Money;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 发票明细项领域模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {
    /**
     * 明细项ID
     */
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
    private Money originalPrice;
    
    /**
     * 单价
     */
    private Money unitPrice;
    
    /**
     * 税率
     */
    private BigDecimal taxRate;
    
    /**
     * 税额
     */
    private Money taxAmount;
    
    /**
     * 总价（含税）
     */
    private Money totalAmount;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 计算税额和总金额
     */
    public void calculate() {
        // 计算项目税前总价
        Money preTaxAmount = unitPrice.multiply(quantity);
        
        // 计算税额 - taxRate是百分比值，需要转换为小数再计算
        // 例如：taxRate为13.00表示13%，需要除以100变成0.13再计算
        BigDecimal taxRateDecimal = taxRate.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        taxAmount = preTaxAmount.multiply(taxRateDecimal);
        
        // 计算含税总价
        totalAmount = preTaxAmount.add(taxAmount);
    }
}