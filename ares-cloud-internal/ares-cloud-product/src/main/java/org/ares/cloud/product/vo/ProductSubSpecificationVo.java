package org.ares.cloud.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商品子规格 数据模型
* @version 1.0.0
* @date 2025-03-24
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品子规格")
public class ProductSubSpecificationVo extends BaseDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "子规格id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "subSpecificationId")
	@Size(max = 32, message = "validation.size.max")
	private String subSpecificationId;

	@Schema(description = "主规格id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "specificationId")
	@Size(max = 32, message = "validation.size.max")
	private String specificationId;

	@Schema(description = "子规格名称")
	@JsonProperty(value = "subSpecificationName")
	@Size(max = 32, message = "validation.size.max")
	private String subSpecificationName;


	@Schema(description = "子规格库存")
	@JsonProperty(value = "subNum")
	private Integer subNum;

	@Schema(description = "子规格价格")
	@JsonProperty(value = "subPrice")
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal subPrice;

	@Schema(description = "子规格图片")
	@JsonProperty(value = "subPicture")
	@Size(max = 128, message = "validation.size.max")
	private String subPicture;

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

	public Integer getSubNum() {
		return subNum;
	}

	public BigDecimal getSubPrice() {
		return subPrice;
	}

	public String getSubPicture() {
		return subPicture;
	}

	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
	}

	public void setSubPrice(BigDecimal subPrice) {
		this.subPrice = subPrice;
	}

	public void setSubPicture(String subPicture) {
		this.subPicture = subPicture;
	}
}