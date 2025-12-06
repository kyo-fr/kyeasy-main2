package org.ares.cloud.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 骑手 数据模型
* @version 1.0.0
* @date 2025-08-26
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "骑手")
public class RiderDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "邮箱")
	@JsonProperty(value = "email")
	@Size(max = 50, message = "validation.size.max")
	private String email;

	@Schema(description = "国家代码")
	@JsonProperty(value = "countryCode")
	@Size(max = 10, message = "validation.size.max")
	private String countryCode;

	@Schema(description = "手机号")
	@JsonProperty(value = "phone")
	@Size(max = 50, message = "validation.size.max")
	private String phone;

	@Schema(description = "名称")
	@JsonProperty(value = "name")
	@Size(max = 50, message = "validation.size.max")
	private String name;

	@Schema(description = "状态(1:正常,2:停用)")
	@JsonProperty(value = "status")
	private Integer status;
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
	* 获取状态(1:正常,2:停用)
	* @return 状态(1:正常,2:停用)
	*/
	public Integer getStatus() {
	return status;
	}

	/**
	* 设置状态(1:正常,2:停用)
	* @param status 状态(1:正常,2:停用)
	*/
	public void setStatus(Integer status) {
	this.status = status;
	}
}