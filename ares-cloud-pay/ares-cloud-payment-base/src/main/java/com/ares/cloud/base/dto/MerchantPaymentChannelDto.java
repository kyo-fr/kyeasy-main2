package com.ares.cloud.base.dto;

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
* @description 商户支付渠道 数据模型
* @version 1.0.0
* @date 2025-05-13
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户支付渠道")
public class MerchantPaymentChannelDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "渠道类型;1:线上；2:线下",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "channelType")
	private Integer channelType;

	@Schema(description = "渠道唯一key")
	@JsonProperty(value = "channelKey")
	@Size(max = 255, message = "validation.size.max")
	private String channelKey;

	@Schema(description = "商户id")
	@JsonProperty(value = "merchantId")
	@Size(max = 20, message = "validation.size.max")
	private String merchantId;

	@Schema(description = "状态 1:启用；2:关闭",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "status")
	private Integer status;
	/**
	* 获取支付渠道;1:线上；2:线下
	* @return 支付渠道;1:线上；2:线下
	*/
	public Integer getChannelType() {
	return channelType;
	}

	/**
	* 设置支付渠道;1:线上；2:线下
	* @param channelType 支付渠道;1:线上；2:线下
	*/
	public void setChannelType(Integer channelType) {
	this.channelType = channelType;
	}
	/**
	* 获取渠道唯一key
	* @return 渠道唯一key
	*/
	public String getChannelKey() {
	return channelKey;
	}

	/**
	* 设置渠道唯一key
	* @param channelKey 渠道唯一key
	*/
	public void setChannelKey(String channelKey) {
	this.channelKey = channelKey;
	}
	/**
	* 获取商户id
	* @return 商户id
	*/
	public String getMerchantId() {
	return merchantId;
	}

	/**
	* 设置商户id
	* @param merchantId 商户id
	*/
	public void setMerchantId(String merchantId) {
	this.merchantId = merchantId;
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