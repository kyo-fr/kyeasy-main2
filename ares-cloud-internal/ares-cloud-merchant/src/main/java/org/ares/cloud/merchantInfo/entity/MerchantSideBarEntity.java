package org.ares.cloud.merchantInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户侧栏 实体
* @version 1.0.0
* @date 2024-10-09
*/
@TableName("merchant_side_bar")
@EqualsAndHashCode(callSuper = false)
public class MerchantSideBarEntity extends TenantEntity {
	/**
	* 勾选侧栏服务
	*/
	private String name;
	/**
	* 是否勾选
	*/
	private Integer status;


	/**
	 * 获取侧栏编码
	 */
	private Integer code;

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
	* 获取侧栏编码
	* @return 侧栏编码
	*/
	public Integer getCode() {
		return code;
	}
	/**
	* 设置侧栏编码
	* @param code 侧栏编码
	*/
	public void setCode(Integer code) {
		this.code = code;
	}
}