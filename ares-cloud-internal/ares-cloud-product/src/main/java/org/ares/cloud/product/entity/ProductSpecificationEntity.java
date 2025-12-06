package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商品规格 实体
* @version 1.0.0
* @date 2025-03-18
*/
@TableName("product_specification")
@EqualsAndHashCode(callSuper = false)
public class ProductSpecificationEntity extends BaseEntity {
	/**
	* 商品id
	*/
	private String productId;

	/**
	* 主规格id
	*/
	@TableField(fill = FieldFill.UPDATE)
	private String specificationId;
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
}