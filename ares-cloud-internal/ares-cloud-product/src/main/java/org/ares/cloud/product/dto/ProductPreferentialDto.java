package org.ares.cloud.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 优惠商品 数据模型
* @version 1.0.0
* @date 2024-11-07
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "优惠商品")
public class ProductPreferentialDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "商品id")
	@JsonProperty(value = "productId")
//	@NotBlank(message = "{validation.product.productPreferential.productId.notBlank}")
//	@Size(max = 32, message = "validation.size.max")
	private String productId;

	@Schema(description = "广告词",requiredMode = Schema.RequiredMode.REQUIRED )
	@JsonProperty(value = "advertisingWords")
	@NotBlank(message = "{validation.product.productPreferential.advertisingWords.notBlank}")
	@Size(max = 1000, message = "validation.size.max")
	private String advertisingWords;

	@Schema(description = "优惠开始时间" ,requiredMode = Schema.RequiredMode.REQUIRED )
	@JsonProperty(value = "startTime")
	@NotBlank(message = "{validation.product.productPreferential.startTime.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private Long startTime;

	@Schema(description = "优惠结束时间",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "endTime")
	@NotBlank(message = "{validation.product.productPreferential.endTime.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private Long endTime;

	@Schema(description = "优惠价格",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "preferentialPrice")
	@NotBlank(message = "{validation.product.productPreferential.preferentialPrice.notBlank}")
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