package org.ares.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商品子规格 数据模型
* @version 1.0.0
* @date 2025-03-24
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品子规格")
public class ProductSubSpecificationDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "商品id")
	@JsonProperty(value = "productId")
	@Size(max = 32, message = "validation.size.max")
	private String productId;

	@Schema(description = "子规格id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "subSpecificationId")
	@NotBlank(message = "{validation.product.sub.specification.id.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String subSpecificationId;

	@Schema(description = "主规格id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "specificationId")
	@NotBlank(message = "{validation.product.sub.specificationId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String specificationId;

	@Schema(description = "子规格名称")
	@JsonProperty(value = "subSpecificationName")
	@Size(max = 32, message = "validation.size.max")
	private String subSpecificationName;


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
	* 获取子规格id
	* @return 子规格id
	*/
	public String getSubSpecificationId() {
	return subSpecificationId;
	}

	/**
	* 设置子规格id
	* @param subSpecificationId 子规格id
	*/
	public void setSubSpecificationId(String subSpecificationId) {
	this.subSpecificationId = subSpecificationId;
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

	public String getSubSpecificationName() {
	return subSpecificationName;
	}

	public void setSubSpecificationName(String subSpecificationName) {
	this.subSpecificationName = subSpecificationName;
	}
}