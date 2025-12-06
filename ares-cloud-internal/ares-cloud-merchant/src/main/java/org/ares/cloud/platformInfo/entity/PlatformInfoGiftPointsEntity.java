package org.ares.cloud.platformInfo.entity;

import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 货币礼物点统计 实体
* @version 1.0.0
* @date 2025-06-09
*/
@TableName("platforminfo_gift_points")
@EqualsAndHashCode(callSuper = false)
public class PlatformInfoGiftPointsEntity extends BaseEntity {
	/**
	* 礼物点手续费总和
	*/
	private BigDecimal totalPremium;
	/**
	* 货币类型
	*/
	private String currency;
	/**
	* 用户id
	*/
	private String userId;
	/**
	* 礼物点区域
	*/
	private String area;
	/**
	* 冻结账户礼物点总和
	*/
	private BigDecimal freezePoint;
	/**
	* 正常账户礼物点总和
	*/
	private BigDecimal normalPoint;
	/**
	* 礼物点汇率
	*/
	private BigDecimal rate;
	/**
	* 已出售礼物点数
	*/
	private BigDecimal sellPoint;
	/**
	* 礼物点总数
	*/
	private BigDecimal totalPoint;
	/**
	* 商户礼物点总和
	*/
	private BigDecimal merchantPoint;
	/**
	* 用户礼物点总和
	*/
	private BigDecimal userPoint;
	/**
	* 现有礼物点
	*/
	private BigDecimal currentPoint;
	/**
	* 获取礼物点手续费总和
	* @return 礼物点手续费总和
	*/
	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

	/**
	* 设置礼物点手续费总和
	* @param totalPremium 礼物点手续费总和
	*/
	public void setTotalPremium(BigDecimal totalPremium) {
		this.totalPremium = totalPremium;
	}
	/**
	* 获取货币类型
	* @return 货币类型
	*/
	public String getCurrency() {
		return currency;
	}

	/**
	* 设置货币类型
	* @param currency 货币类型
	*/
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	* 获取用户id
	* @return 用户id
	*/
	public String getUserId() {
		return userId;
	}

	/**
	* 设置用户id
	* @param userId 用户id
	*/
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	* 获取礼物点区域
	* @return 礼物点区域
	*/
	public String getArea() {
		return area;
	}

	/**
	* 设置礼物点区域
	* @param area 礼物点区域
	*/
	public void setArea(String area) {
		this.area = area;
	}
	/**
	* 获取冻结账户礼物点总和
	* @return 冻结账户礼物点总和
	*/
	public BigDecimal getFreezePoint() {
		return freezePoint;
	}

	/**
	* 设置冻结账户礼物点总和
	* @param freezePoint 冻结账户礼物点总和
	*/
	public void setFreezePoint(BigDecimal freezePoint) {
		this.freezePoint = freezePoint;
	}
	/**
	* 获取正常账户礼物点总和
	* @return 正常账户礼物点总和
	*/
	public BigDecimal getNormalPoint() {
		return normalPoint;
	}

	/**
	* 设置正常账户礼物点总和
	* @param normalPoint 正常账户礼物点总和
	*/
	public void setNormalPoint(BigDecimal normalPoint) {
		this.normalPoint = normalPoint;
	}
	/**
	* 获取礼物点汇率
	* @return 礼物点汇率
	*/
	public BigDecimal getRate() {
		return rate;
	}

	/**
	* 设置礼物点汇率
	* @param rate 礼物点汇率
	*/
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	/**
	* 获取已出售礼物点数
	* @return 已出售礼物点数
	*/
	public BigDecimal getSellPoint() {
		return sellPoint;
	}

	/**
	* 设置已出售礼物点数
	* @param sellPoint 已出售礼物点数
	*/
	public void setSellPoint(BigDecimal sellPoint) {
		this.sellPoint = sellPoint;
	}
	/**
	* 获取礼物点总数
	* @return 礼物点总数
	*/
	public BigDecimal getTotalPoint() {
		return totalPoint;
	}

	/**
	* 设置礼物点总数
	* @param totalPoint 礼物点总数
	*/
	public void setTotalPoint(BigDecimal totalPoint) {
		this.totalPoint = totalPoint;
	}
	/**
	* 获取商户礼物点总和
	* @return 商户礼物点总和
	*/
	public BigDecimal getMerchantPoint() {
		return merchantPoint;
	}

	/**
	* 设置商户礼物点总和
	* @param merchantPoint 商户礼物点总和
	*/
	public void setMerchantPoint(BigDecimal merchantPoint) {
		this.merchantPoint = merchantPoint;
	}
	/**
	* 获取用户礼物点总和
	* @return 用户礼物点总和
	*/
	public BigDecimal getUserPoint() {
		return userPoint;
	}

	/**
	* 设置用户礼物点总和
	* @param userPoint 用户礼物点总和
	*/
	public void setUserPoint(BigDecimal userPoint) {
		this.userPoint = userPoint;
	}
	/**
	* 获取现有礼物点
	* @return 现有礼物点
	*/
	public BigDecimal getCurrentPoint() {
		return currentPoint;
	}

	/**
	* 设置现有礼物点
	* @param currentPoint 现有礼物点
	*/
	public void setCurrentPoint(BigDecimal currentPoint) {
		this.currentPoint = currentPoint;
	}
}