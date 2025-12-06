package org.ares.cloud.businessId.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 系统业务id 数据模型
* @version 1.0.0
* @date 2024-10-13
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统业务id")
public class BusinessIdDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "业务模块名",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "moduleName")
	@NotBlank(message = "{validation.businessId.moduleName}")
	@Size(max = 20, message = "validation.size.max")
	private String moduleName;

	@Schema(description = "业务前缀")
	@JsonProperty(value = "prefix")
	@Size(max = 10, message = "validation.size.max")
	private String prefix;

	@Schema(description = "当前最大流水号")
	@JsonProperty(value = "maxSequence")
	private Long maxSequence;

	@Schema(description = "流水号周期(0:从不，1:每天，2:每周，3：每月，4：每年)")
	@JsonProperty(value = "cycleType")
	private Integer cycleType;

	@Schema(description = "当前时间",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "currentDate")
	@NotBlank(message = "{validation.businessId.currentDate}")
	@Size(max = 13, message = "validation.size.max")
	private String currentDate;

	@Schema(description = "日期模版")
	@JsonProperty(value = "dateTemp")
	@Size(max = 15, message = "validation.size.max")
	private String dateTemp;
	/**
	* 获取业务模块名
	* @return 业务模块名
	*/
	public String getModuleName() {
	return moduleName;
	}

	/**
	* 设置业务模块名
	* @param moduleName 业务模块名
	*/
	public void setModuleName(String moduleName) {
	this.moduleName = moduleName;
	}
	/**
	* 获取业务前缀
	* @return 业务前缀
	*/
	public String getPrefix() {
	return prefix;
	}

	/**
	* 设置业务前缀
	* @param prefix 业务前缀
	*/
	public void setPrefix(String prefix) {
	this.prefix = prefix;
	}
	/**
	* 获取当前最大流水号
	* @return 当前最大流水号
	*/
	public Long getMaxSequence() {
	return maxSequence;
	}

	/**
	* 设置当前最大流水号
	* @param maxSequence 当前最大流水号
	*/
	public void setMaxSequence(Long maxSequence) {
	this.maxSequence = maxSequence;
	}
	/**
	* 获取流水号周期(0:从不，1:每天，2:每周，3：每月，4：每年)
	* @return 流水号周期(0:从不，1:每天，2:每周，3：每月，4：每年)
	*/
	public Integer getCycleType() {
	return cycleType;
	}

	/**
	* 设置流水号周期(0:从不，1:每天，2:每周，3：每月，4：每年)
	* @param cycleType 流水号周期(0:从不，1:每天，2:每周，3：每月，4：每年)
	*/
	public void setCycleType(Integer cycleType) {
	this.cycleType = cycleType;
	}
	/**
	* 获取当前时间
	* @return 当前时间
	*/
	public String getCurrentDate() {
	return currentDate;
	}

	/**
	* 设置当前时间
	* @param currentDate 当前时间
	*/
	public void setCurrentDate(String currentDate) {
	this.currentDate = currentDate;
	}
	/**
	* 获取日期模版
	* @return 日期模版
	*/
	public String getDateTemp() {
	return dateTemp;
	}

	/**
	* 设置日期模版
	* @param dateTemp 日期模版
	*/
	public void setDateTemp(String dateTemp) {
	this.dateTemp = dateTemp;
	}
}