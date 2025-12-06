package org.ares.cloud.rider.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 骑手 数据模型
* @version 1.0.0
* @date 2025-08-26
*/
@Schema(description = "新增骑手")
public class CreateRiderDto  {


	@Schema(description = "邮箱")
	@JsonProperty(value = "email")
	@Size(max = 50, message = "validation.size.max")
	private String email;

	@Schema(description = "国家代码", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "countryCode")
	@Size(max = 10, message = "validation.size.max")
	private String countryCode;

	@Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "phone")
	@Size(max = 50, message = "validation.size.max")
	private String phone;

	@Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@Size(max = 50, message = "validation.size.max")
	private String name;

	@Schema(description = "登录密码")
	@JsonProperty(value = "password")
	@Size(min=8,max = 16,message = "{validation.password}")
	private String password;

	@Schema(description = "商户id")
	@JsonProperty(value = "merchantId")
	private String merchantId;
	/**
	* 获取邮箱
	* @return 邮箱
	*/
	public String getEmail() {
	return email;
	}

	/**
	* 设置邮箱
	* @param email 邮箱
	*/
	public void setEmail(String email) {
	this.email = email;
	}
	/**
	* 获取国家代码
	* @return 国家代码
	*/
	public String getCountryCode() {
	return countryCode;
	}

	/**
	* 设置国家代码
	* @param countryCode 国家代码
	*/
	public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
	}
	/**
	* 获取手机号
	* @return 手机号
	*/
	public String getPhone() {
	return phone;
	}

	/**
	* 设置手机号
	* @param phone 手机号
	*/
	public void setPhone(String phone) {
	this.phone = phone;
	}
	/**
	* 获取名称
	* @return 名称
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置名称
	* @param name 名称
	*/
	public void setName(String name) {
	this.name = name;
	}
	/**
	* 获取登录密码
	* @return 登录密码
	*/
	public String getPassword() {
		return password;
	}
	/**
	* 设置登录密码
	* @param password 登录密码
	*/
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	* 获取商户id
	* @return 商户id
	*/
	public String getMerchantId() {
		return merchantId;
	}
	/**
	* 获取商户id
	* @param merchantId 商户id
	*/

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}