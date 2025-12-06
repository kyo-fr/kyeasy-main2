package org.ares.cloud.merchantInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户文件上传 实体
* @version 1.0.0
* @date 2024-10-09
*/
@TableName("merchant_file_upload")
@EqualsAndHashCode(callSuper = false)
public class MerchantFileUploadEntity extends TenantEntity {

	/**
	* 营业执照
	*/
	private String businessLicense;
	/**
	* 银行rib
	*/
	private String bankRib;
	/**
	* 商户logo
	*/
	private String logo;
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