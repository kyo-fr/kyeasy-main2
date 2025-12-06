package org.ares.cloud.merchantInfo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
* @author hugo tangxkwork@163.com
* @description 商户信息 数据模型
* @version 1.0.0
* @date 2024-10-08
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户信息")
public class MerchantInfoVo extends BaseDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@JsonProperty(value = "userId")
	private String userId;

	@Schema(description = "用户注册手机号")
	@JsonProperty(value = "phone")
	private String phone;

	@Schema(description = "用户名称")
	@JsonProperty(value = "userName")
	private String userName;


	@Schema(description = "用户邮箱")
	@JsonProperty(value = "userEmail")
	private String userEmail;

	@Schema(description = "商户logo")
	@JsonProperty(value = "logo")
	private String logo;

	@Schema(description = "企业电话",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "registerPhone")
	private String registerPhone;


	@Schema(description = "企业名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	private String name;

	@Schema(description = "企业域名")
	@JsonProperty(value = "domainName")
	private String domainName;


	@Schema(description = "税务号(企业编号)")
	@JsonProperty(value = "taxNum")
	private String taxNum;

	@Schema(description = "商户合同id",hidden = true)
	@JsonProperty(value = "contractId")
	private String contractId;

	@Schema(description = "企业邮箱")
	@JsonProperty(value = "enterpriseEmail")
	private String enterpriseEmail;


	@Schema(description = "企业iBan")
	@JsonProperty(value = "iBan")
	private String iBan;

	@Schema(description = "企业BIC")
	@JsonProperty(value = "bic")
	private String bic;

	@Schema(description = "商户所在国家：示例值： +86")
	@JsonProperty(value = "countryCode")
	private String countryCode;

	@Schema(description = "网站语言")
	@JsonProperty(value = "language")
	private String language;


	@Schema(description = "收款货币")
	@JsonProperty(value = "currency")
	private String currency;

	@Schema(description = "首页展示 1：正常商品 ；2：拍卖商品")
	@JsonProperty(value = "pageDisplay")
	private Integer pageDisplay;

	@Schema(description = "是否开启礼物点 1:是 ; 2:否")
	@JsonProperty(value = "isOpenGift")
	private int isOpenGift;


	@Schema(description = "企业地址",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "address")
	private String address;

	private Integer status;



	@Schema(description = "总存储量,单位G",defaultValue = "0")
	@JsonProperty(value = "sumMemory")
	private BigDecimal sumMemory;

	@Schema(description = "已使用存储量,单位G",defaultValue = "0")
	@JsonProperty(value = "usedMemory")
	private BigDecimal usedMemory;


	@Schema(description = "上次购买存储金额", defaultValue = "0")
	@JsonProperty(value = "lastPurchaseAmount")
	private BigDecimal lastPurchaseAmount;

	@Schema(description = "企业siren")
	@JsonProperty(value = "siren")
	private String siren;

	@Schema(description = "code naf")
	@JsonProperty(value = "naf")
	private String naf;

	@Schema(description = "国家(地址)")
	@JsonProperty(value = "country")
	private String country;

	@Schema(description = "城市")
	@JsonProperty(value = "city")
	private String city;

	@Schema(description = "邮编")
	@JsonProperty(value = "postalCode")
	private String postalCode;

	/**
	 * 是否已维护商户信息 调用商户信息接口更新此字段为已维护
	 * @return
	 */
	@Schema(description = "是否已维护商户信息Y代表已维护商户信息；N代表没有维护")
	public String isUpdated;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	* 获取商户税务号
	* @return 商户税务号
	*/
	public String getTaxNum() {
	return taxNum;
	}

	/**
	* 设置商户税务号
	* @param taxNum 商户税务号
	*/
	public void setTaxNum(String taxNum) {
	this.taxNum = taxNum;
	}

	/**
	* 获取商户所在国家
	* @return 商户所在国家
	*/
	public String getCountryCode() {
	return countryCode;
	}

	/**
	* 设置商户所在国家
	* @param countryCode 商户所在国家
	*/
	public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
	}
	/**
	* 获取网站语言
	* @return 网站语言
	*/
	public String getLanguage() {
	return language;
	}

	/**
	* 设置网站语言
	* @param language 网站语言
	*/
	public void setLanguage(String language) {
	this.language = language;
	}

	/**
	* 获取商户注册手机号
	* @return 商户注册手机号
	*/
	public String getRegisterPhone() {
	return registerPhone;
	}

	/**
	* 设置商户注册手机号
	* @param registerPhone 商户注册手机号
	*/
	public void setRegisterPhone(String registerPhone) {
	this.registerPhone = registerPhone;
	}
	/**
	* 获取商户名称
	* @return 商户名称
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置商户名称
	* @param name 商户名称
	*/
	public void setName(String name) {
	this.name = name;
	}
	/**
	* 获取商户域名
	* @return 商户域名
	*/
	public String getDomainName() {
	return domainName;
	}

	/**
	* 设置商户域名
	* @param domainName 商户域名
	*/
	public void setDomainName(String domainName) {
	this.domainName = domainName;
	}
	/**
	* 获取联系电话
	* @return 联系电话
	*/
	public String getPhone() {
	return phone;
	}

	/**
	* 设置联系电话
	* @param phone 联系电话
	*/
	public void setPhone(String phone) {
	this.phone = phone;
	}

	/**
	* 获取商户地址
	* @return 商户地址
	*/
	public String getAddress() {
	return address;
	}

	/**
	* 设置商户地址
	* @param address 商户地址
	*/
	public void setAddress(String address) {
	this.address = address;
	}

	/**
	 * 用户id
	 * @return 用户id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 用户id
	 * @param userId 用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEnterpriseEmail() {
		return enterpriseEmail;
	}


	public String getBic() {
		return bic;
	}

	public String getCurrency() {
		return currency;
	}

	public Integer getPageDisplay() {
		return pageDisplay;
	}

	public void setEnterpriseEmail(String enterpriseEmail) {
		this.enterpriseEmail = enterpriseEmail;
	}


	public String getiBan() {
		return iBan;
	}

	public void setiBan(String iBan) {
		this.iBan = iBan;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setPageDisplay(Integer pageDisplay) {
		this.pageDisplay = pageDisplay;
	}


	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public int getIsOpenGift() {
		return isOpenGift;
	}

	public void setIsOpenGift(int isOpenGift) {
		this.isOpenGift = isOpenGift;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getIsUpdated() {
		return isUpdated;
	}
	public void setIsUpdated(String isUpdated) {
		this.isUpdated = isUpdated;
	}

	public BigDecimal getSumMemory() {
		return sumMemory;
	}
	public void setSumMemory(BigDecimal sumMemory) {
		this.sumMemory = sumMemory;
	}
	public BigDecimal getUsedMemory() {
		return usedMemory;
	}
	public void setUsedMemory(BigDecimal usedMemory) {
		this.usedMemory = usedMemory;
	}

	public BigDecimal getLastPurchaseAmount() {
		return lastPurchaseAmount;
	}

	public void setLastPurchaseAmount(BigDecimal lastPurchaseAmount) {
		this.lastPurchaseAmount = lastPurchaseAmount;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSiren() {
		return siren;
	}

	public void setSiren(String siren) {
		this.siren = siren;
	}

	public String getNaf() {
		return naf;
	}

	public void setNaf(String naf) {
		this.naf = naf;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}