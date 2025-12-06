package com.ares.cloud.order.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品规格DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商品规格DTO")
public class ProductSpecificationDTO {
    
    @Schema(description = "规格记录ID")
    @JsonProperty("id")
    private String id;

    @Schema(description = "商品的规格id")
    @JsonProperty("productSpecId")
    private String productSpecId;

    @Schema(description = "规格名称")
    @JsonProperty("name")
    private String name;
    
    @Schema(description = "规格值")
    @JsonProperty("value")
    private String value;
    
    @Schema(description = "规格价格")
    @JsonProperty("price")
    private BigDecimal price;
}