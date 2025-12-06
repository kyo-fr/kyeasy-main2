package com.ares.cloud.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 支付渠道 实体
* @version 1.0.0
* @date 2025-05-13
*/
@Data
@TableName("sys_payment_channel")
@EqualsAndHashCode(callSuper = false)
public class SysPaymentChannelEntity extends BaseEntity {
	/**
	* Logo
	*/
	private String logo;
	/**
	* 状态 0:启用；1:关闭
	*/
	private Integer status;
	/**
	* 渠道唯一key
	*/
	private String channelKey;
	/**
	* 支付渠道;1:线上；2:线下
	*/
	private Integer channelType;
	/**
	* 支付渠道名
	*/
	private String channelName;
	/**
	* 支付商家针对线上支付(braintree、alipay、wechat)
	*/
	private String paymentMerchant;
}