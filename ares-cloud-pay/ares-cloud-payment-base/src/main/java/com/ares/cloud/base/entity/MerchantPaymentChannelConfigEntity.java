package com.ares.cloud.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道配置 实体
* @version 1.0.0
* @date 2025-05-13
*/
@TableName("merchant_payment_channel_config")
@EqualsAndHashCode(callSuper = false)
public class MerchantPaymentChannelConfigEntity extends TenantEntity {
	/**
	* 商户ID
	*/
	private String merchantId;
	/**
	* 支付商户(braintree、alipay、wechat)
	*/
	private String paymentMerchant;
	/**
	* 支付配置
	*/
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