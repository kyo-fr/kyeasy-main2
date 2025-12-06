package org.ares.cloud.merchantInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户广告 实体
* @version 1.0.0
* @date 2025-01-03
*/
@TableName("merchant_advertised")
@EqualsAndHashCode(callSuper = false)
public class MerchantAdvertisedEntity extends TenantEntity {
	/**
	* 广告描述
	*/
	private String contents;
	/**
	* 获取广告描述
	* @return 广告描述
	*/
	public String getContents() {
		return contents;
	}

	/**
	* 设置广告描述
	* @param contents 广告描述
	*/
	public void setContents(String contents) {
		this.contents = contents;
	}
}