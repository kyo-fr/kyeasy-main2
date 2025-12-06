package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户营业时间 数据模型
* @version 1.0.0
* @date 2024-10-08
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户营业时间")
public class OpeningHoursDto extends BaseDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "租户(商户id)")
	@JsonProperty(value = "tenantId")
	private String tenantId;


	@Schema(description = "营业时间类型 allDay: 24/7全天候 ; time：设置工作日时间; relax：休息",requiredMode = Schema.RequiredMode.REQUIRED,defaultValue = "time")
	@JsonProperty(value = "timeType")
	@NotBlank(message = "{validation.merchantOpeningHours.timeType.notBlank}")
	private String timeType;


	@Schema(description = "工作日",example = "周一")
	@JsonProperty(value = "weekDay")
	private String weekDay;

	/**
	 * 上午开店时间
	 */
	@Schema(description = "上午开店时间",example = "09:00")
	@JsonProperty(value = "openTime1")
	private String openTime1;

	/**
	 * 上午闭店时间
	 */
	@Schema(description = "上午闭店时间",example = "12:00")
	@JsonProperty(value = "closeTime1")
	private String closeTime1;


	/**
	 * 下午开店时间
	 */
	@Schema(description = "下午开店时间",example = "14:00")
	@JsonProperty(value = "openTime2")
	private String openTime2;

	/**
	 * 下午闭店时间
	 */
	@Schema(description = "下午闭店时间",example = "18:00")
	@JsonProperty(value = "closeTime2")
	private String closeTime2;


	@Schema(description = "上午是否休息 1:是 ; 2：否",defaultValue = "2")
	@JsonProperty(value = "isAMRest")
	private Integer isAmRest;

	@Schema(description = "下午是否休息 1:是 ; 2：否",defaultValue = "2")
	@JsonProperty(value = "isPMRest")
	private Integer isPmRest;

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
	* 获取上午开店时间
	* @return 上午开店时间
	*/
	public String getOpenTime1() {
	return openTime1;
	}



	public void setOpenTime1(String openTime1) {
	this.openTime1 = openTime1;
	}

	/**
	* 获取上午闭店时间
	* @return 上午闭店时间
	*/

	public String getCloseTime1() {
	return closeTime1;
	}

	public void setCloseTime1(String closeTime1) {
	this.closeTime1 = closeTime1;
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