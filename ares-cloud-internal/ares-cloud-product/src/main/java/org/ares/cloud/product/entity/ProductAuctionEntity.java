package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品 实体
* @version 1.0.0
* @date 2024-11-08
*/
@TableName("product_auction")
@EqualsAndHashCode(callSuper = false)
public class ProductAuctionEntity extends TenantEntity {
	/**
	* 每次加价
	*/
	private BigDecimal fares;
	/**
	* 一口价
	*/
	private BigDecimal fixedPrice;
	/**
	* 开始时间
	*/
	private Long startTime;
	/**
	* 结束时间
	*/
	private Long endTime;
	/**
	* 商品id
	*/
	private String productId;
	/**
	* 获取每次加价
	* @return 每次加价
	*/
	public BigDecimal getFares() {
		return fares;
	}

	/**
	* 设置每次加价
	* @param fares 每次加价
	*/
	public void setFares(BigDecimal fares) {
		this.fares = fares;
	}
	/**
	* 获取一口价
	* @return 一口价
	*/
	public BigDecimal getFixedPrice() {
		return fixedPrice;
	}

	/**
	* 设置一口价
	* @param fixedPrice 一口价
	*/
	public void setFixedPrice(BigDecimal fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
	/**
	* 获取开始时间
	* @return 开始时间
	*/
	public Long getStartTime() {
		return startTime;
	}

	/**
	* 设置开始时间
	* @param startTime 开始时间
	*/
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	/**
	* 获取结束时间
	* @return endTime
	*/
	public Long getEndTime() {
		return endTime;
	}

	/**
	* 设置结束时间
	* @param  endTime
	*/
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
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