package org.ares.cloud.merchantInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 主规格主数据 实体
* @version 1.0.0
* @date 2025-03-18
*/
@TableName("merchant_specification")
@EqualsAndHashCode(callSuper = false)
public class MerchantSpecificationEntity extends TenantEntity {
	/**
	* 规格类型(单选：radio，多选：choices)
	*/
	private String type;
	/**
	* 主规格名称
	*/
	private String name;
	/**
	* 获取规格类型(单选：radio，多选：choices)
	* @return 规格类型(单选：radio，多选：choices)
	*/
	public String getType() {
		return type;
	}

	/**
	* 设置规格类型(单选：radio，多选：choices)
	* @param type 规格类型(单选：radio，多选：choices)
	*/
	public void setType(String type) {
		this.type = type;
	}
	/**
	* 获取主规格名称
	* @return 主规格名称
	*/
	public String getName() {
		return name;
	}

	/**
	* 设置主规格名称
	* @param name 主规格名称
	*/
	public void setName(String name) {
		this.name = name;
	}
}