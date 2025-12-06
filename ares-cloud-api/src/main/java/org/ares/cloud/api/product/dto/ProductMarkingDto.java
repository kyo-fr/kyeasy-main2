package org.ares.cloud.api.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;

/**
* @author hugo tangxkwork@163.com
* @description 商品标注 数据模型
* @version 1.0.0
* @date 2024-11-08
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品标注")
public class ProductMarkingDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "商品id",hidden = true,requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "productId")
	@NotBlank(message = "{validation.product.marking.productId.notBlank}")
	private String productId;

	@Schema(description = "标注id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "markingId")
	@NotBlank(message = "{validation.product.marking.markingId.notBlank}")
	private String markingId;

	@Schema(description = "标注名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "markingName")
	private String markingName;
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
	* 获取标注id
	* @return 标注id
	*/
	public String getMarkingId() {
	return markingId;
	}

	/**
	* 设置标注id
	* @param markingId 标注id
	*/
	public void setMarkingId(String markingId) {
	this.markingId = markingId;
	}

	public String getMarkingName() {
		return markingName;
	}

	public void setMarkingName(String markingName) {
		this.markingName = markingName;
	}
}