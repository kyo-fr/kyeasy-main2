package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商品标注 实体
* @version 1.0.0
* @date 2024-11-08
*/
@TableName("product_marking")
@EqualsAndHashCode(callSuper = false)
public class ProductMarkingEntity extends BaseEntity {
	/**
	* 商品id
	*/
	private String productId;
	/**
	* 标注id
	*/
	private String markingId;
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
	* 获取标注id
	* @return 标注id
	*/
	public String getMarkingId() {
		return markingId;
	}

	/**
	* 设置标注id
	* @param markingId 标注id
	*/
	public void setMarkingId(String markingId) {
		this.markingId = markingId;
	}
}