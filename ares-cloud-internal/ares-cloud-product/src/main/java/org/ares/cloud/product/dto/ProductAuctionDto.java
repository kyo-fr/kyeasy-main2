package org.ares.cloud.product.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品 数据模型
* @version 1.0.0
* @date 2024-11-08
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "拍卖商品")
public class ProductAuctionDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "每次加价",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "fares")
	@NotBlank(message = "{validation.product.productAuction.fares.notBlank}")
	private BigDecimal fares;

	@Schema(description = "一口价")
	@JsonProperty(value = "fixedPrice")
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal fixedPrice;

	@Schema(description = "开始时间")
	@JsonProperty(value = "startTime")
	//@Size(max = 20, message = "validation.size.max")
	private Long startTime;

	@Schema(description = "结束时间")
	@JsonProperty(value = "endTime")
	//@Size(max = 20, message = "validation.size.max")
	private Long endTime;

	@Schema(description = "商品id")
	@JsonProperty(value = "productId")
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
	* @return 结束时间
	*/
	public Long getEndTime() {
	return endTime;
	}

	/**
	* 设置结束时间
	* @param endTime 结束时间
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