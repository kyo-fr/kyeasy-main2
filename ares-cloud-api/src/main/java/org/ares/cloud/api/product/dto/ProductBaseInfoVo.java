package org.ares.cloud.api.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;

import java.math.BigDecimal;
import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品基础信息 数据模型
* @version 1.0.0
* @date 2024-11-06
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品基础信息")
public class ProductBaseInfoVo extends TenantDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "商品名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@Size(max = 255, message = "validation.size.max")
	private String name;

	@Schema(description = "商品价格",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "price")
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal price;

	@Schema(description = "库存",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "inventory")
	private Integer inventory;

	@Schema(description = "一级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "levelOneId")
	@Size(max = 32, message = "validation.size.max")
	private String levelOneId;

	@Schema(description = "二级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "levelTwoId")
	@Size(max = 32, message = "validation.size.max")
	private String levelTwoId;

	@Schema(description = "三级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "levelThreeId")
	@Size(max = 32, message = "validation.size.max")
	private String levelThreeId;

	@Schema(description = "商品税率id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "taxId")
	@NotBlank(message = "{validation.product.productBaseInfo.taxId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String taxId;


	@Schema(description = "商品税率值",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "taxRate")
	@NotNull(message = "{validation.product.productBaseInfo.taxRate.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private BigDecimal taxRate;


	@Schema(description = "商品简介",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "briefly")
	@Size(max = 2000, message = "validation.size.max")
	private String briefly;

	@Schema(description = "商品图片",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "pictureUrl")
	@Size(max = 10000, message = "validation.size.max")
	private String pictureUrl;

	@Schema(description = "视频")
	@JsonProperty(value = "videoUrl")
	@Size(max = 2000, message = "validation.size.max")
	private String videoUrl;

	@Schema(description = "重量")
	@JsonProperty(value = "weight")
	private BigDecimal weight;

	@Schema(description = "长度")
	@JsonProperty(value = "length")
	private BigDecimal length;

	@Schema(description = "是否勾选配送费：distribution-是；not_distribution-否")
	@JsonProperty(value = "isDistribution")
	private String isDistribution;

	@Schema(description = "是否勾选服务费：serve-是；not_serve-否")
	@JsonProperty(value = "isServe")
	private String isServe;

	@Schema(description = "配送费金额")
	@JsonProperty(value = "deliveryFee")
	private String deliveryFee;

	@Schema(description = "服务费百分比")
	@JsonProperty(value = "preServeFee")
	private BigDecimal preServeFee;

	@Schema(description = "服务费金额")
	@JsonProperty(value = "serveFee")
	private String serveFee;

	@Schema(description = "宽度",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "width")
	private BigDecimal width;

	@Schema(description = "高度",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "height")
	private BigDecimal height;

	@Schema(description = "配送费百分比")
	private BigDecimal perDeliveryFee;

	@Schema(description = "商品上下架 enable-上架;not_enable-下架 默认值=enable",defaultValue = "enable")
	@JsonProperty(value = "isEnable",defaultValue = "enable")
	private String isEnable="enable";

	@Schema(description = "商品类型：common-普通商品;preferential-优惠商品;auction-拍卖商品;wholesale-批发商品 默认值=common",defaultValue = "common")
	@JsonProperty(value = "type",defaultValue = "common")
	private String type="common";

	@Schema(description = "拍卖信息")
	@JsonProperty(value = "auction")
	private ProductAuctionDto auction;

	@Schema(description = "批发信息")
	@JsonProperty(value = "wholesale")
	private ProductWholesaleDto wholesale;

	@Schema(description = "优惠信息")
	@JsonProperty(value = "preferential")
	private ProductPreferentialDto preferential;

	@Schema(description = "标注集合")
	@JsonProperty(value = "productMarkingList")
	private List<ProductMarkingDto>   productMarkingList;


	@Schema(description = "关键字集合")
	@JsonProperty(value = "productKeyWordsList")
	private List<ProductKeywordsDto>    productKeyWordsList;



	@Schema(description = "主规格集合")
	@JsonProperty(value = "productSpecificationList")
	private  List<ProductSpecificationDto>  ProductSpecificationList;


	@Schema(description = "子规格集合")
	@JsonProperty(value = "productSubSpecificationList")
	private  List<ProductSubSpecificationVo>  ProductSubSpecificationList;


	@Schema(description = "仓库id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "warehouseId")
	@Size(max = 32, message = "validation.size.max")
	private String warehouseId;

	@Schema(description = "仓库名称")
	@JsonProperty(value = "warehouseName")
	private String warehouseName;

	@Schema(description = "位子id")
	@JsonProperty(value = "warehouseSeatId")
	@Size(max = 32, message = "validation.size.max")
	private String warehouseSeatId;



	@Schema(description = "位子名称")
	@JsonProperty(value = "warehouseSeatName")
	@Size(max = 32, message = "validation.size.max")
	private String warehouseSeatName;



	@Schema(description = "商品清单集合")
	@JsonProperty(value = "productListVoList")
	@Size(max = 32, message = "validation.size.max")
	private List<ProductListVo> productListVoList;

	@Schema(description = "商品生产时间")
	@JsonProperty(value = "produceTime")
	private Long produceTime;

	@Schema(description = "商品过期时间")
	@JsonProperty(value = "overdueTime")
	private Long overdueTime;

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
	public String getDeliveryFee() {
	return deliveryFee;
	}

	/**
	* 设置配送费金额
	* @param deliveryFee 配送费金额
	*/
	public void setDeliveryFee(String deliveryFee) {
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
	public String getServeFee() {
	return serveFee;
	}

	/**
	* 设置服务费金额
	* @param serveFee 服务费金额
	*/
	public void setServeFee(String serveFee) {
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




	public ProductAuctionDto getAuction() {
		return auction;
	}

	public void setAuction(ProductAuctionDto auction) {
		this.auction = auction;
	}

	public ProductWholesaleDto getWholesale() {
		return wholesale;
	}

	public void setWholesale(ProductWholesaleDto wholesale) {
		this.wholesale = wholesale;
	}

	public ProductPreferentialDto getPreferential() {
		return preferential;
	}

	public void setPreferential(ProductPreferentialDto preferential) {
		this.preferential = preferential;
	}

	public List<ProductMarkingDto> getProductMarkingList() {
		return productMarkingList;
	}

	public List<ProductKeywordsDto> getProductKeyWordsList() {
		return productKeyWordsList;
	}

	public List<ProductSpecificationDto> getProductSpecificationList() {
		return ProductSpecificationList;
	}



	public void setProductMarkingList(List<ProductMarkingDto> productMarkingList) {
		this.productMarkingList = productMarkingList;
	}

	public void setProductKeyWordsList(List<ProductKeywordsDto> productKeyWordsList) {
		this.productKeyWordsList = productKeyWordsList;
	}

	public void setProductSpecificationList(List<ProductSpecificationDto> productSpecificationList) {
		ProductSpecificationList = productSpecificationList;
	}


	public String getWarehouseId() {
		return warehouseId;
	}

	public String getWarehouseSeatId() {
		return warehouseSeatId;
	}

	public void setWarehouseSeatId(String warehouseSeatId) {
		this.warehouseSeatId = warehouseSeatId;
	}


	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public List<ProductSubSpecificationVo> getProductSubSpecificationList() {

		return ProductSubSpecificationList;
	}

	public void setProductSubSpecificationList(List<ProductSubSpecificationVo> productSubSpecificationList) {
		ProductSubSpecificationList = productSubSpecificationList;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public String getWarehouseSeatName() {
		return warehouseSeatName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public void setWarehouseSeatName(String warehouseSeatName) {
		this.warehouseSeatName = warehouseSeatName;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public List<ProductListVo> getProductListVoList() {
		return productListVoList;
	}

	public void setProductListVoList(List<ProductListVo> productListVoList) {
		this.productListVoList = productListVoList;
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