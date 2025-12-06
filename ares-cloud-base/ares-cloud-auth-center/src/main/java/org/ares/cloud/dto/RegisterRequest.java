package org.ares.cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/9 00:06
 */
@Data
@Schema(description = "注册账号")
public class RegisterRequest {
    @Schema(description = "手机号")
    @JsonProperty(value = "phone")
    private String phone;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    private String countryCode;

    @Schema(description = "密码")
    @JsonProperty(value = "password")
    private String password;

    @Schema(description = "确认密码")
    @JsonProperty(value = "confirmPassword")
    private String confirmPassword;

    @Schema(description = "昵称")
    @JsonProperty(value = "nickname")
    private String nickname;

    @Schema(description = "邮箱")
    @JsonProperty(value = "email")
    private String email;

    @Schema(description = "验证码验证后返回的签名")
    @JsonProperty(value = "sign")
    private String sign;
}
