package org.ares.cloud.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品分类管理 数据模型
* @version 1.0.0
* @date 2024-10-28
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品分类管理")
public class ProductTypeVo extends TenantDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "id")
	private String id;

	@Schema(description = "分类名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	private String name;

	@Schema(description = "图片",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "picture")
	private String picture;

	@Schema(description = "父级id")
	@JsonProperty(value = "parentId")
	private String parentId;

	@Schema(description = "分类级别 1:1级,2:2级,3:3级,",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "levels")
	private Integer levels;
	@Schema(description = "是否存在子集,true:存在；false:不存在",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "hasChildren")
	private boolean hasChildren;

	@Schema(description = "子集",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "children")
	private List<ProductTypeVo> children;

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
	* @param levels 分类级别
	*/
	public void setLevels(Integer levels) {
	this.levels = levels;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@Override
	public String getId() {
		return id;
	}

	public List<ProductTypeVo> getChildren() {
		return children;
	}

	public void setChildren(List<ProductTypeVo> children) {
		this.children = children;
	}
	public void setId(String id) {
		this.id = id;
	}


}