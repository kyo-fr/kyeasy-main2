package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 平台信息 数据模型
* @version 1.0.0
* @date 2024-10-15
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "平台信息")
public class PlatformInfoDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "平台名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "platformName")
	@NotBlank(message = "{validation.platformInfo.platformName.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String platformName;

	@Schema(description = "平台联系电话",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "platformPhone")
	@NotBlank(message = "{validation.platformInfo.platformPhone.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String platformPhone;

	@Schema(description = "税务号",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "taxNum")
	@NotBlank(message = "{validation.platformInfo.taxNum.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String taxNum;

	@Schema(description = "平台地址",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "address")
	@NotBlank(message = "{validation.platformInfo.address.notBlank}")
	@Size(max = 128, message = "validation.size.max")
	private String address;

	@Schema(description = "平台邮箱",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "email")
	@NotBlank(message = "{validation.platformInfo.email.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String email;

	@Schema(description = "后台语言",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "language")
	@NotBlank(message = "{validation.platformInfo.language.notBlank}")
	@Size(max = 16, message = "validation.size.max")
	private String language;

	@Schema(description = "国家",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "country")
	@NotBlank(message = "{validation.platformInfo.country.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String country;

	@Schema(description = "注册手机号",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "mobile")
	@NotBlank(message = "{validation.platformInfo.mobile.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String mobile;

	/**
	 * 收款货币
	 */
	@Schema(description = "收款货币",hidden = true)
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

	/**
	* 获取收款货币
	* @return 收款货币
	*/
	public String getCurrency() {
		return currency;
	}

	/**
	* 设置收款货币
	* @param currency 收款货币
	*/
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}