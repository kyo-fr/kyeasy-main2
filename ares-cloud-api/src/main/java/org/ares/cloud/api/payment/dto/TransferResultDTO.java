package org.ares.cloud.api.payment.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomMoneySerializer;

/**
 * 转账结果DTO
 */
@Data
@Schema(description = "转账结果信息")
public class TransferResultDTO {
    
    /**
     * 交易ID
     */
    @Schema(description = "交易ID", example = "txn_123456789")
    private String transactionId;
    
    /**
     * 转账金额
     */
    @Schema(description = "转账金额")
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Money amount;
    
    /**
     * 支付区域
     */
    @Schema(description = "支付区域", example = "CNY", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    private String paymentRegion;
    
    /**
     * 转账说明
     */
    @Schema(description = "转账说明", example = "用户转账")
    private String description;
    
    /**
     * 交易状态
     */
    @Schema(description = "交易状态", example = "SUCCESS", allowableValues = {"SUCCESS", "FAILED", "PENDING"})
    private String status;
    
    /**
     * 结果消息
     */
    @Schema(description = "结果消息", example = "转账成功")
    private String message;
    
    /**
     * 转出方ID
     */
    @Schema(description = "转出方ID", example = "user_123456")
    private String fromId;
    
    /**
     * 转入方ID
     */
    @Schema(description = "转入方ID", example = "user_789012")
    private String toId;
    
    /**
     * 交易时间
     */
    @Schema(description = "交易时间", example = "1640995200000")
    private Long transactionTime;
} 