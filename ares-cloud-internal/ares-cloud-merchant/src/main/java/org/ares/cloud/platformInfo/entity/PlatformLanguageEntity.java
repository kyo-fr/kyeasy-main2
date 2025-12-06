package org.ares.cloud.platformInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 平台设置语言 实体
* @version 1.0.0
* @date 2024-10-15
*/
@TableName("platform_language")
@EqualsAndHashCode(callSuper = false)
public class PlatformLanguageEntity extends BaseEntity {
	/**
	* 语言名称
	*/
	private String name;
	/**
	* 状态 1:启用；2:关闭
	*/
	private Integer status;
	/**
	* 获取语言名称
	* @return 语言名称
	*/
	public String getName() {
		return name;
	}

	/**
	* 设置语言名称
	* @param name 语言名称
	*/
	public void setName(String name) {
		this.name = name;
	}
	/**
	* 获取状态 1:启用；2:关闭
	* @return 状态 1:启用；2:关闭
	*/
	public Integer getStatus() {
		return status;
	}

	/**
	* 设置状态 1:启用；2:关闭
	* @param status 状态 1:启用；2:关闭
	*/
	public void setStatus(Integer status) {
		this.status = status;
	}
}