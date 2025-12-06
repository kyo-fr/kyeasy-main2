package com.ares.cloud.payment.application.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

/**
 * 发票查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "发票查询对象")
public class InvoiceQuery extends Query {
    
    @Schema(description = "商户ID")
    @JsonProperty("merchantId")
    private String merchantId;

    @Schema(description = "用户ID")
    @JsonProperty("userId")
    private String userId;
    
    @Schema(description = "开始时间(时间戳)")
    @JsonProperty("startTime")
    private Long startTime;
    
    @Schema(description = "结束时间(时间戳)")
    @JsonProperty("endTime")
    private Long endTime;

    @Schema(description = "交易类型：INCOME-收入, EXPENSE-支出, ALL-全部")
    @JsonProperty("transactionType")
    private TransactionType transactionType = TransactionType.ALL;

    @Schema(description = "发票状态")
    @JsonProperty("status")
    private String status;

    @Schema(description = "订单ID")
    @JsonProperty("orderId")
    private String orderId;

    @Schema(description = "合同ID")
    @JsonProperty("contractId")
    private String contractId;

    /**
     * 交易类型枚举
     */
    public enum TransactionType {
        /** 收入 */
        INCOME,
        /** 支出 */
        EXPENSE,
        /** 全部 */
        ALL
    }
}