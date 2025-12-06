package org.ares.cloud.merchantInfo.dto;

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
* @description 硬件管理 数据模型
* @version 1.0.0
* @date 2024-10-12
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "硬件管理")
public class HardWareDto  extends BaseDto  {
	private static final long serialVersionUID = 1L;


	@Schema(description = "硬件id",hidden = true)
	@JsonProperty(value = "hardwareId")
	@Size(max = 32, message = "validation.size.max")
	private String hardwareId;

	@Schema(description = "类型id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "typeId")
	private Integer typeId;


	@Schema(description = "类型名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.hardWare.name.notBlank}")
	@Size(max = 128, message = "validation.size.max")
	private String name;

	@Schema(description = "状态(1:可用；2不可用)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "status")
	private Integer status;

	/**
	* 获取硬件id
	* @return 硬件id
	*/
	public String getHardwareId() {
	return hardwareId;
	}

	/**
	* 设置硬件id
	* @param hardwareId 硬件id
	*/
	public void setHardwareId(String hardwareId) {
	this.hardwareId = hardwareId;
	}
	/**
	* 获取类型id
	* @return 类型id
	*/
	public Integer getTypeId() {
	return typeId;
	}

	/**
	* 设置类型id
	* @param typeId 类型id
	*/
	public void setTypeId(Integer typeId) {
	this.typeId = typeId;
	}

	/**
	* 获取类型名称
	* @return 类型名称
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置类型名称
	* @param name 类型名称
	*/
	public void setName(String name) {
	this.name = name;
	}
	/**
	* 获取状态(1:可用；2不可用)
	* @return 状态(1:可用；2不可用)
	*/
	public Integer getStatus() {
	return status;
	}

	/**
	* 设置状态(1:可用；2不可用)
	* @param status 状态(1:可用；2不可用)
	*/
	public void setStatus(Integer status) {
	this.status = status;
	}
}