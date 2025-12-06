package org.ares.cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: 修改密码
 * @date 2024/12/7 13:50
 */
@Data
public class ChangePasswordRequest {
    @Schema(description = "旧密码")
    @JsonProperty(value = "old_password")
    @NotBlank(message = "{validation.old_password.notBlank}")
    @Size(min=8,max = 16,message = "{validation.password}")
    private String oldPassword;
    @Schema(description = "新密码")
    @JsonProperty(value = "new_password")
    @NotBlank(message = "{validation.new_password.notBlank}")
    @Size(min=8,max = 16,message = "{validation.password}")
    private String newPassword;
    @Schema(description = "验证码验证后返回的签名")
    @JsonProperty(value = "sign")
    private String sign;
}
