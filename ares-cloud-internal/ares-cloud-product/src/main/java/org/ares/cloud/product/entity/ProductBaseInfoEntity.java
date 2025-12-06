package org.ares.cloud.product.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.apache.ibatis.type.JdbcType;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomMoneySerializer;
import org.ares.cloud.database.entity.TenantEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商品基础信息 实体
* @version 1.0.0
* @date 2024-11-06
*/
@TableName("product_base_info")
@EqualsAndHashCode(callSuper = false)
public class ProductBaseInfoEntity extends TenantEntity {

	/**
	* 商品名称
	*/
	private String name;
	/**
	* 商品价格
	*/
	private BigDecimal price;
	/**
	* 库存
	*/
	private Integer inventory;
	/**
	* 一级分类id
	*/
	private String levelOneId;
	/**
	* 二级分类id
	*/
	private String levelTwoId;
	/**
	* 三级分类id
	*/
	private String levelThreeId;
	/**
	* 商品税率id
	*/
	private String taxId;
	/**
	* 商品简介
	*/
	private String briefly;

	/**
	 * 商品图片
	 */
	private String pictureUrl;

	/**
	* 视频
	*/
	private String videoUrl;
	/**
	* 重量
	*/
	private BigDecimal weight;
	/**
	* 长度
	*/
	private BigDecimal length;
	/**
	* 是否勾选配送费：1-是；2-否
	*/
	private String isDistribution;
	/**
	* 是否勾选服务费：1-是；2-否
	*/
	private String isServe;
	/**
	* 配送费金额
	*/
	private BigDecimal deliveryFee;
	/**
	* 服务费百分比
	*/
	private BigDecimal preServeFee;
	/**
	* 服务费金额
	*/
	private BigDecimal serveFee;
	/**
	* 宽度
	*/
	private BigDecimal width;
	/**
	* 高度
	*/
	private BigDecimal height;
	/**
	* 配送费百分比
	*/
	private BigDecimal perDeliveryFee;

	/**
	 * 上下架
	 */
	private String isEnable;

	/**
	 * 商品类型 common-普通商品;discount-优惠商品;auction-拍卖商品;wholesale-批发商品
	 */
	private String type;


	/**
	 * 仓库id
	 */
	private String warehouseId;
	

	/**
	 * 位子id
	 */
	private String warehouseSeatId;

	/**
	 * 商品生产时间
	 */
	private Long produceTime;

	/**
	 * 过期时间
	 */
	private Long overdueTime;
	private BigDecimal taxRate;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	/**
	* 获取商品名称
	* @return 商品名称
	*/
	public String getName() {
		return name;
	}

	/**
	* 设置商品名称
	* @param name 商品名称
	*/
	public void setName(String name) {
		this.name = name;
	}
	/**
	* 获取商品价格
	* @return 商品价格
	*/
	public BigDecimal getPrice() {
		return price;
	}

	/**
	* 设置商品价格
	* @param price 商品价格
	*/
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	* 获取库存
	* @return 库存
	*/
	public Integer getInventory() {
		return inventory;
	}

	/**
	* 设置库存
	* @param inventory 库存
	*/
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	/**
	* 获取一级分类id
	* @return 一级分类id
	*/
	public String getLevelOneId() {
		return levelOneId;
	}

	/**
	* 设置一级分类id
	* @param levelOneId 一级分类id
	*/
	public void setLevelOneId(String levelOneId) {
		this.levelOneId = levelOneId;
	}
	/**
	* 获取二级分类id
	* @return 二级分类id
	*/
	public String getLevelTwoId() {
		return levelTwoId;
	}

	/**
	* 设置二级分类id
	* @param levelTwoId 二级分类id
	*/
	public void setLevelTwoId(String levelTwoId) {
		this.levelTwoId = levelTwoId;
	}
	/**
	* 获取三级分类id
	* @return 三级分类id
	*/
	public String getLevelThreeId() {
		return levelThreeId;
	}

	/**
	* 设置三级分类id
	* @param levelThreeId 三级分类id
	*/
	public void setLevelThreeId(String levelThreeId) {
		this.levelThreeId = levelThreeId;
	}
	/**
	* 获取商品税率id
	* @return 商品税率id
	*/
	public String getTaxId() {
		return taxId;
	}

