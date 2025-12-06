package com.ares.cloud.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道 实体
* @version 1.0.0
* @date 2025-05-13
*/
@TableName("merchant_pay_channel")
@EqualsAndHashCode(callSuper = false)
public class MerchantPaymentChannelEntity extends TenantEntity {
	/**
	* 支付渠道;1:线上；2:线下
	*/
	private Integer channelType;
	/**
	* 渠道唯一key
	*/
	private String channelKey;
	/**
	* 商户id
	*/
	private String merchantId;
	/**
	* 状态 1:启用；2:关闭
	*/
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