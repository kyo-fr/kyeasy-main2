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
* @description 平台设置语言 数据模型
* @version 1.0.0
* @date 2024-10-15
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "平台设置语言")
public class PlatformLanguageDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "语言名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.platformInfo.language.languageName.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String name;

	@Schema(description = "状态 1:启用；2:关闭",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "status")
	private Integer status;
	/**
	* 获取语言名称
	* @return 语言名称
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置语言名称
	* @param name 语言名称
	*/
	public void setName(String name) {
	this.name = name;
	}
	/**
	* 获取状态 1:启用；2:关闭
	* @return 状态 1:启用；2:关闭
	*/
	public Integer getStatus() {
	return status;
	}

	/**
	* 设置状态 1:启用；2:关闭
	* @param status 状态 1:启用；2:关闭
	*/
	public void setStatus(Integer status) {
	this.status = status;
	}
}