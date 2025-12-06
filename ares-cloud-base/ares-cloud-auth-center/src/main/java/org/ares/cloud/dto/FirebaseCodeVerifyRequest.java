package org.ares.cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: firebase 验证码校验
 * @date 2024/10/10 17:05
 */
@Data
@Schema(description = "firebase 验证码校验")
public class FirebaseCodeVerifyRequest {
    @Schema(description = "手机号")
    @JsonProperty(value = "phone")
    @NotBlank(message = "{validation.phone.notBlank}")
    private String phone;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    @NotBlank(message = "{validation.phone.countryCode}")
    private String countryCode;

    @Schema(description = "firebase校验返回的token")
    @JsonProperty(value = "recaptchaToken")
    private String recaptchaToken;
}
