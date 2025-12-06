package org.ares.cloud.product.entity;

import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商品分类管理 实体
* @version 1.0.0
* @date 2024-10-28
*/
@TableName("product_type")
@EqualsAndHashCode(callSuper = false)
public class ProductTypeEntity extends TenantEntity {
	/**
	* 分类名称
	*/
	private String name;
	/**
	* 图片
	*/
	private String picture;
	/**
	* 父级id
	*/
	private String parentId;
	/**
	* 分类级别
	*/
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
	public void setLevels(Integer level) {
		this.levels = level;
	}
}