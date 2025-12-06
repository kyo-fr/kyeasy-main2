package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商品清单 实体
* @version 1.0.0
* @date 2025-04-03
*/
@TableName("product_list")
@EqualsAndHashCode(callSuper = false)
public class ProductListEntity extends BaseEntity {
	/**
	* 商品清单id
	*/
	private String subProductId;
	/**
	* 商品id
	*/
	private String productId;
	/**
	* 获取商品清单id
	* @return 商品清单id
	*/
	public String getSubProductId() {
		return subProductId;
	}

	/**
	* 设置商品清单id
	* @param subProductId 商品清单id
	*/
	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}
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
}