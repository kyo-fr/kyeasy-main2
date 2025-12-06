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
* @description 商户支付渠道配置 数据模型
* @version 1.0.0
* @date 2025-05-13
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户支付渠道配置")
public class MerchantPaymentChannelConfigDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "商户ID")
	@JsonProperty(value = "merchantId")
	@Size(max = 32, message = "validation.size.max")
	private String merchantId;

	@Schema(description = "支付商户(braintree、alipay、wechat)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "paymentMerchant")
	@NotBlank(message = "{validation.base.paymentMerchant}")
	@Size(max = 100, message = "validation.size.max")
	private String paymentMerchant;

	@Schema(description = "支付配置")
	@JsonProperty(value = "configData")
	@Size(max = 50, message = "validation.size.max")
	private String configData;
	/**
	* 获取商户ID
	* @return 商户ID
	*/
	public String getMerchantId() {
	return merchantId;
	}

	/**
	* 设置商户ID
	* @param merchantId 商户ID
	*/
	public void setMerchantId(String merchantId) {
	this.merchantId = merchantId;
	}
	/**
	* 获取支付商户(braintree、alipay、wechat)
	* @return 支付商户(braintree、alipay、wechat)
	*/
	public String getPaymentMerchant() {
	return paymentMerchant;
	}

	/**
	* 设置支付商户(braintree、alipay、wechat)
	* @param paymentMerchant 支付商户(braintree、alipay、wechat)
	*/
	public void setPaymentMerchant(String paymentMerchant) {
	this.paymentMerchant = paymentMerchant;
	}
	/**
	* 获取支付配置
	* @return 支付配置
	*/
	public String getConfigData() {
	return configData;
	}

	/**
	* 设置支付配置
	* @param configData 支付配置
	*/
	public void setConfigData(String configData) {
	this.configData = configData;
	}
}