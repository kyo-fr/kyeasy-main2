package com.ares.cloud.pay.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商户摘要DTO
 */
@Data
@Schema(description = "商户摘要信息")
public class MerchantSummaryDTO {
    
    /**
     * 商户ID
     */
    @Schema(description = "商户ID", example = "merchant_123456")
    private String id;
    
    /**
     * 商户号
     */
    @Schema(description = "商户号", example = "MCH20231201001")
    private String merchantNo;
    
    /**
     * 商户名称
     */
    @Schema(description = "商户名称", example = "示例商户有限公司")
    private String merchantName;
    
    /**
     * 商户类型
     */
    @Schema(description = "商户类型", example = "BUSINESS", allowableValues = {"BUSINESS", "INDIVIDUAL"})
    private String merchantType;
    
    /**
     * 商户状态
     */
    @Schema(description = "商户状态", example = "ACTIVE", allowableValues = {"ACTIVE", "FROZEN", "CLOSED"})
    private String status;
    
    /**
     * 联系人
     */
    @Schema(description = "联系人", example = "张三")
    private String contactPerson;
    
    /**
     * 联系电话
     */
    @Schema(description = "联系电话", example = "13800138000")
    private String contactPhone;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "1640995200000")
    private Long createTime;
} 