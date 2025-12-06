package org.ares.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商品分类管理 数据模型
* @version 1.0.0
* @date 2024-10-28
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品分类管理")
public class ProductTypeDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "分类名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.productType.name.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String name;

	@Schema(description = "图片",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "picture")
	@NotBlank(message = "{validation.productType.picture.notBlank}")
	@Size(max = 2000, message = "validation.size.max")
	private String picture;

	@Schema(description = "父级id")
	@JsonProperty(value = "parentId")
	@Size(max = 32, message = "validation.size.max")
	private String parentId;

	@Schema(description = "分类级别 1:1级,2:2级,3:3级,",requiredMode = Schema.RequiredMode.REQUIRED,defaultValue = "1")
	@JsonProperty(value = "levels")
	@NotNull(message = "{validation.productType.level.notBlank}")
	private Integer levels;
	/**
	* 获取分类名称
	* @return 分类名称
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置分类名称
	* @param name 分类名称
	*/
	public void setName(String name) {
	this.name = name;
	}
	/**
	* 获取图片
	* @return 图片
	*/
	public String getPicture() {
	return picture;
	}

	/**
	* 设置图片
	* @param picture 图片
	*/
	public void setPicture(String picture) {
	this.picture = picture;
	}
	/**
	* 获取父级id
	* @return 父级id
	*/
	public String getParentId() {
	return parentId;
	}

	/**
	* 设置父级id
	* @param parentId 父级id
	*/
	public void setParentId(String parentId) {
	this.parentId = parentId;
	}
	/**
	* 获取分类级别
	* @return 分类级别
	*/
	public Integer getLevels() {
	return levels;
	}

	/**
	* 设置分类级别
	* @param level 分类级别
	*/
	public void setLevels(Integer levels) {
	this.levels = levels;
	}
}