package com.ares.cloud.pay.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomMoneySerializer;

/**
 * 账户流水DTO
 */
@Data
@Schema(description = "账户流水信息")
public class AccountFlowDTO {
    
    /**
     * 流水ID
     */
    @Schema(description = "流水ID", example = "flow_123456")
    private String id;
    
    /**
     * 账户ID
     */
    @Schema(description = "账户ID", example = "account_123456")
    private String accountId;
    
    /**
     * 交易ID
     */
    @Schema(description = "交易ID", example = "txn_123456")
    private String transactionId;

    /**
     * 支付区域
     */
    @Schema(description = "支付区域", example = "CNY")
    private String paymentRegion;
    
    /**
     * 流水类型
     */
    @Schema(description = "流水类型", example = "IN", allowableValues = {"IN", "OUT"})
    private String flowType;
    
    /**
     * 变动金额
     */
    @Schema(description = "变动金额",type = "string")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money amount;
    
    /**
     * 手续费金额
     */
    @Schema(description = "手续费金额",type = "string")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money feeAmount;
    
    /**
     * 手续费百分比（以万分比为单位，如100表示1%）
     */
    @Schema(description = "手续费百分比", example = "100")
    private Integer feeRate;
    
    /**
     * 实际到账金额（扣除手续费后的金额）
     */
    @Schema(description = "实际到账金额",type = "string")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money actualAmount;
    
    /**
     * 变动前余额
     */
    @Schema(description = "变动前余额",type = "string")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money balanceBefore;
    
    /**
     * 变动后余额
     */
    @Schema(description = "变动后余额",type = "string")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money balanceAfter;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "1640995200000")
    private Long createTime;
    
    /**
     * 交易对方账户ID
     */
    @Schema(description = "交易对方账户ID", example = "account_789012")
    private String counterpartyAccountId;
    
    /**
     * 交易对方国家编号
     */
    @Schema(description = "交易对方国家编号", example = "+86")
    private String counterpartyCountryCode;
    
    /**
     * 交易对方手机号
     */
    @Schema(description = "交易对方手机号", example = "13800138000")
    private String counterpartyPhone;
    
    /**
     * 交易对方账号
     */
    @Schema(description = "交易对方账号", example = "ACC20231201002")
    private String counterpartyAccount;
    
    /**
     * 交易对方类型（ACCOUNT/MERCHANT）
     */
    @Schema(description = "交易对方类型", example = "ACCOUNT", allowableValues = {"ACCOUNT", "MERCHANT"})
    private String counterpartyType;
    
    /**
     * 交易对方名称（商户名称或用户账号）
     */
    @Schema(description = "交易对方名称", example = "张三")
    private String counterpartyName;
    
    /**
     * 交易描述
     */
    @Schema(description = "交易描述", example = "用户转账")
    private String description;
    
    /**
     * 交易类型
     */
    @Schema(description = "交易类型", example = "USER_TO_USER", allowableValues = {"USER_TO_USER", "USER_TO_MERCHANT", "MERCHANT_TO_USER", "PLATFORM_RECHARGE", "PLATFORM_DEDUCTION"})
    private String transactionType;
} 