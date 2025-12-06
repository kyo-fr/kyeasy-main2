package org.ares.cloud.api.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;

/**
* @author hugo tangxkwork@163.com
* @description 商品规格 数据模型
* @version 1.0.0
* @date 2025-03-18
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品规格")
public class ProductSpecificationDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "商品id",hidden = true,requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "productId")
//	@NotBlank(message = "{validation.product.specification.productId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String productId;

	@Schema(description = "主规格id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "specificationId")
	@NotBlank(message = "{validation.product.specification.specificationId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String specificationId;

	@Schema(description = "主规格名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "specificationName")
	private String specificationName;


	@Schema(description = "规格类型(单选：radio，多选：choices)")
	@JsonProperty(value = "type")
	private String type;

	/**
	* 获取商品id
	* @return 商品id
	*/
	public String getProductId() {
	return productId;
	}

	/**
	* 设置商品id
	* @param productId 商品id
	*/
	public void setProductId(String productId) {
	this.productId = productId;
	}
	/**
	* 获取主规格id
	* @return 主规格id
	*/
	public String getSpecificationId() {
	return specificationId;
	}

	/**
	* 设置主规格id
	* @param specificationId 主规格id
	*/
	public void setSpecificationId(String specificationId) {
	this.specificationId = specificationId;
	}

	public String getSpecificationName() {
		return specificationName;
	}

	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}