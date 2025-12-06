package org.ares.cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: firebase 验证码校验
 * @date 2024/10/10 17:05
 */
@Data
@Schema(description = "firebase 验证码校验")
public class VerificationCodeVerifyRequest {

    @Schema(description = "手机号")
    @JsonProperty(value = "phone")
    @NotBlank(message = "{validation.phone.notBlank}")
    private String phone;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    @NotBlank(message = "{validation.phone.countryCode}")
    @Size(max = 6, message = "{validation.size.max}")
    private String countryCode;

    @Schema(description = "用户输入的验证码")
    @JsonProperty(value = "code")
    @NotBlank(message = "{validation.phone.code}")
    private String code;
}
