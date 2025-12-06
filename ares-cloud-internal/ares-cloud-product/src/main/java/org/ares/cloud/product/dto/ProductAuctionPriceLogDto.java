package org.ares.cloud.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品竞价记录 数据模型
* @version 1.0.0
* @date 2025-09-23
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "拍卖商品竞价记录")
@Data
public class ProductAuctionPriceLogDto extends BaseDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "租户")
	@JsonProperty(value = "tenantId")
	@Size(max = 32, message = "validation.size.max")
	private String tenantId;

	@Schema(description = "用户id")
	@JsonProperty(value = "userId")
	@Size(max = 32, message = "validation.size.max")
	private String userId;

	@Schema(description = "每次竞拍价格",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "price")
	@NotNull(message = "{validation.product.productAuctionPriceLog.price.notBlank}")
	private BigDecimal price;

	@Schema(description = "商品id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "productId")
	@NotBlank(message = "{validation.product.productAuctionPriceLog.productId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
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