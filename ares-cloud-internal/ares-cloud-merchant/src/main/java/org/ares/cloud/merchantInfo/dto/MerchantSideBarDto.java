package org.ares.cloud.merchantInfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;

/**
* @author hugo tangxkwork@163.com
* @description 商户侧栏 数据模型
* @version 1.0.0
* @date 2024-10-09
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户侧栏")
public class MerchantSideBarDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "勾选侧栏服务名",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.merchantSideBar.name.notBlank}")
	private String name;

	@Schema(description = "侧栏服务code：1:网上支付; 2:保险; 3:货到付款; 4:免费配送; 5:退款; 6:到店取货",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "code")
	@NotNull(message = "{validation.merchantSideBar.code.notBlank}")
	private Integer code;

	@Schema(description = "是否勾选,1:是;2:否",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "status")
	@NotNull(message = "{validation.merchantSideBar.status.notBlank}")
	private Integer status;
	/**
	* 获取勾选侧栏服务
	* @return 勾选侧栏服务
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置勾选侧栏服务
	* @param name 勾选侧栏服务
	*/
	public void setName(String name) {
	this.name = name;
	}
	/**
	* 获取是否勾选
	* @return 是否勾选
	*/
	public Integer getStatus() {
	return status;
	}

	/**
	* 设置是否勾选
	* @param status 是否勾选
	*/
	public void setStatus(Integer status) {
	this.status = status;
	}


	/**
	* 获取侧栏服务code
	* @return 侧栏服务code
	*/
	public Integer getCode() {
		return code;
	}

	/**
	* 设置侧栏服务code
	* @param code 侧栏服务code
	*/
	public void setCode(Integer code) {
		this.code = code;
	}
}