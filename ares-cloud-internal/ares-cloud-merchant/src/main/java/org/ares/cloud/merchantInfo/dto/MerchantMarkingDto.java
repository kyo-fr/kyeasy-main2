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
* @description 标注主数据 数据模型
* @version 1.0.0
* @date 2025-03-19
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "标注主数据")
public class MerchantMarkingDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "标注名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.merchant.marking.name.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String name;
	/**
	* 获取标注名称
	* @return 标注名称
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置标注名称
	* @param markingName 标注名称
	*/
	public void setName(String name) {
	this.name = name;
	}
}