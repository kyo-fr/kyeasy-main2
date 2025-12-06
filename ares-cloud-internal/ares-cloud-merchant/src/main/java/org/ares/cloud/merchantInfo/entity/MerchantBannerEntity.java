package org.ares.cloud.merchantInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 轮播图 实体
* @version 1.0.0
* @date 2025-03-18
*/
@TableName("merchant_banner")
@EqualsAndHashCode(callSuper = false)
public class MerchantBannerEntity extends TenantEntity {
	/**
	* 图片链接
	*/
	//@TableField(fill = FieldFill.INSERT_UPDATE)
	private String picUrl;
	/**
	* 图片描述
	*/
	private String picDesc;
	/**
	* 跳转链接
	*/
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