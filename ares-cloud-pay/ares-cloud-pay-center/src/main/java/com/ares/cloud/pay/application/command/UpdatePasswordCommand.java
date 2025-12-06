package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 通用更新密码命令
 * 支持商户和账户的密码更新，封装更新密码所需的所有参数
 * <p>
 * 该命令用于商户或用户更新其支付密码，确保账户安全
 * <p>
 * 使用场景：
 * 1. 密码定期更换
 * 2. 密码泄露重置
 * 3. 安全策略要求
 */
@Data
@Schema(description = "通用更新密码命令对象", title = "通用更新密码命令")
public class UpdatePasswordCommand {
    
    /**
     * 实体类型：MERCHANT 或 ACCOUNT
     * <p>
     * 要更新密码的实体类型，用于区分是商户密码还是账户密码。
     * 支持的类型包括：MERCHANT（商户）、ACCOUNT（账户）。
     */
    @Schema(description = "实体类型", example = "MERCHANT", allowableValues = {"MERCHANT", "ACCOUNT"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String entityType;
    
    /**
     * 实体ID（商户ID或账户ID）
     * <p>
     * 要更新密码的实体ID，用于定位具体的商户或账户记录。
     * 系统会验证该实体是否存在，并更新其密码。
     */
    @Schema(description = "实体ID（商户ID或账户ID）", example = "merchant_123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String entityId;
    
    /**
     * 旧密码
     * <p>
     * 当前的密码，用于验证用户身份和权限。
     * 只有旧密码验证通过后才能更新为新密码。
     */
    @Schema(description = "旧密码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;
    
    /**
     * 新密码
     * <p>
     * 新的密码，用于替换当前的密码。
     * 密码必须包含至少6位字符，包含数字和字母。
     */
    @Schema(description = "新密码", example = "newpass123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
    
    /**
     * 实体类型常量
     */
    public static class EntityType {
        public static final String MERCHANT = "MERCHANT";
        public static final String ACCOUNT = "ACCOUNT";
    }
} 