package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商户广告 数据模型
* @version 1.0.0
* @date 2025-01-03
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户广告")
public class MerchantAdvertisedDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "广告描述")
	@JsonProperty(value = "contents")
	@NotBlank(message = "{validation.merchant.merchantAdvertised.contents.notBlank}")
	@Size(max = 3000, message = "validation.size.max")
	private String contents;
	/**
	* 获取广告描述
	* @return 广告描述
	*/
	public String getContents() {
	return contents;
	}

	/**
	* 设置广告描述
	* @param contents 广告描述
	*/
	public void setContents(String contents) {
	this.contents = contents;
	}
}