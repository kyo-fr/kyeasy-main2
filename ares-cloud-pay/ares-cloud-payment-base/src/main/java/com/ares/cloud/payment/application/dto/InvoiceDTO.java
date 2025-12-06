package com.ares.cloud.payment.application.dto;

import com.ares.cloud.payment.domain.enums.TransactionPartyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 发票数据传输对象
 */
@Data
@Schema(description = "发票数据传输对象")
public class InvoiceDTO {
    /**
     * 发票ID
     */
    @Schema(description = "发票ID")
    private String invoiceId;
    
    /**
     * 交易方类型
     */
    @Schema(description = "交易方类型")
    private TransactionPartyType transactionPartyType;
    
    /**
     * 付款方信息
     */
    @Schema(description = "付款方信息")
    private PartyDTO payer;
    
    /**
     * 收款方信息
     */
    @Schema(description = "收款方信息")
    private PartyDTO payee;
    
    /**
     * 发票明细项列表
     */
    @Schema(description = "发票明细项列表")
    private List<InvoiceItemDTO> items;
    
    /**
     * 合同ID
     */
    @Schema(description = "合同ID")
    private String contractId;
    
    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private String orderId;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Long createTime;
    
    /**
     * 配送时间
     */
    @Schema(description = "配送时间")
    private Long deliveryTime;
    
    /**
     * 完成时间
     */
    @Schema(description = "完成时间")
    private Long completeTime;
    
    /**
     * 交易ID
     */
    @Schema(description = "交易ID")
    private String transactionId;
    
    /**
     * 税前总价
     */
    @Schema(description = "税前总价")
    private BigDecimal preTaxAmount;
    
    /**
     * 总价（含税）
     */
    @Schema(description = "总价（含税）")
    private BigDecimal totalAmount;
    
    /**
     * 线上支付项列表
     */
    @Schema(description = "线上支付项列表")
    private List<PayItemDTO> onlinePayItems;
    
    /**
     * 线下支付项列表
     */
    @Schema(description = "线下支付项列表")
    private List<PayItemDTO> offlinePayItems;
    
    /**
     * 减免金额
     */
    @Schema(description = "减免金额")
    private BigDecimal deductAmount;
}