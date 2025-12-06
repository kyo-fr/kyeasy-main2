package org.ares.cloud.merchantInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 硬件管理 实体
* @version 1.0.0
* @date 2024-10-12
*/
@TableName("hardware")
@EqualsAndHashCode(callSuper = false)
public class HardWareEntity extends BaseEntity {
	/**
	* 硬件id
	*/
	private String hardwareId;
	/**
	* 类型id
	*/
	private Integer typeId;
	/**
	* 类型名称
	*/
	private String name;
	/**
	* 状态(1:可用；2不可用)
	*/
	private Integer status;
	/**
	* 获取硬件id
	* @return 硬件id
	*/
	public String getHardwareId() {
		return hardwareId;
	}

	/**
	* 设置硬件id
	* @param hardwareId 硬件id
	*/
	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}
	/**
	* 获取类型id
	* @return 类型id
	*/
	public Integer getTypeId() {
		return typeId;
	}

	/**
	* 设置类型id
	* @param typeId 类型id
	*/
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/**
	* 获取类型名称
	* @return 类型名称
	*/
	public String getName() {
		return name;
	}

	/**
	* 设置类型名称
	* @param name 类型名称
	*/
	public void setName(String name) {
		this.name = name;
	}
	/**
	* 获取状态(1:可用；2不可用)
	* @return 状态(1:可用；2不可用)
	*/
	public Integer getStatus() {
		return status;
	}

	/**
	* 设置状态(1:可用；2不可用)
	* @param status 状态(1:可用；2不可用)
	*/
	public void setStatus(Integer status) {
		this.status = status;
	}
}