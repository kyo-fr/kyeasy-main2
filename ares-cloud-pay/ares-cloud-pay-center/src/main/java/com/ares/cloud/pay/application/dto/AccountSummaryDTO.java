package com.ares.cloud.pay.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 账户摘要DTO
 */
@Data
@Schema(description = "账户摘要信息")
public class AccountSummaryDTO {
    
    /**
     * 账户ID
     */
    @Schema(description = "账户ID", example = "account_123456")
    private String id;
    
    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "user_123456")
    private String userId;
    
    /**
     * 国家代码
     */
    @Schema(description = "国家代码", example = "+86")
    private String countryCode;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
    
    /**
     * 账号
     */
    @Schema(description = "账号", example = "ACC20231201001")
    private String account;
    
    /**
     * 账户状态
     */
    @Schema(description = "账户状态", example = "ACTIVE", allowableValues = {"ACTIVE", "FROZEN", "CLOSED"})
    private String status;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "1640995200000")
    private Long createTime;
} 