	/**
	* 设置商品税率id
	* @param taxId 商品税率id
	*/
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	/**
	* 获取商品简介
	* @return 商品简介
	*/
	public String getBriefly() {
		return briefly;
	}

	/**
	* 设置商品简介
	* @param briefly 商品简介
	*/
	public void setBriefly(String briefly) {
		this.briefly = briefly;
	}





	/**
	* 获取视频
	* @return 视频
	*/
	public String getVideoUrl() {
		return videoUrl;
	}

	/**
	* 设置视频
	* @param videoUrl 视频
	*/
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	/**
	* 获取重量
	* @return 重量
	*/
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	* 设置重量
	* @param weight 重量
	*/
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	* 获取长度
	* @return 长度
	*/
	public BigDecimal getLength() {
		return length;
	}

	/**
	* 设置长度
	* @param length 长度
	*/
	public void setLength(BigDecimal length) {
		this.length = length;
	}
	/**
	* 获取是否勾选配送费：1-是；2-否
	* @return 是否勾选配送费：1-是；2-否
	*/
	public String getIsDistribution() {
		return isDistribution;
	}

	/**
	* 设置是否勾选配送费：1-是；2-否
	* @param isDistribution 是否勾选配送费：1-是；2-否
	*/
	public void setIsDistribution(String isDistribution) {
		this.isDistribution = isDistribution;
	}
	/**
	* 获取是否勾选服务费：1-是；2-否
	* @return 是否勾选服务费：1-是；2-否
	*/
	public String getIsServe() {
		return isServe;
	}

	/**
	* 设置是否勾选服务费：1-是；2-否
	* @param isServe 是否勾选服务费：1-是；2-否
	*/
	public void setIsServe(String isServe) {
		this.isServe = isServe;
	}
	/**
	* 获取配送费金额
	* @return 配送费金额
	*/
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	/**
	* 设置配送费金额
	* @param deliveryFee 配送费金额
	*/
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	/**
	* 获取服务费百分比
	* @return 服务费百分比
	*/
	public BigDecimal getPreServeFee() {
		return preServeFee;
	}

	/**
	* 设置服务费百分比
	* @param preServeFee 服务费百分比
	*/
	public void setPreServeFee(BigDecimal preServeFee) {
		this.preServeFee = preServeFee;
	}
	/**
	* 获取服务费金额
	* @return 服务费金额
	*/
	public BigDecimal getServeFee() {
		return serveFee;
	}

	/**
	* 设置服务费金额
	* @param serveFee 服务费金额
	*/
	public void setServeFee(BigDecimal serveFee) {
		this.serveFee = serveFee;
	}
	/**
	* 获取宽度
	* @return 宽度
	*/
	public BigDecimal getWidth() {
		return width;
	}

	/**
	* 设置宽度
	* @param width 宽度
	*/
	public void setWidth(BigDecimal width) {
		this.width = width;
	}
	/**
	* 获取高度
	* @return 高度
	*/
	public BigDecimal getHeight() {
		return height;
	}

	/**
	* 设置高度
	* @param height 高度
	*/
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	/**
	* 获取配送费百分比
	* @return 配送费百分比
	*/
	public BigDecimal getPerDeliveryFee() {
		return perDeliveryFee;
	}

	/**
	* 设置配送费百分比
	* @param perDeliveryFee 配送费百分比
	*/
	public void setPerDeliveryFee(BigDecimal perDeliveryFee) {
		this.perDeliveryFee = perDeliveryFee;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public String getWarehouseSeatId() {
		return warehouseSeatId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public void setWarehouseSeatId(String warehouseSeatId) {
		this.warehouseSeatId = warehouseSeatId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}


	public Long getProduceTime() {
		return produceTime;
	}
	/**
	 * 设置商品生产时间
	 * @param produceTime 商品生产时间
	 */
	public void setProduceTime(Long produceTime) {
		this.produceTime = produceTime;
	}

	public Long getOverdueTime() {
		return overdueTime;
	}
	/**
	 * 获取商品过期时间
	 * @return 商品过期时间
	 */
	public void setOverdueTime(Long overdueTime) {
		this.overdueTime = overdueTime;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
}