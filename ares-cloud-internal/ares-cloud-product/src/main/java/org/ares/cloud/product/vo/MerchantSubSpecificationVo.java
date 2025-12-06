package org.ares.cloud.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商品子规格 数据模型
* @version 1.0.0
* @date 2025-03-16
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品子规格")
public class MerchantSubSpecificationVo extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "子规格名称",requiredMode = Schema.RequiredMode.REQUIRED)
	private String subName;

	@Schema(description = "子规格库存",requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer subNum;

	@Schema(description = "子规格价格",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal subPrice;

	@Schema(description = "子规格图片",requiredMode = Schema.RequiredMode.REQUIRED)
	private String subPicture;

	@Schema(description = "主规格id",requiredMode = Schema.RequiredMode.REQUIRED)
	private String specificationId;


	@Schema(description = "主规格名称",requiredMode = Schema.RequiredMode.REQUIRED)
	private String specificationName;


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

	public String getSpecificationName() {
		return specificationName;
	}

	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
}