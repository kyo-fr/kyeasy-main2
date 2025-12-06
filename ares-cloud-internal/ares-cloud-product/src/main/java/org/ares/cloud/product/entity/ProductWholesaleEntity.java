package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 批发商品 实体
* @version 1.0.0
* @date 2024-11-08
*/
@TableName("product_wholesale")
@EqualsAndHashCode(callSuper = false)
public class ProductWholesaleEntity extends TenantEntity {
	/**
	* 数量1
	*/
	private Integer numOne;
	/**
	* 数量2
	*/
	private Integer numTwo;
	/**
	* 单价3
	*/
	private BigDecimal priceThree;
	/**
	* 数量3
	*/
	private Integer numThree;
	/**
	* 商品id
	*/
	private String productId;
	/**
	* 单价1
	*/
	private BigDecimal priceOne;
	/**
	* 单价2
	*/
	private BigDecimal priceTwo;
	/**
	* 获取数量1
	* @return 数量1
	*/
	public Integer getNumOne() {
		return numOne;
	}

	/**
	* 设置数量1
	* @param numOne 数量1
	*/
	public void setNumOne(Integer numOne) {
		this.numOne = numOne;
	}
	/**
	* 获取数量2
	* @return 数量2
	*/
	public Integer getNumTwo() {
		return numTwo;
	}

	/**
	* 设置数量2
	* @param numTwo 数量2
	*/
	public void setNumTwo(Integer numTwo) {
		this.numTwo = numTwo;
	}
	/**
	* 获取单价3
	* @return 单价3
	*/
	public BigDecimal getPriceThree() {
		return priceThree;
	}

	/**
	* 设置单价3
	* @param priceThree 单价3
	*/
	public void setPriceThree(BigDecimal priceThree) {
		this.priceThree = priceThree;
	}
	/**
	* 获取数量3
	* @return 数量3
	*/
	public Integer getNumThree() {
		return numThree;
	}

	/**
	* 设置数量3
	* @param numThree 数量3
	*/
	public void setNumThree(Integer numThree) {
		this.numThree = numThree;
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
	/**
	* 获取单价1
	* @return 单价1
	*/
	public BigDecimal getPriceOne() {
		return priceOne;
	}

	/**
	* 设置单价1
	* @param priceOne 单价1
	*/
	public void setPriceOne(BigDecimal priceOne) {
		this.priceOne = priceOne;
	}
	/**
	* 获取单价2
	* @return 单价2
	*/
	public BigDecimal getPriceTwo() {
		return priceTwo;
	}

	/**
	* 设置单价2
	* @param priceTwo 单价2
	*/
	public void setPriceTwo(BigDecimal priceTwo) {
		this.priceTwo = priceTwo;
	}
}