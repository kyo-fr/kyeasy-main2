package org.ares.cloud.merchantInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 子规格主数据 实体
* @version 1.0.0
* @date 2025-03-18
*/
@TableName("merchant_sub_specification")
@EqualsAndHashCode(callSuper = false)
public class MerchantSubSpecificationEntity extends TenantEntity {
	/**
	* 子规格名称
	*/
	private String subName;
	/**
	* 子规格库存
	*/
	private Integer subNum;
	/**
	* 子规格价格
	*/
	private BigDecimal subPrice;
	/**
	* 子规格图片
	*/
	private String subPicture;
	/**
	* 主规格id
	*/
	private String specificationId;
	/**
	* 获取子规格名称
	* @return 子规格名称
	*/
	public String getSubName() {
		return subName;
	}

	/**
	* 设置子规格名称
	* @param subName 子规格名称
	*/
	public void setSubName(String subName) {
		this.subName = subName;
	}
	/**
	* 获取子规格库存
	* @return 子规格库存
	*/
	public Integer getSubNum() {
		return subNum;
	}

	/**
	* 设置子规格库存
	* @param subNum 子规格库存
	*/
	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
	}
	/**
	* 获取子规格价格
	* @return 子规格价格
	*/
	public BigDecimal getSubPrice() {
		return subPrice;
	}

	/**
	* 设置子规格价格
	* @param subPrice 子规格价格
	*/
	public void setSubPrice(BigDecimal subPrice) {
		this.subPrice = subPrice;
	}
	/**
	* 获取子规格图片
	* @return 子规格图片
	*/
	public String getSubPicture() {
		return subPicture;
	}

	/**
	* 设置子规格图片
	* @param subPicture 子规格图片
	*/
	public void setSubPicture(String subPicture) {
		this.subPicture = subPicture;
	}
	/**
	* 获取主规格id
	* @return 主规格id
	*/
	public String getSpecificationId() {
		return specificationId;
	}

	/**
	* 设置主规格id
	* @param specificationId 主规格id
	*/
	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
}