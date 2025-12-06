package org.ares.cloud.api.merchant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "税率")
public class PlatformTaxRateDto {

    @Schema(description = "类型:1-商品税率；2-服务税率；3:配送税率；4:回扣税率",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "type")
    @NotBlank(message = "{validation.platformInfo.type.notBlank}")
    @Size(max = 8, message = "validation.size.max")
    private String type;

    @Schema(description = "税率",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "taxRate")
    private BigDecimal taxRate;
    /**
     * 获取类型
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * 获取税率
     * @return 税率
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * 设置税率
     * @param taxRate 税率
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
