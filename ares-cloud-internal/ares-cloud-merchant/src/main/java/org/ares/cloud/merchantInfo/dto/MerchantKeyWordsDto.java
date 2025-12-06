package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商户关键字 数据模型
* @version 1.0.0
* @date 2024-10-11
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户关键字")
public class MerchantKeyWordsDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "关键字名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "keyName")
	@NotBlank(message = "{validation.merchant.keyWords.keyName.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String keyName;

//	@Schema(description = "关键字id",requiredMode = Schema.RequiredMode.REQUIRED)
//	@JsonProperty(value = "keyId")
//	@Size(max = 32, message = "validation.size.max")
//	private String keyId;

	@Schema(description = "关键字状态 1:启用; 2关闭",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "status")
	private Integer status;

	/**
	* 获取关键字名称
	* @return 关键字名称
	*/
	public String getKeyName() {
	return keyName;
	}

	/**
	* 设置关键字名称
	* @param keyName 关键字名称
	*/
	public void setKeyName(String keyName) {
	this.keyName = keyName;
	}
	/**
	* 获取关键字id
	* @return 关键字id
	*/
//	public String getKeyId() {
//	return keyId;
//	}
//
//	/**
//	* 设置关键字id
//	* @param keyId 关键字id
//	*/
//	public void setKeyId(String keyId) {
//	this.keyId = keyId;
//	}
	/**
	* 获取关键字状态
	* @return 关键字状态
	*/
	public Integer getStatus() {
	return status;
	}

	/**
	* 设置关键字状态
	* @param status 关键字状态
	*/
	public void setStatus(Integer status) {
	this.status = status;
	}
//	/**
//	* 获取租户(商户id)
//	* @return 租户(商户id)
//	*/
//	public String getTenantId() {
//	return tenantId;
//	}
//
//	/**
//	* 设置租户(商户id)
//	* @param tenantId 租户(商户id)
//	*/
//	public void setTenantId(String tenantId) {
//	this.tenantId = tenantId;
//	}
}