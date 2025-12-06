package org.ares.cloud.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;

/**
* @author hugo tangxkwork@163.com
* @description 主规格主数据 数据模型
* @version 1.0.0
* @date 2025-03-18
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "主规格主数据")
public class MerchantSpecificationDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "规格类型(单选：radio，多选：choices)")
	@JsonProperty(value = "type")
	@NotBlank(message = "{validation.merchant.merchantSpecification.type.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String type;

	@Schema(description = "主规格名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.merchant.merchantSpecification.name.notBlank}")
	@Size(max = 128, message = "validation.size.max")
	private String name;
	/**
	* 获取规格类型(单选：radio，多选：choices)
	* @return 规格类型(单选：radio，多选：choices)
	*/
	public String getType() {
	return type;
	}

	/**
	* 设置规格类型(单选：radio，多选：choices)
	* @param type 规格类型(单选：radio，多选：choices)
	*/
	public void setType(String type) {
	this.type = type;
	}
	/**
	* 获取主规格名称
	* @return 主规格名称
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置主规格名称
	* @param name 主规格名称
	*/
	public void setName(String name) {
	this.name = name;
	}
}