package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商品关键字 实体
* @version 1.0.0
* @date 2025-03-18
*/
@TableName("product_keywords")
@EqualsAndHashCode(callSuper = false)
public class ProductKeywordsEntity extends BaseEntity {
	/**
	* 商品id`
	*/
	private String productId;
	/**
	* 关键字id
	*/
	private String keyWordsId;
	/**
	* 获取商品id`
	* @return 商品id`
	*/
	public String getProductId() {
		return productId;
	}

	/**
	* 设置商品id`
	* @param productId 商品id`
	*/
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	* 获取关键字id
	* @return 关键字id
	*/
	public String getKeyWordsId() {
		return keyWordsId;
	}

	/**
	* 设置关键字id
	* @param keyWordsId 关键字id
	*/
	public void setKeyWordsId(String keyWordsId) {
		this.keyWordsId = keyWordsId;
	}
}