package com.ares.cloud.pay.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomMoneySerializer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 按交易类型分组的统计信息
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/27
 */
@Data
@Schema(description = "按交易类型分组的统计")
public class TransactionTypeStatistics implements Serializable {
    
    @Schema(description = "交易类型代码", example = "USER_TO_USER")
    private String transactionType;
    
    @Schema(description = "交易类型描述", example = "用户间转账")
    private String transactionTypeDesc;
    
    @Schema(description = "流水类型：IN-收入，OUT-支出", allowableValues = {"IN", "OUT"})
    private String flowType;
    
    @Schema(description = "流水类型描述", example = "收入")
    private String flowTypeDesc;
    
    @Schema(description = "交易金额")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money amount;
    
    @Schema(description = "交易数量")
    private Integer transactionCount;
    
    @Schema(description = "手续费金额")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money feeAmount;
    
    @Schema(description = "平均手续费率（百分比）", example = "0.50")
    private BigDecimal avgFeeRate;
}

