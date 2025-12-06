package org.ares.cloud.businessId.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 系统业务id 实体
* @version 1.0.0
* @date 2024-10-13
*/
@TableName("sys_business_id")
@EqualsAndHashCode(callSuper = false)
public class BusinessIdEntity extends BaseEntity {
	/**
	* 业务模块名
	*/
	private String moduleName;
	/**
	* 业务前缀
	*/
	private String prefix;
	/**
	* 当前最大流水号
	*/
	private Long maxSequence;
	/**
	* 流水号周期(0:从不，1:每天，2:每周，3：每月，4：每年)
	*/
	private Integer cycleType;
	/**
	* 当前时间
	*/
	private String currentDate;
	/**
	* 日期模版
	*/
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