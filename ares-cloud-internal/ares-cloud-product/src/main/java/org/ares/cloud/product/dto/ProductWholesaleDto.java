package org.ares.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 批发商品 数据模型
* @version 1.0.0
* @date 2024-11-08
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "批发商品")
public class ProductWholesaleDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "数量1")
	@JsonProperty(value = "numOne")
	private Integer numOne;

	@Schema(description = "数量2")
	@JsonProperty(value = "numTwo")
	private Integer numTwo;

	@Schema(description = "单价3")
	@JsonProperty(value = "priceThree")
	private BigDecimal priceThree;

	@Schema(description = "数量3")
	@JsonProperty(value = "numThree")
	private Integer numThree;

	@Schema(description = "商品id")
	@JsonProperty(value = "productId")
	//@NotBlank(message = "{validation.product.ProductWholesale.productId.productId.notBlank}")
	//@Size(max = 32, message = "validation.size.max")
	private String productId;

	@Schema(description = "单价1")
	@JsonProperty(value = "priceOne")
	private BigDecimal priceOne;

	@Schema(description = "单价2")
	@JsonProperty(value = "priceTwo")
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