package com.ares.cloud.pay.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 开通账户命令
 * 通过用户ID和支付密码开通账户，系统会自动从用户服务获取用户信息
 */
@Data
@Schema(description = "开通账户命令")
public class OpenAccountCommand {
    /**
     * 支付密码
     */
    @Schema(description = "支付密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payPassword;
    
    /**
     * 确认支付密码
     */
    @Schema(description = "确认支付密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String confirmPayPassword;
    
    /**
     * 验证密码一致性
     * 
     * @return 如果密码一致返回true，否则返回false
     */
    @Schema(hidden = true)
    public boolean isPasswordMatch() {
        if (payPassword == null || confirmPayPassword == null) {
            return false;
        }
        return payPassword.equals(confirmPayPassword);
    }
    
    /**
     * 验证密码强度
     * 密码必须包含至少6位字符，包含数字和字母
     * 
     * @return 如果密码强度符合要求返回true，否则返回false
     */
    @Schema(hidden = true)
    public boolean isPasswordStrong() {
        if (payPassword == null || payPassword.length() < 6) {
            return false;
        }
        
        // 检查是否包含数字
        boolean hasDigit = payPassword.matches(".*\\d.*");
        // 检查是否包含字母
        boolean hasLetter = payPassword.matches(".*[a-zA-Z].*");
        
        return hasDigit && hasLetter;
    }
    
    /**
     * 验证密码格式
     * 密码不能包含特殊字符，只能包含数字和字母
     * 
     * @return 如果密码格式正确返回true，否则返回false
     */
    @Schema(hidden = true)
    public boolean isPasswordFormatValid() {
        if (payPassword == null) {
            return false;
        }
        
        // 只允许数字和字母
        return payPassword.matches("^[a-zA-Z0-9]+$");
    }
} 