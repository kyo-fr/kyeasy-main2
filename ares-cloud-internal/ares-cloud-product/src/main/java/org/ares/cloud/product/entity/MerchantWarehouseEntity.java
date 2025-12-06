package org.ares.cloud.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户仓库主数据 实体
* @version 1.0.0
* @date 2025-03-22
*/
@TableName("merchant_warehouse")
@EqualsAndHashCode(callSuper = false)
public class MerchantWarehouseEntity extends TenantEntity {
	/**
	* 仓库名称
	*/
	private String name;
	/**
	* 获取仓库名称
	* @return 仓库名称
	*/
	public String getName() {
		return name;
	}

	/**
	* 设置仓库名称
	* @param name 仓库名称
	*/
	public void setName(String name) {
		this.name = name;
	}
}