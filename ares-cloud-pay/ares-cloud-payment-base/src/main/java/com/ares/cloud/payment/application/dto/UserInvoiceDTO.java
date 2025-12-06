package com.ares.cloud.payment.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户视图的发票DTO
 */
@Data
@Schema(description = "用户视图的发票DTO")
public class UserInvoiceDTO {
    
    @Schema(description = "发票ID")
    private String invoiceId;
    
    @Schema(description = "订单ID")
    private String orderId;
    
    @Schema(description = "状态")
    private String status;
    
    @Schema(description = "对方名称")
    private String counterpartyName;

    @Schema(description = "对方手机号")
    private String counterpartyPhone;

    @Schema(description = "对方类型(1:商户,2:个人)")
    private Integer counterpartyType;
    
    @Schema(description = "创建时间")
    private Long createTime;
    
    @Schema(description = "金额")
    private BigDecimal amount;
} 