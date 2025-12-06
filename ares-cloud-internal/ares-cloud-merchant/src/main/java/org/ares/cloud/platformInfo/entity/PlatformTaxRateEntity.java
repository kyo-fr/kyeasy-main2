package org.ares.cloud.platformInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 税率 实体
* @version 1.0.0
* @date 2024-10-15
*/
@TableName("platform_tax_rate")
@EqualsAndHashCode(callSuper = false)
public class PlatformTaxRateEntity extends BaseEntity {
	/**
	* 类型
	*/
	private String type;
	/**
	* 税率
	*/
	private BigDecimal taxRate;

	/**
	 * 商户id
	 */
	private String tenantId;

	/**
	* 获取类型
	* @return 类型
	*/
	public String getType() {
		return type;
	}

	/**
	* 设置类型
	* @param type 类型
	*/
	public void setType(String type) {
		this.type = type;
	}
	/**
	* 获取税率
	* @return 税率
	*/
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	/**
	* 设置税率
	* @param taxRate 税率
	*/
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}