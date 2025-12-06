package org.ares.cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "登录账号")
public class LoginResponse {
    @Schema(description = "令牌")
    @JsonProperty(value = "accessToken")
    private String accessToken;

    @Schema(description = "令牌过期时间")
    @JsonProperty(value = "expiresIn")
    private Long expiresIn;

    @Schema(description = "刷新令牌")
    @JsonProperty(value = "refreshToken")
    private String refreshToken;

    @Schema(description = "令牌类型")
    @JsonProperty(value = "tokenType")
    private String tokenType;

    @Schema(description = "刷新令牌过期时间")
    @JsonProperty(value = "refreshTokenExpiresIn")
    private Long refreshTokenExpiresIn;
}

