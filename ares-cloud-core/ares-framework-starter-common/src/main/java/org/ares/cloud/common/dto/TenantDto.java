package org.ares.cloud.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author hugo
 * @version 1.0
 * @description: 租户
 * @date 2024/9/30 17:16
 */
public class TenantDto extends BaseDto {
    @Schema(description = "租户",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "tenantId")
    @NotBlank(message = "{validation.tenantId.notBlank}")
    @Size(max = 32, message = "validation.size.max")
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
