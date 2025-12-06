package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商户信息 数据模型
* @version 1.0.0
* @date 2024-10-08
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户信息")
public class MerchantInfoDto extends BaseDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "userId")
	@NotBlank(message = "{validation.merchantInfo.merchantType.userId.notBlank}")
	private String userId;

	@Schema(description = "商户注册手机号",requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "{validation.merchantInfo.merchantRegisterPhone.notBlank}")
	@JsonProperty(value = "registerPhone")
	private String registerPhone;


	@Schema(description = "企业名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "{validation.merchantInfo.merchantName.notBlank} ")
	@JsonProperty(value = "name")
	private String name;

	@Schema(description = "企业域名")
	@JsonProperty(value = "domainName")
	private String domainName;


	@Schema(description = "税务号(企业编号)")
	@JsonProperty(value = "taxNum")
	@NotBlank(message = "{validation.merchantInfo.taxNum.notBlank} ")
	private String taxNum;

	@Schema(description = "商户合同id",hidden = true)
	@JsonProperty(value = "contractId")
	private String contractId;


	@Schema(description = "企业电话",requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "{validation.merchantInfo.merchantPhone.notBlank}")
	@JsonProperty(value = "phone")
	private String phone;


	@Schema(description = "企业邮箱")
	@JsonProperty(value = "enterpriseEmail")
	@NotBlank(message = "{validation.merchantInfo.enterpriseEmail.notBlank}")
	private String enterpriseEmail;


	@Schema(description = "企业iBan")
	@JsonProperty(value = "iBan")
	private String iBan;

	@Schema(description = "企业BIC")
	@JsonProperty(value = "bic")
	private String bic;

	@Schema(description = "商户所在国家：示例值： +86")
	@JsonProperty(value = "countryCode")
	@NotBlank(message = "{validation.merchantInfo.countryCode.notBlank}")
	private String countryCode;

	@Schema(description = "网站语言")
	@JsonProperty(value = "language")
	@NotBlank(message = "{validation.merchantInfo.language.notBlank}")
	private String language;


	@Schema(description = "收款货币")
	@JsonProperty(value = "currency")
	@NotBlank(message = "{validation.merchantInfo.currency.notBlank}")
	private String currency;

	@Schema(description = "首页展示 1：正常商品 ；2：拍卖商品")
	@JsonProperty(value = "pageDisplay")
	private Integer pageDisplay;

	@Schema(description = "是否开启礼物点 1:是 ; 2:否",hidden = true)
	@JsonProperty(value = "isOpenGift")
	private int isOpenGift;


	@Schema(description = "企业地址",requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "{validation.merchantInfo.merchantAddress.notBlank}")
	@JsonProperty(value = "address")
	private String address;

	@Schema(description = "商户状态 1-注册中；2-正常；3-冻结",defaultValue = "1")
	@JsonProperty(value = "status")
	private Integer status;

	@Schema(description = "总存储量",hidden = true,defaultValue = "0")
	@JsonProperty(value = "sumMemory")
	private BigDecimal sumMemory;

	@Schema(description = "已使用存储量",hidden = true,defaultValue = "0")
	@JsonProperty(value = "usedMemory")
	private BigDecimal usedMemory;


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
	 * 存储过期时间
	 */
	@Schema(description = "存储过期时间",hidden = true)
	private Long overdueDate;


	/**
	 * 是否已维护商户信息 调用商户信息接口更新此字段为已维护
	 * @return
	 */
	@Schema(description = "是否已维护商户信息Y代表已维护商户信息；N代表没有维护")
	public String isUpdated="N";


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

	public BigDecimal getSumMemory() {
		return sumMemory;
	}

	public BigDecimal getUsedMemory() {
		return usedMemory;
	}

	public void setSumMemory(BigDecimal sumMemory) {
		this.sumMemory = sumMemory;
	}

	public void setUsedMemory(BigDecimal usedMemory) {
		this.usedMemory = usedMemory;
	}

	public Long getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Long overdueDate) {
		this.overdueDate = overdueDate;
	}

	public String getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(String isUpdated) {
		this.isUpdated = isUpdated;
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