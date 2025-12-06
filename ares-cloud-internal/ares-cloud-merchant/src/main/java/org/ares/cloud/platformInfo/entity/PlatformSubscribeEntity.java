package org.ares.cloud.platformInfo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 订阅基础信息 实体
* @version 1.0.0
* @date 2024-10-31
*/
@TableName("platform_subscribe")
@EqualsAndHashCode(callSuper = false)
public class PlatformSubscribeEntity extends BaseEntity {
	/**
	* 订阅类型（1：服务订阅；2：存储订阅）
	*/
	private String subscribeType;
	/**
	* 金额
	*/
	private BigDecimal price;
	/**
	* 子类型:网站、财务软件
	*/
//	private Integer type;
	/**
	* 存储空间
	*/
	private Integer memory;

	private Integer month;

	/**
	* 获取订阅类型key
	* @return 订阅类型
	*/
	public String getSubscribeType() {
		return subscribeType;
	}

	/**
	* 设置订阅类型key
	* @param subscribeType 订阅类型
	*/
	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
	}
	/**
	* 获取金额
	* @return 金额
	*/
	public BigDecimal getPrice() {
		return price;
	}

	/**
	* 设置金额
	* @param price 金额
	*/
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
//	/**
//	* 获取子类型:网站、财务软件
//	* @return 子类型:网站、财务软件
//	*/
//	public Integer getType() {
//		return type;
//	}
//
//	/**
//	* 设置子类型:网站、财务软件
//	* @param type 子类型:网站、财务软件
//	*/
//	public void setType(Integer type) {
//		this.type = type;
//	}
	/**
	* 获取存储空间
	* @return 存储空间
	*/
	public Integer getMemory() {
		return memory;
	}

	/**
	* 设置存储空间
	* @param memory 存储空间
	*/
	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}