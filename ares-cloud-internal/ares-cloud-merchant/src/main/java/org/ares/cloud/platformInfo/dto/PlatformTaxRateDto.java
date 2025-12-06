package org.ares.cloud.platformInfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.checkerframework.checker.units.qual.MixedUnits;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 税率 数据模型
* @version 1.0.0
* @date 2024-10-15
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "税率")
public class PlatformTaxRateDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "类型:1-商品税率(商户)；2-服务税率(商户)；3-运送税率(商户)；4-订阅税率(平台)；5-礼物点税率(平台)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "type")
	@NotBlank(message = "{validation.platformInfo.type.notBlank}")
	@Size(max = 8, message = "validation.size.max")
	private String type;


	@Schema(description = "商户id")
	@JsonProperty(value = "tenantId")
	@Size(max = 32, message = "validation.size.max")
	private String tenantId;

	@Schema(description = "税率",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "taxRate")
	@DecimalMin(value = "0.00")
	@DecimalMax(value = "100.00")
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}