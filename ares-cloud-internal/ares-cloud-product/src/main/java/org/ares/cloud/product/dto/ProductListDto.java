package org.ares.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商品清单 数据模型
* @version 1.0.0
* @date 2025-04-03
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品清单")
public class ProductListDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "商品清单id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "subProductId")
	@NotBlank(message = "{validation.product.productList.subProductId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String subProductId;

	@Schema(description = "商品id")
	@JsonProperty(value = "productId")
//	@NotBlank(message = "{validation.product.productList.productId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String productId;
	/**
	* 获取商品清单id
	* @return 商品清单id
	*/
	public String getSubProductId() {
	return subProductId;
	}

	/**
	* 设置商品清单id
	* @param subProductId 商品清单id
	*/
	public void setSubProductId(String subProductId) {
	this.subProductId = subProductId;
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