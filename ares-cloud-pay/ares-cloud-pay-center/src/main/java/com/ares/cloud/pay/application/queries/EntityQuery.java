package com.ares.cloud.pay.application.queries;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

/**
 * 通用实体查询对象
 * 支持商户和账户的查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "通用实体查询对象")
public class EntityQuery extends Query {

    
    /**
     * 实体ID
     */
    @Schema(description = "实体ID")
    @JsonProperty("id")
    private String id;

    
    /**
     * 国家编号
     */
    @Schema(description = "国家编号")
    @JsonProperty("countryCode")
    private String countryCode;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @JsonProperty("phone")
    private String phone;
    
    /**
     * 账号（仅账户查询时使用）
     */
    @Schema(description = "账号")
    @JsonProperty("account")
    private String account;

    
    /**
     * 状态
     */
    @Schema(description = "状态")
    @JsonProperty("status")
    private String status;
} 