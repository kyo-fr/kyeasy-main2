package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商品子规格 实体
* @version 1.0.0
* @date 2025-03-24
*/
@TableName("product_sub_specification")
@EqualsAndHashCode(callSuper = false)
public class ProductSubSpecificationEntity extends BaseEntity {
	/**
	* 商品id
	*/
	private String productId;
	/**
	* 子规格id
	*/
	private String subSpecificationId;
	/**
	* 主规格id
	*/
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
}