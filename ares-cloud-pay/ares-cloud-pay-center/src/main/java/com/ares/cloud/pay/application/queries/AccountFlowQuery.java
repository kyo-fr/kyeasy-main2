package com.ares.cloud.pay.application.queries;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

import java.math.BigDecimal;

/**
 * 账户流水查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "账户流水查询对象")
public class AccountFlowQuery extends Query {

    /**
     * 账户ID
     */
    @Schema(description = "账户ID")
    @JsonProperty("accountId")
    private String accountId;

    /**
     * 交易ID
     */
    @Schema(description = "交易ID")
    @JsonProperty("transactionId")
    private String transactionId;

    /**
     * 流水类型
     */
    @Schema(description = "流水类型", allowableValues = {"IN", "OUT"})
    @JsonProperty("flowType")
    private String flowType;

    /**
     * 交易类型
     */
    @Schema(description = "交易类型", allowableValues = {"USER_TO_USER", "USER_TO_MERCHANT", "MERCHANT_TO_USER", "PLATFORM_RECHARGE", "PLATFORM_DEDUCTION"})
    @JsonProperty("transactionType")
    private String transactionType;

    /**
     * 支付区域
     */
    @Schema(description = "支付区域", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    @JsonProperty("paymentRegion")
    private String paymentRegion;

    /**
     * 最小金额（以元为单位，支持小数）
     */
    @Schema(description = "最小金额（以元为单位，支持小数）")
    @JsonProperty("minAmount")
    private BigDecimal minAmount;

    /**
     * 最大金额（以元为单位，支持小数）
     */
    @Schema(description = "最大金额（以元为单位，支持小数）")
    @JsonProperty("maxAmount")
    private BigDecimal maxAmount;
} 