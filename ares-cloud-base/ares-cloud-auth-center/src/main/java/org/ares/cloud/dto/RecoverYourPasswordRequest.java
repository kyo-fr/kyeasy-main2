package org.ares.cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: 找回密码
 * @date 2024/12/8 01:21
 */
@Data
public class RecoverYourPasswordRequest {
    @Schema(description = "手机号")
    @JsonProperty(value = "phone")
    @NotBlank(message = "{validation.phone.notBlank}")
    private String phone;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    @NotBlank(message = "{validation.phone.countryCode}")
    private String countryCode;

    @Schema(description = "新密码")
    @JsonProperty(value = "new_password")
    @NotBlank(message = "{validation.new_password.notBlank}")
    @Size(min=8,max = 16,message = "{validation.password}")
    private String newPassword;

    @Schema(description = "确认密码")
    @JsonProperty(value = "confirm_password")
    @NotBlank(message = "{validation.confirm_password.notBlank}")
    @Size(min=8,max = 16,message = "{validation.password}")
    private String confirmPassword;

    @Schema(description = "验证码验证后返回的签名")
    @JsonProperty(value = "sign")
    private String sign;
}
