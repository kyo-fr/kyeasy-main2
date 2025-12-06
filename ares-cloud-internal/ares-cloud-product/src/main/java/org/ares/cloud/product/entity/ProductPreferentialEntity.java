package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 优惠商品 实体
* @version 1.0.0
* @date 2024-11-07
*/
@TableName("product_preferential")
@EqualsAndHashCode(callSuper = false)
public class ProductPreferentialEntity extends TenantEntity {
	/**
	* 商品id
	*/
	private String productId;
	/**
	* 广告词
	*/
	private String advertisingWords;
	/**
	* 优惠开始时间
	*/
	private Long startTime;
	/**
	* 优惠结束时间
	*/
	private Long endTime;
	/**
	* 优惠价格
	*/
	private BigDecimal preferentialPrice;
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
	* 获取广告词
	* @return 广告词
	*/
	public String getAdvertisingWords() {
		return advertisingWords;
	}

	/**
	* 设置广告词
	* @param advertisingWords 广告词
	*/
	public void setAdvertisingWords(String advertisingWords) {
		this.advertisingWords = advertisingWords;
	}
	/**
	* 获取优惠开始时间
	* @return 优惠开始时间
	*/
	public Long getStartTime() {
		return startTime;
	}

	/**
	* 设置优惠开始时间
	* @param startTime 优惠开始时间
	*/
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	/**
	* 获取优惠结束时间
	* @return 优惠结束时间
	*/
	public Long getEndTime() {
		return endTime;
	}

	/**
	* 设置优惠结束时间
	* @param endTime 优惠结束时间
	*/
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	/**
	* 获取优惠价格
	* @return 优惠价格
	*/
	public BigDecimal getPreferentialPrice() {
		return preferentialPrice;
	}

	/**
	* 设置优惠价格
	* @param preferentialPrice 优惠价格
	*/
	public void setPreferentialPrice(BigDecimal preferentialPrice) {
		this.preferentialPrice = preferentialPrice;
	}
}