package com.ares.cloud.pay.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 商户详情DTO
 */
@Data
@Schema(description = "商户详细信息")
public class MerchantDetailDTO {
    
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
     * 联系邮箱
     */
    @Schema(description = "联系邮箱", example = "contact@example.com")
    private String contactEmail;
    
    /**
     * 商户地址
     */
    @Schema(description = "商户地址", example = "北京市朝阳区xxx街道xxx号")
    private String address;
    
    /**
     * 营业执照号
     */
    @Schema(description = "营业执照号", example = "91110000123456789X")
    private String businessLicense;
    
    /**
     * 法人代表
     */
    @Schema(description = "法人代表", example = "李四")
    private String legalRepresentative;
    
    /**
     * 支持的支付区域
     */
    @Schema(description = "支持的支付区域", example = "[\"EUR\", \"USD\", \"CNY\"]")
    private List<String> supportedRegions;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "1640995200000")
    private Long createTime;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "1640995200000")
    private Long updateTime;
    
    /**
     * 钱包列表（五个区域的钱包信息）
     */
    @Schema(description = "钱包列表（包含EUR、USD、CNY、CHF、GBP五个区域的钱包信息）")
    private List<WalletDTO> wallets;
} 