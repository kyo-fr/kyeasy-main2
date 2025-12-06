package com.ares.cloud.pay.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 账户详情DTO
 */
@Data
@Schema(description = "账户详细信息")
public class AccountDetailDTO {
    
    /**
     * 账户ID
     */
    @Schema(description = "账户ID", example = "account_123456")
    private String id;
    
    /**
     * 租户ID
     */
    @Schema(description = "租户ID", example = "tenant_123456")
    private String tenantId;
    
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
     * 创建者
     */
    @Schema(description = "创建者", example = "admin")
    private String creator;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "1640995200000")
    private Long createTime;
    
    /**
     * 更新者
     */
    @Schema(description = "更新者", example = "admin")
    private String updater;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "1640995200000")
    private Long updateTime;
    
    /**
     * 版本号
     */
    @Schema(description = "版本号", example = "1")
    private Integer version;
    
    /**
     * 钱包列表（五个区域的钱包信息）
     */
    @Schema(description = "钱包列表（包含EUR、USD、CNY、CHF、GBP五个区域的钱包信息）")
    private List<WalletDTO> wallets;
} 