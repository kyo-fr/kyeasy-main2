package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 货运配送费用 数据模型
* @version 1.0.0
* @date 2024-11-05
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "货运配送费用")
public class MerchantFreightDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "货运类型: 1-cm3；2-ft3；3-m3；4-托盘；5-20GP；6-40GP；7-40HQ",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "type")
	@NotNull(message = "{validation.merchant.merchantFreight.type.notBlank}")
	private Integer type;

	@Schema(description = "每公斤费用",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "kilograms")
	@NotNull(message = "{validation.merchant.merchantFreight.kilograms.notBlank}")
	private BigDecimal kilograms;

	@Schema(description = "每体积费用",requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "{validation.merchant.merchantFreight.expenses.notBlank}")
	@JsonProperty(value = "expenses")
	private BigDecimal expenses;

	@Schema(description = "是否勾选,Y:是; N:否;默认否",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "status")
	@NotBlank(message = "{validation.merchant.merchantFreight.status.notBlank}")
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
	* 获取是否勾选,是否勾选,Y:是; N:否;默认否
	* @return 是否勾选,是否勾选,Y:是; N:否;默认否
	*/
	public String getStatus() {
		return status;
	}
	/**
	* 设置是否勾选,是否勾选,Y:是; N:否;默认否
	* @param status 是否勾选,是否勾选,Y:是; N:否;默认否
	*/
	public void setStatus(String status) {
		this.status = status;
	}
}
