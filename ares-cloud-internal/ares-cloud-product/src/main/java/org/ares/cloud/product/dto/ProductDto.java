package org.ares.cloud.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;
import org.ares.cloud.common.serializer.CustomMoneySerializer;

import java.math.BigDecimal;

/**
 * 优惠商品vo
 */
@Data
public class ProductDto extends TenantDto {

    //商品基础信息
    @Schema(description = "商品名称")
    @JsonProperty(value = "productName")
    private String productName;

    @Schema(description = "id")
    @JsonProperty(value = "id")
    private String id;

    @Schema(description = "商品类型：common-普通商品;preferential-优惠商品;auction-拍卖商品;wholesale-批发商品 默认值=common",defaultValue = "common")
    @JsonProperty(value = "type",defaultValue = "common")
    private String type="common";

    @Schema(description = "商品id")
    @JsonProperty(value = "productId")
    private String productId;

    @Schema(description = "价格")
    @JsonProperty(value = "price")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal price;

    @Schema(description = "商品图片")
    @JsonProperty(value = "pictureUrl")
    private String pictureUrl;

    @Schema(description = "商品上下架 enable-上架;not_enable-下架 默认值=enable")
    @JsonProperty(value = "status")
    private String isEnable;

    @Schema(description = "库存")
    @JsonProperty(value = "inventory")
    private Integer inventory;

    //批发商品

    @Schema(description = "单价1")
    @JsonProperty(value = "priceOne")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal priceOne;

    @Schema(description = "单价2")
    @JsonProperty(value = "priceTwo")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal priceTwo;

    @Schema(description = "单价3")
    @JsonProperty(value = "priceThree")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal priceThree;

    @Schema(description = "一级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "levelOneId")
    private String levelOneId;

    @Schema(description = "二级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "levelTwoId")
    private String levelTwoId;

    @Schema(description = "三级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "levelThreeId")
    private String levelThreeId;

    //拍卖商品
    //一口价
    @Schema(description = "一口价")
    @JsonProperty(value = "fixedPrice")
    private BigDecimal fixedPrice;


    //优惠商品
    //优惠价格
    @Schema(description = "优惠价格")
    @JsonProperty(value = "pricePre")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal pricePre;

    @Schema(description = "税率")
    @JsonProperty(value = "taxRate")
    private BigDecimal taxRate;


    @Schema(description = "仓库名称")
    @JsonProperty(value = "warehouse")
    private String warehouseName;

    @Schema(description = "位子名称")
    @JsonProperty(value = "warehouseSeat")
    private String warehouseSeatName;

    @Schema(description = "商品简介",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "briefly")
    @Size(max = 2000, message = "validation.size.max")
    private String briefly;

    @Schema(description = "商品生产时间")
    @JsonProperty(value = "produceTime")
    private Long produceTime;

    @Schema(description = "商品过期时间")
    @JsonProperty(value = "overdueTime")
    private Long overdueTime;


    @Schema(description = "开始时间")
    @JsonProperty(value = "startTime")
    private Long startTime;


    @Schema(description = "结束时间")
    @JsonProperty(value = "endTime")
    private Long endTime;

    @Schema(description = "数量1")
    @JsonProperty(value = "numOne")
    private Integer numOne;

    @Schema(description = "数量2")
    @JsonProperty(value = "numTwo")
    private Integer numTwo;

    @Schema(description = "数量3")
    @JsonProperty(value = "numThree")
    private Integer numThree;


    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public BigDecimal getPricePre() {
        return pricePre;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPricePre(BigDecimal pricePre) {
        this.pricePre = pricePre;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductId() {
        return productId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public BigDecimal getPriceOne() {
        return priceOne;
    }

    public BigDecimal getPriceTwo() {
        return priceTwo;
    }

    public BigDecimal getPriceThree() {
        return priceThree;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setPriceOne(BigDecimal priceOne) {
        this.priceOne = priceOne;
    }

    public void setPriceTwo(BigDecimal priceTwo) {
        this.priceTwo = priceTwo;
    }

    public void setPriceThree(BigDecimal priceThree) {
        this.priceThree = priceThree;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getLevelOneId() {
        return levelOneId;
    }

    public String getLevelTwoId() {
        return levelTwoId;
    }

    public String getLevelThreeId() {
        return levelThreeId;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }



    public void setLevelOneId(String levelOneId) {
        this.levelOneId = levelOneId;
    }

    public void setLevelTwoId(String levelTwoId) {
        this.levelTwoId = levelTwoId;
    }

    public void setLevelThreeId(String levelThreeId) {
        this.levelThreeId = levelThreeId;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
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

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public Integer getNumOne() {
        return numOne;
    }

    public Integer getNumTwo() {
        return numTwo;
    }

    public Integer getNumThree() {
        return numThree;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setNumOne(Integer numOne) {
        this.numOne = numOne;
    }

    public void setNumTwo(Integer numTwo) {
        this.numTwo = numTwo;
    }

    public void setNumThree(Integer numThree) {
        this.numThree = numThree;
    }
}
