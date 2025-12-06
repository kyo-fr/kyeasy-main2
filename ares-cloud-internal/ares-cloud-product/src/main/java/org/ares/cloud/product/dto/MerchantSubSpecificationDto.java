package org.ares.cloud.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 子规格主数据 数据模型
* @version 1.0.0
* @date 2025-03-18
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "子规格主数据")
public class MerchantSubSpecificationDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "子规格名称")
	@JsonProperty(value = "subName")
	@NotBlank(message = "{validation.merchant.merchantSubSpecification.subName.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String subName;

	@Schema(description = "子规格库存")
	@NotNull(message = "{validation.merchant.merchantSubSpecification.subNum.notBlank}")
	@JsonProperty(value = "subNum")
	private Integer subNum;

	@Schema(description = "子规格价格")
	@JsonProperty(value = "subPrice")
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
//	@NotNull(message = "{validation.merchant.merchantSubSpecification.subPrice.notBlank}")
	private BigDecimal subPrice;

	@Schema(description = "子规格图片")
	@JsonProperty(value = "subPicture")
//	@NotBlank(message = "{validation.merchant.merchantSubSpecification.subPicture.notBlank}")
	@Size(max = 128, message = "validation.size.max")
	private String subPicture;

	@Schema(description = "主规格id")
	@JsonProperty(value = "specificationId")
	@NotBlank(message = "{validation.merchant.merchantSubSpecification.specificationId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String specificationId;
	/**
	* 获取子规格名称
	* @return 子规格名称
	*/
	public String getSubName() {
	return subName;
	}

	/**
	* 设置子规格名称
	* @param subName 子规格名称
	*/
	public void setSubName(String subName) {
	this.subName = subName;
	}
	/**
	* 获取子规格库存
	* @return 子规格库存
	*/
	public Integer getSubNum() {
	return subNum;
	}

	/**
	* 设置子规格库存
	* @param subNum 子规格库存
	*/
	public void setSubNum(Integer subNum) {
	this.subNum = subNum;
	}
	/**
	* 获取子规格价格
	* @return 子规格价格
	*/
	public BigDecimal getSubPrice() {
	return subPrice;
	}

	/**
	* 设置子规格价格
	* @param subPrice 子规格价格
	*/
	public void setSubPrice(BigDecimal subPrice) {
	this.subPrice = subPrice;
	}
	/**
	* 获取子规格图片
	* @return 子规格图片
	*/
	public String getSubPicture() {
	return subPicture;
	}

	/**
	* 设置子规格图片
	* @param subPicture 子规格图片
	*/
	public void setSubPicture(String subPicture) {
	this.subPicture = subPicture;
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
}