package org.ares.cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录")
public class LoginRequest {
    @Schema(description = "手机号")
    @JsonProperty(value = "phone")
    @NotBlank(message = "{validation.phone.notBlank}")
    private String phone;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    @NotBlank(message = "{validation.phone.countryCode}")
    private String countryCode;

    @Schema(description = "密码")
    @JsonProperty(value = "password")
    private String password;

    @Schema(description = "域名",hidden = true)
    private String domain;

    @Schema(description = "设备类型",hidden = true)
    private String deviceType;
}
