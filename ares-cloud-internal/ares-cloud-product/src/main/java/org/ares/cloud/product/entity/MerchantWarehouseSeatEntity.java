package org.ares.cloud.product.entity;

import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户仓库位子主数据 实体
* @version 1.0.0
* @date 2025-03-22
*/
@TableName("merchant_warehouse_seat")
@EqualsAndHashCode(callSuper = false)
public class MerchantWarehouseSeatEntity extends TenantEntity {
	/**
	* 仓库id
	*/
	private String warehouseId;
	/**
	* 位子名称
	*/
	private String seatName;
	/**
	* 获取仓库id
	* @return 仓库id
	*/
	public String getWarehouseId() {
		return warehouseId;
	}

	/**
	* 设置仓库id
	* @param warehouseId 仓库id
	*/
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	/**
	* 获取位子名称
	* @return 位子名称
	*/
	public String getSeatName() {
		return seatName;
	}

	/**
	* 设置位子名称
	* @param seatName 位子名称
	*/
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
}