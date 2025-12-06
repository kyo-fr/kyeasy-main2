package org.ares.cloud.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;

import java.math.BigDecimal;

/**
 * 优惠商品vo
 */
@TableName("product_base_info")
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends TenantEntity {

    //商品基础信息
    private String productName;

    private String id;


    private String productId;

    private BigDecimal price;

    private String pictureUrl;

    private String isEnable;

    private Integer inventory;

    private BigDecimal priceOne;

    private BigDecimal priceTwo;

    private BigDecimal priceThree;

    private String levelOneId;

    private String levelTwoId;

    private String levelThreeId;

    //拍卖商品
    //一口价
    private BigDecimal fixedPrice;

    private BigDecimal taxRate;

    private BigDecimal pricePre;

    public String getProductName() {
        return productName;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }


    public String getIsEnable() {
        return isEnable;
    }

    public Integer getInventory() {
        return inventory;
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

    public String getLevelOneId() {
        return levelOneId;
    }

    public String getLevelTwoId() {
        return levelTwoId;
    }

    public String getLevelThreeId() {
        return levelThreeId;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public BigDecimal getPricePre() {
        return pricePre;
    }

    public void setLevelThreeId(String levelThreeId) {
        this.levelThreeId = levelThreeId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
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

    public void setLevelOneId(String levelOneId) {
        this.levelOneId = levelOneId;
    }

    public void setLevelTwoId(String levelTwoId) {
        this.levelTwoId = levelTwoId;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public void setPricePre(BigDecimal pricePre) {
        this.pricePre = pricePre;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
