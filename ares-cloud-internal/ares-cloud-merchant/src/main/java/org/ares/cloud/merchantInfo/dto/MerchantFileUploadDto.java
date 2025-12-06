package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @author hugo tangxkwork@163.com
* @description 商户文件上传 数据模型
* @version 1.0.0
* @date 2024-10-09
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户文件上传")
public class MerchantFileUploadDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "营业执照")
	@JsonProperty(value = "businessLicense")
	private String businessLicense;

//	@Schema(description = "银行iban")
//	@JsonProperty(value = "bankIban")
//	private String bankIban;

//	@Schema(description = "银行bic")
//	@JsonProperty(value = "bankBic")
//	private String bankBic;

	@Schema(description = "银行rib")
	@JsonProperty(value = "bankRib")
	private String bankRib;

	@Schema(description = "商户logo")
	@JsonProperty(value = "logo")
	private String logo;

//	@Schema(description = "综合分类")
//	@JsonProperty(value = "comprehensiveClassification")
//	private String comprehensiveClassification;
	/**
	* 获取营业执照
	* @return 营业执照
	*/
	public String getBusinessLicense() {
	return businessLicense;
	}

	/**
	* 设置营业执照
	* @param businessLicense 营业执照
	*/
	public void setBusinessLicense(String businessLicense) {
	this.businessLicense = businessLicense;
	}


	/**
	* 获取银行rib
	* @return 银行rib
	*/
	public String getBankRib() {
	return bankRib;
	}

	/**
	* 设置银行rib
	* @param bankRib 银行rib
	*/
	public void setBankRib(String bankRib) {
	this.bankRib = bankRib;
	}
	/**
	* 获取商户logo
	* @return 商户logo
	*/
	public String getLogo() {
	return logo;
	}

	/**
	* 设置商户logo
	* @param logo 商户logo
	*/
	public void setLogo(String logo) {
	this.logo = logo;
	}


}