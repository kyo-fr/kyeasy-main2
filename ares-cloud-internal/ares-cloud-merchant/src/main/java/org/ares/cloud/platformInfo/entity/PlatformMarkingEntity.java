package org.ares.cloud.platformInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo platformInfo
* @description 商品标注 实体
* @version 1.0.0
* @date 2024-11-04
*/
@TableName("platform_marking")
@EqualsAndHashCode(callSuper = false)
public class PlatformMarkingEntity extends TenantDto {
	/**
	* 标注名称
	*/
	private String markingName;
	/**
	* 获取标注名称
	* @return 标注名称
	*/
	public String getMarkingName() {
		return markingName;
	}

	/**
	* 设置标注名称
	* @param markingName 标注名称
	*/
	public void setMarkingName(String markingName) {
		this.markingName = markingName;
	}
}