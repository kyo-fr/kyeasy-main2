package org.ares.cloud.merchantInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 货运配送费用 实体
* @version 1.0.0
* @date 2024-11-05
*/
@TableName("merchant_freight")
@EqualsAndHashCode(callSuper = false)
public class MerchantFreightEntity extends TenantEntity {
	/**
	* 货运类型: 1-cm3；2-ft3；3-m3；4-托盘；5-20GP；6-40GP；7-40HQ
	*/
	private Integer type;
	/**
	* 每公斤费用
	*/
	private BigDecimal kilograms;
	/**
	* 每体积费用
	*/
	private BigDecimal expenses;

	/**
	 * 是否勾选,是否勾选,Y:是; N:否;默认否
	 */
	private String status ="N";



	/**
	* 获取货运类型: 1-cm3；2-ft3；3-m3；4-托盘；5-20GP；6-40GP；7-40HQ
	* @return 货运类型: 1-cm3；2-ft3；3-m3；4-托盘；5-20GP；6-40GP；7-40HQ
	*/
	public Integer getType() {
		return type;
	}

	/**
	* 设置货运类型: 1-cm3；2-ft3；3-m3；4-托盘；5-20GP；6-40GP；7-40HQ
	* @param type 货运类型: 1-cm3；2-ft3；3-m3；4-托盘；5-20GP；6-40GP；7-40HQ
	*/
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	* 获取每公斤费用
	* @return 每公斤费用
	*/
	public BigDecimal getKilograms() {
		return kilograms;
	}

	/**
	* 设置每公斤费用
	* @param kilograms 每公斤费用
	*/
	public void setKilograms(BigDecimal kilograms) {
		this.kilograms = kilograms;
	}
	/**
	* 获取每体积费用
	* @return 每体积费用
	*/
	public BigDecimal getExpenses() {
		return expenses;
	}

	/**
	* 设置每体积费用
	* @param expenses 每体积费用
	*/
	public void setExpenses(BigDecimal expenses) {
		this.expenses = expenses;
	}

	/**
	 * 获取是否勾选,,Y:是; N:否;默认否
	 * @return 是否勾选,Y:是; N:否;默认否
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置是否勾选,Y:是; N:否;默认否
	 * @param status 是否勾选,Y:是; N:否;默认否
	 */

	public void setStatus(String status) {
		this.status = status;
	}
}