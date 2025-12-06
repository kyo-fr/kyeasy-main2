package org.ares.cloud.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 标注主数据 实体
* @version 1.0.0
* @date 2025-03-19
*/
@TableName("merchant_marking")
@EqualsAndHashCode(callSuper = false)
public class MerchantMarkingEntity extends TenantEntity {
	/**
	* 标注名称
	*/
	private String name;
	/**
	* 获取标注名称
	* @return 标注名称
	*/
	public String getName() {
		return name;
	}

	/**
	* 设置标注名称
	* @param markingName 标注名称
	*/
	public void setName(String name) {
		this.name = name;
	}
}