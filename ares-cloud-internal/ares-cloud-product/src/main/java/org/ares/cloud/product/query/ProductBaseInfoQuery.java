package org.ares.cloud.product.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

import java.util.Arrays;

/**
* @author hugo tangxkwork@163.com
* @description 商品基础信息 查询原型
* @version 1.0.0
* @date 2024-11-06
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商品基础信息查询")
public class ProductBaseInfoQuery extends Query {

    @Schema(description = "商品类型：common-普通商品;preferential-优惠商品;auction-拍卖商品;wholesale-批发商品 默认值=common",defaultValue = "common")
//    @NotNull(message = "{validation.product.productBaseInfo.type.notBlank}")
    String type;

    @Schema(description = "商户id")
//    @NotNull(message = "{validation.tenantId.notBlank}")
    String tenantId;

    @Schema(description = "商品分类等级，1:1级；2:2级；3:2级",defaultValue = "1")
    Integer levels;

    @Schema(description = "分类主键id")
    String typeId;

    //多种关键字id数组查询
    @Schema(description = "商品关键字数组",example = "1,2,3")
    private String[] keyWordsList;

    @Schema(description = "是否上下架:enable=上架；not_enable=下架")
    String isEnable;

//    @Schema(description = "一级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
//    private String levelOneId;
//
//    @Schema(description = "二级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
//    @JsonProperty(value = "levelTwoId")
//    private String levelTwoId;
//
//    @Schema(description = "三级分类id",requiredMode = Schema.RequiredMode.REQUIRED)
//    @JsonProperty(value = "levelThreeId")
//    private String levelThreeId;

    @Schema(description = "仓库id")
    String warehouseId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTenantId() {
        return tenantId;
    }

//    public String getLevelOneId() {
//        return levelOneId;
//    }
//
//    public String getLevelTwoId() {
//        return levelTwoId;
//    }
//
//    public String getLevelThreeId() {
//        return levelThreeId;
//    }
//
//    public void setTenantId(String tenantId) {
//        this.tenantId = tenantId;
//    }
//
//    public void setLevelOneId(String levelOneId) {
//        this.levelOneId = levelOneId;
//    }

//    public void setLevelTwoId(String levelTwoId) {
//        this.levelTwoId = levelTwoId;
//    }
//
//    public void setLevelThreeId(String levelThreeId) {
//        this.levelThreeId = levelThreeId;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String[] getKeyWordsList() {
        return keyWordsList;
    }

    public void setKeyWordsList(String[] keyWordsList) {
        this.keyWordsList = keyWordsList;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
}