package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 平台支付类型 数据模型
* @version 1.0.0
* @date 2024-10-15
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "平台支付类型")
public class PlatformPayWayDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "支付渠道1:线上；2:线下",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "channelType")
	@NotNull(message = "{validation.platformInfo.payWay.channelType}")
	private Integer channelType;

	@Schema(description = "图片",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "url")
	@NotBlank(message = "{validation.platformInfo.payWay.url}")
	@Size(max = 255, message = "validation.size.max")
	private String url;

	@Schema(description = "状态 1:启用；2:关闭",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "status")
	private Integer status;

	@Schema(description = "前端验证key")
	@JsonProperty(value = "frontKey")
	@Size(max = 255, message = "validation.size.max")
	@NotBlank(message = "{validation.platformInfo.payWay.frontKey.notBlank} ")
	private String frontKey;

	@Schema(description = "后端验证key",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "backKey")
	@NotBlank(message = "{validation.platformInfo.payWay.backKey.notBlank} ")
	@Size(max = 255, message = "validation.size.max")
	private String backKey;

	//支付名称
	@Schema(description = "支付名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "payName")
	@NotBlank(message = "{validation.platformInfo.payWay.payName.notBlank} ")
	@Size(max = 255, message = "validation.size.max")
	private String payName;


	/**
	* 获取支付渠道1:线上；2:线下
	* @return 支付渠道1:线上；2:线下
	*/
	public Integer getChannelType() {
	return channelType;
	}

	/**
	* 设置支付渠道1:线上；2:线下
	* @param channelType 支付渠道1:线上；2:线下
	*/
	public void setChannelType(Integer channelType) {
	this.channelType = channelType;
	}
	/**
	* 获取图片
	* @return 图片
	*/
	public String getUrl() {
	return url;
	}

	/**
	* 设置图片
	* @param url 图片
	*/
	public void setUrl(String url) {
	this.url = url;
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
	/**
	* 获取前端验证key
	* @return 前端验证key
	*/
	public String getFrontKey() {
	return frontKey;
	}

	/**
	* 设置前端验证key
	* @param frontKey 前端验证key
	*/
	public void setFrontKey(String frontKey) {
	this.frontKey = frontKey;
	}
	/**
	* 获取后端验证key
	* @return 后端验证key
	*/
	public String getBackKey() {
	return backKey;
	}

	/**
	* 设置后端验证key
	* @param backKey 后端验证key
	*/
	public void setBackKey(String backKey) {
	this.backKey = backKey;
	}

	/**
	* 获取支付名称
	* @return 支付名称
	*/
	public String getPayName() {
	return payName;
	}

	/**
	* 设置支付名称
	* @param payName 支付名称
	*/
	public void setPayName(String payName) {
	this.payName = payName;
	}
}