package org.ares.cloud.platformInfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户类型 实体
* @version 1.0.0
* @date 2024-10-15
*/
@TableName("platform_merchant_type")
@EqualsAndHashCode(callSuper = false)
public class MerchantTypeEntity extends BaseEntity {
	/**
	* 类型名称
	*/
	private String type;
	/**
	* 获取类型名称
	* @return 类型名称
	*/
	public String getType() {
		return type;
	}

	/**
	* 设置类型名称
	* @param type 类型名称
	*/
	public void setType(String type) {
		this.type = type;
	}
}