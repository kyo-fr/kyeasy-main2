package org.ares.cloud.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品竞价记录 实体
* @version 1.0.0
* @date 2025-09-23
*/
@TableName("product_auction_price_log")
@EqualsAndHashCode(callSuper = false)
@Data
public class ProductAuctionPriceLogEntity extends BaseEntity {
	/**
	* 用户id
	*/
	private String userId;

	/**
	 * 租户(商户id)
	 */
	private String tenantId;

	/**
	* 竞拍价格
	*/
	private BigDecimal price;
	/**
	* 商品id
	*/
	private String productId;
	/**
	* 获取用户id
	* @return 用户id
	*/
	public String getUserId() {
		return userId;
	}

	/**
	* 设置用户id
	* @param userId 用户id
	*/
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	* 获取竞拍价格
	* @return 竞拍价格
	*/
	public BigDecimal getPrice() {
		return price;
	}

	/**
	* 设置竞拍价格
	* @param price 竞拍价格
	*/
	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}