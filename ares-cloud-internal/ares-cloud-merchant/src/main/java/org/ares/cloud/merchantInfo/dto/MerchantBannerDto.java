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
* @description 轮播图 数据模型
* @version 1.0.0
* @date 2025-03-18
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "轮播图")
public class MerchantBannerDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "图片链接",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "picUrl")
	@NotBlank(message = "{validation.merchant.banner.picUrl.notBlank}")
	@Size(max = 10000, message = "validation.size.max")
	private String picUrl;

	@Schema(description = "图片描述")
	@JsonProperty(value = "picDesc")
	@Size(max = 128, message = "validation.size.max")
	private String picDesc;

	@Schema(description = "跳转链接")
	@JsonProperty(value = "jumpUrl")
	@Size(max = 255, message = "validation.size.max")
	private String jumpUrl;
	/**
	* 获取图片链接
	* @return 图片链接
	*/
	public String getPicUrl() {
	return picUrl;
	}

	/**
	* 设置图片链接
	* @param picUrl 图片链接
	*/
	public void setPicUrl(String picUrl) {
	this.picUrl = picUrl;
	}
	/**
	* 获取图片描述
	* @return 图片描述
	*/
	public String getPicDesc() {
	return picDesc;
	}

	/**
	* 设置图片描述
	* @param picDesc 图片描述
	*/
	public void setPicDesc(String picDesc) {
	this.picDesc = picDesc;
	}
	/**
	* 获取跳转链接
	* @return 跳转链接
	*/
	public String getJumpUrl() {
	return jumpUrl;
	}

	/**
	* 设置跳转链接
	* @param jumpUrl 跳转链接
	*/
	public void setJumpUrl(String jumpUrl) {
	this.jumpUrl = jumpUrl;
	}
}