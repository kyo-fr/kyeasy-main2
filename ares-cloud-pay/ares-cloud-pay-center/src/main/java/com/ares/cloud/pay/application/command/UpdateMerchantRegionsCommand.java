package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 更新商户区域命令
 * 封装更新商户支持区域所需的所有参数，用于在系统中更新商户支持的支付区域
 * <p>
 * 该命令用于商户业务扩展或调整时，更新其支持的支付货币区域
 * <p>
 * 使用场景：
 * 1. 商户业务扩展
 * 2. 支付区域调整
 * 3. 业务策略变更
 */
@Data
@Schema(description = "更新商户区域命令对象", title = "更新商户区域命令")
public class UpdateMerchantRegionsCommand {
    
    /**
     * 商户ID
     * <p>
     * 要更新支持区域的商户ID，用于定位具体的商户记录。
     * 系统会验证该商户是否存在，并更新其支持的支付区域。
     */
    @Schema(description = "商户ID", example = "merchant_123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantId;
    
    /**
     * 支持的支付区域
     * <p>
     * 商户新支持的支付货币区域，决定商户可以接收哪些货币的支付。
     * 支持的区域包括：EUR（欧元）、USD（美元）、CNY（人民币）、CHF（瑞士法郎）、GBP（英镑）。
     */
    @Schema(description = "支持的支付区域", example = "[\"EUR\", \"USD\", \"CNY\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> supportedRegions;
} 