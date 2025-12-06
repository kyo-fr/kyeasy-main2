package org.ares.cloud.api.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;

/**
* @author hugo tangxkwork@163.com
* @description 商品关键字 数据模型
* @version 1.0.0
* @date 2025-03-18
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品关键字")
public class ProductKeywordsDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "商品id`",hidden = true,requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "productId")
//	@NotBlank(message = "{validation.product.productKeywords.productId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String productId;

	@Schema(description = "关键字id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "keyWordsId")
	@NotBlank(message = "{validation.product.productKeywords.keyWordsId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String keyWordsId;

	@Schema(description = "关键字名称")
	@JsonProperty(value = "keyWordsName")
//	@NotBlank(message = "{validation.product.productKeywords.keyWordsName.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String keyWordsName;

	/**
	* 获取商品id`
	* @return 商品id`
	*/
	public String getProductId() {
	return productId;
	}

	/**
	* 设置商品id`
	* @param productId 商品id`
	*/
	public void setProductId(String productId) {
	this.productId = productId;
	}
	/**
	* 获取关键字id
	* @return 关键字id
	*/
	public String getKeyWordsId() {
	return keyWordsId;
	}

	/**
	* 设置关键字id
	* @param keyWordsId 关键字id
	*/
	public void setKeyWordsId(String keyWordsId) {
	this.keyWordsId = keyWordsId;
	}

	public String getKeyWordsName() {
	return keyWordsName;
	}

	public void setKeyWordsName(String keyWordsName) {
	this.keyWordsName = keyWordsName;
	}

}