package org.ares.cloud.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.ares.cloud.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/10 00:42
 */
@Data
@Builder
@Schema(description = "方法中的数据")
public class AccessTokenClaims {
    @Schema(description = "类型")
    @JsonProperty(value = "type")
    private String type;

    @Schema(description = "用户id")
    @JsonProperty(value = "userId")
    private String userId;

    @Schema(description = "租户id")
    @JsonProperty(value = "tenantId")
    private String tenantId;

    @Schema(description = "角色")
    @JsonProperty(value = "role")
    private String role;

    @Schema(description = "权限")
    @JsonProperty(value = "scope")
    private String scope;

    @Schema(description = "平台id")
    @JsonProperty(value = "platformId")
    private String platformId;

    @Schema(description = "用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)")
    @JsonProperty(value = "identity")
    private Integer identity;

    /**
     * 转换成AccessToken 的
     * @return 转换成string
     */
    public Map<String, Object> toAccessTokenClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "access_token");
        if (StringUtils.isNotBlank(userId)) {
            map.put("userId", userId);
        }
        if (StringUtils.isNotBlank(tenantId)) {
            map.put("tenantId", tenantId);
        }
        if (StringUtils.isNotBlank(role)) {
            map.put("role", role);
        }
        if (StringUtils.isNotBlank(scope)) {
            map.put("scope", scope);
        }
        if (StringUtils.isNotBlank(platformId)) {
            map.put("platformId", platformId);
        }
        if (identity != null) {
            map.put("identity", identity);
        }
        return map;
    }
    /**
     * 转换成RefToken 的
     * @return map结果
     */
    public Map<String, Object> toRefTokenClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "ref_token");
        map.put("userId", userId);
        return map;
    }

    /**
     * 解析结果
     * @param map 解析结果
     * @return 返回数据
     */
    public static AccessTokenClaims fromAccessTokenClaims(Map<String, Object> map) {
        AccessTokenClaimsBuilder builder = AccessTokenClaims.builder();
        if (map.get("type")  != null) {
            builder = builder.type((String) map.get("type"));
        }
        if (map.get("userId")  != null) {
            builder = builder.userId((String) map.get("userId"));
        }
        if (map.get("tenantId")  != null) {
            builder = builder.tenantId((String) map.get("tenantId"));
        }
        if (map.get("role")  != null) {
            builder = builder.role((String) map.get("role"));
        }
        if (map.get("scope")  != null) {
            builder = builder.scope((String) map.get("scope"));
        }
        if (map.get("platformId")  != null) {
            builder = builder.platformId((String) map.get("platformId"));
        }
        if (map.get("identity")  != null) {
            builder = builder.identity((Integer) map.get("identity"));
        }
        return builder.build();
    }
}

