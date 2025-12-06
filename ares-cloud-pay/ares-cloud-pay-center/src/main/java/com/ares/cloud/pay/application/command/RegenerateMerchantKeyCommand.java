package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 重新生成商户密钥命令
 * 封装重新生成商户密钥所需的所有参数，用于在系统中重新生成商户的API密钥
 * <p>
 * 该命令用于商户密钥泄露或定期更换密钥的场景，确保API调用的安全性
 * <p>
 * 使用场景：
 * 1. 商户密钥泄露
 * 2. 定期密钥更换
 * 3. 安全策略要求
 */
@Data
@Schema(description = "重新生成商户密钥命令对象", title = "重新生成商户密钥命令")
public class RegenerateMerchantKeyCommand {
    
    /**
     * 商户ID
     * <p>
     * 要重新生成密钥的商户ID，用于定位具体的商户记录。
     * 系统会验证该商户是否存在，并生成新的API密钥。
     */
    @Schema(description = "商户ID", example = "merchant_123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantId;
} 