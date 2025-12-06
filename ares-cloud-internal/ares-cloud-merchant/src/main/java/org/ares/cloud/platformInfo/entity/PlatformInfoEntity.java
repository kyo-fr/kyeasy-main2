package org.ares.cloud.platformInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 平台信息 实体
* @version 1.0.0
* @date 2024-10-15
*/
@TableName("platform_info")
@EqualsAndHashCode(callSuper = false)
public class PlatformInfoEntity extends BaseEntity {
	/**
	* 平台名称
	*/
	private String platformName;
	/**
	* 平台联系电话
	*/
	private String platformPhone;
	/**
	* 税务号
	*/
	private String taxNum;
	/**
	* 平台地址
	*/
	private String address;
	/**
	* 平台邮箱
	*/
	private String email;
	/**
	* 后台语言
	*/
	private String language;
	/**
	* 国家
	*/
	private String country;
	/**
	* 注册手机号
	*/
	private String mobile;
	/**
	 * 收款货币
	 */
	private String currency;
	/**
	* 获取平台名称
	* @return 平台名称
	*/
	public String getPlatformName() {
		return platformName;
	}

	/**
	* 设置平台名称
	* @param platformName 平台名称
	*/
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	/**
	* 获取平台联系电话
	* @return 平台联系电话
	*/
	public String getPlatformPhone() {
		return platformPhone;
	}

	/**
	* 设置平台联系电话
	* @param platformPhone 平台联系电话
	*/
	public void setPlatformPhone(String platformPhone) {
		this.platformPhone = platformPhone;
	}
	/**
	* 获取税务号
	* @return 税务号
	*/
	public String getTaxNum() {
		return taxNum;
	}

	/**
	* 设置税务号
	* @param taxNum 税务号
	*/
	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}
	/**
	* 获取平台地址
	* @return 平台地址
	*/
	public String getAddress() {
		return address;
	}

	/**
	* 设置平台地址
	* @param address 平台地址
	*/
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	* 获取平台邮箱
	* @return 平台邮箱
	*/
	public String getEmail() {
		return email;
	}

	/**
	* 设置平台邮箱
	* @param email 平台邮箱
	*/
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	* 获取后台语言
	* @return 后台语言
	*/
	public String getLanguage() {
		return language;
	}

	/**
	* 设置后台语言
	* @param language 后台语言
	*/
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	* 获取国家
	* @return 国家
	*/
	public String getCountry() {
		return country;
	}

	/**
	* 设置国家
	* @param country 国家
	*/
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	* 获取注册手机号
	* @return 注册手机号
	*/
	public String getMobile() {
		return mobile;
	}

	/**
	* 设置注册手机号
	* @param mobile 注册手机号
	*/
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}