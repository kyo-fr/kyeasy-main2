package org.ares.cloud.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户关键字 实体
* @version 1.0.0
* @date 2024-10-11
*/
@TableName("merchant_key_words")
@EqualsAndHashCode(callSuper = false)
public class MerchantKeyWordsEntity extends TenantEntity {
	/**
	* 关键字名称
	*/
	private String keyName;
	/**
	* 关键字id
	*/
//	private String keyId;
	/**
	* 关键字状态
	*/
	private Integer status;
	/**
	* 获取关键字名称
	* @return 关键字名称
	*/
	public String getKeyName() {
		return keyName;
	}

	/**
	* 设置关键字名称
	* @param keyName 关键字名称
	*/
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	/**
	* 获取关键字id
	* @return 关键字id
	*/
//	public String getKeyId() {
//		return keyId;
//	}
//
//	/**
//	* 设置关键字id
//	* @param keyId 关键字id
//	*/
//	public void setKeyId(String keyId) {
//		this.keyId = keyId;
//	}
	/**
	* 获取关键字状态
	* @return 关键字状态
	*/
	public Integer getStatus() {
		return status;
	}

	/**
	* 设置关键字状态
	* @param status 关键字状态
	*/
	public void setStatus(Integer status) {
		this.status = status;
	}
}