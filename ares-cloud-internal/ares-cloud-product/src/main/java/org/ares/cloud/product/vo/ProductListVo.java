package org.ares.cloud.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;
import org.ares.cloud.common.serializer.CustomMoneySerializer;
import org.ares.cloud.database.entity.BaseEntity;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商品清单 数据模型
* @version 1.0.0
* @date 2025-04-03
*/
@Schema(description = "商品清单")
public class ProductListVo  {
	private static final long serialVersionUID = 1L;


	@Schema(description = "商品清单id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "subProductId")
	private String subProductId;

	@Schema(description = "商品名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "subProductName")
	private String subProductName;

	@Schema(description = "商品清单图片",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "pictureUrl")
	private String pictureUrl;

	@Schema(description = "商品价格",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "price")
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal price;


	public String getSubProductId() {
		return subProductId;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}