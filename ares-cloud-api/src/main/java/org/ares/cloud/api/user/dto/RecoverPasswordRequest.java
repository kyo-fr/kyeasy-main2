package org.ares.cloud.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/12/9 11:27
 */
@Data
public class RecoverPasswordRequest {
    /**
     * 账号
     */
    private String account;
    /**
     * 新密码
     */
    private String newPassword;
    /**
     * 确认密码
     */
    private String confirmPassword;
}
