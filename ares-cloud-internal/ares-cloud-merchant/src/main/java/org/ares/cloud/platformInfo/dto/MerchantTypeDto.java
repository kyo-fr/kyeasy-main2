package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商户类型 数据模型
* @version 1.0.0
* @date 2024-10-15
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户类型")
public class MerchantTypeDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "类型名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "type")
	@NotBlank(message = "{validation.platformInfo.merchantType.notBlank}")
	@Size(max = 16, message = "validation.size.max")
	private String type;

	/**
	* 获取类型名称
	* @return 类型名称
	*/
	public String getType() {
	return type;
	}

	/**
	* 设置类型名称
	* @param type 类型名称
	*/
	public void setType(String type) {
	this.type = type;
	}
}