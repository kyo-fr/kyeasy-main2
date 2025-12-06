package org.ares.cloud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/28 10:19
 */
@Data
public class RefTokenRequest {
    @Schema(description = "refToken")
    @JsonProperty(value = "refToken")
    private String refToken;
}
