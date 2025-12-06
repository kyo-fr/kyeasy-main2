package org.ares.cloud.merchantInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户休假 实体
* @version 1.0.0
* @date 2025-01-03
*/
@TableName("merchant_holiday")
@EqualsAndHashCode(callSuper = false)
public class MerchantHoliDayEntity extends TenantEntity {
	/**
	* 休假描述
	*/
	private String contents;
	/**
	* 获取休假描述
	* @return 休假描述
	*/
	public String getContents() {
		return contents;
	}

	/**
	* 设置休假描述
	* @param contents 休假描述
	*/
	public void setContents(String contents) {
		this.contents = contents;
	}
}