package org.ares.cloud.merchantInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户营业时间 实体
* @version 1.0.0
* @date 2024-10-08
*/
@TableName("merchant_opening_hours")
@EqualsAndHashCode(callSuper = false)
public class OpeningHoursEntity extends BaseEntity {
	/**
	* 工作日
	*/
	
	private String weekDay;


	/**
	* 营业时间类型 1: 24/7全天候 ; 2：设置工作日时间
	*/
	private String timeType;


	/**
	 * 上午是否休息 1:是 ; 2：否
	 */
	private Integer isAmRest;


	/**
	 * 下午是否休息 1:是 ; 2：否
	 */
	private Integer isPmRest;


	/**
	 * 上午开店时间
	 */
	@Schema(description = "上午开店时间")
	@JsonProperty(value = "openTime1")
	private String openTime1;

	/**
	 * 上午闭店时间
	 */
	@Schema(description = "上午闭店时间")
	@JsonProperty(value = "closeTime1")
	private String closeTime1;


	/**
	 * 下午开店时间
	 */
	@Schema(description = "下午开店时间")
	@JsonProperty(value = "openTime2")
	private String openTime2;

	/**
	 * 下午闭店时间
	 */
	@Schema(description = "下午闭店时间")
	@JsonProperty(value = "closeTime2")
	private String closeTime2;



	/**
	 * 类型是否平台 1:平台 ; 2：商户
	 */
	private String tenantId;

	/**
	 * 获取租户
	 * @return 租户
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * 设置租户
	 * @param tenantId 租户
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	* 获取工作日
	* @return 工作日
	*/
	public String getWeekDay() {
		return weekDay;
	}

	/**
	* 设置工作日
	* @param weekDay 工作日
	*/
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	/**
	* 获取营业时间类型 1: 24/7全天候 ; 2：设置工作日时间
	* @return 营业时间类型 1: 24/7全天候 ; 2：设置工作日时间
	*/
	public String getTimeType() {
		return timeType;
	}

	/**
	* 设置营业时间类型 1: 24/7全天候 ; 2：设置工作日时间
	* @param timeType 营业时间类型 1: 24/7全天候 ; 2：设置工作日时间
	*/
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}


	public String getOpenTime1() {
		return openTime1;
	}
	public void setOpenTime1(String openTime1) {
		this.openTime1 = openTime1;
	}
	public String getCloseTime1() {
		return closeTime1;
	}
	public void setCloseTime1(String closeTime1) {
		this.closeTime1 = closeTime1;
	}
	public String getOpenTime2() {
		return openTime2;
	}
	public void setOpenTime2(String openTime2) {
		this.openTime2 = openTime2;
	}
	public String getCloseTime2() {
		return closeTime2;
	}
	public void setCloseTime2(String closeTime2) {
		this.closeTime2 = closeTime2;
	}

	public Integer getIsAmRest() {
		return isAmRest;
	}

	public void setIsAmRest(Integer isAmRest) {
		this.isAmRest = isAmRest;
	}


	public Integer getIsPmRest() {
		return isPmRest;
	}


	public void setIsPmRest(Integer isPmRest) {
		this.isPmRest = isPmRest;
	}

}