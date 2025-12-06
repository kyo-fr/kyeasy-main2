package com.ares.cloud.payment.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 发票明细项数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发票明细项数据传输对象")
public class InvoiceItemDTO {
    /**
     * 明细项ID
     */
    @Schema(description = "明细项ID")
    private String id;
    
    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String productId;
    
    /**
     * 订单项ID
     */
    @Schema(description = "订单项ID")
    private String orderItemId;
    
    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;
    
    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer quantity;
    
    /**
     * 原价
     */
    @Schema(description = "原价")
    private BigDecimal originalPrice;
    
    /**
     * 单价
     */
    @Schema(description = "单价")
    private BigDecimal unitPrice;
    
    /**
     * 税率
     */
    @Schema(description = "税率")
    private BigDecimal taxRate;
    
    /**
     * 税额
     */
    @Schema(description = "税额")
    private BigDecimal taxAmount;
    
    /**
     * 总价（含税）
     */
    @Schema(description = "总价（含税）")
    private BigDecimal totalAmount;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}