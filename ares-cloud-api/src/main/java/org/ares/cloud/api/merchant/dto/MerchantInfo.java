package org.ares.cloud.api.merchant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/4/15 15:38
 */
@Data
public class MerchantInfo {
    /**
     * id
     */
    @Schema(description = "主键")
    @JsonProperty(value = "id")
    protected String id;
    @Schema(description = "用户id",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "userId")
    private String userId;

    @Schema(description = "商户注册手机号",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "registerPhone")
    private String registerPhone;


    @Schema(description = "企业名称",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "name")
    private String name;

    @Schema(description = "企业域名")
    @JsonProperty(value = "domainName")
    private String domainName;


    @Schema(description = "税务号(企业编号)")
    @JsonProperty(value = "taxNum")
    private String taxNum;

    @Schema(description = "商户合同id",hidden = true)
    @JsonProperty(value = "contractId")
    private String contractId;


    @Schema(description = "企业电话")
    @JsonProperty(value = "phone")
    private String phone;


    @Schema(description = "企业邮箱")
    @JsonProperty(value = "enterpriseEmail")
    private String enterpriseEmail;


    @Schema(description = "企业iBan")
    @JsonProperty(value = "iBan")
    private String iBan;

    @Schema(description = "企业BIC")
    @JsonProperty(value = "bic")
    private String bic;

    @Schema(description = "商户所在国家：示例值： +86")
    @JsonProperty(value = "countryCode")
    private String countryCode;

    @Schema(description = "网站语言")
    @JsonProperty(value = "language")
    private String language;


    @Schema(description = "收款货币")
    @JsonProperty(value = "currency")
    private String currency;

//    @Schema(description = "货币精度")
//    @JsonProperty(value = "scale")
//    private Integer scale;

//    @Schema(description = "税号")
//    @JsonProperty(value = "taxId")
//    private String taxId;

    @Schema(description = "邮编")
    @JsonProperty(value = "postalCode")
    private String postalCode;

//    @Schema(description = "电子邮箱")
//    @JsonProperty(value = "email")
//    private String email;

    @Schema(description = "币种精度")
    @JsonProperty(value = "currencyScale")
    private Integer currencyScale;

    @Schema(description = "是否开启礼物点 1:是 ; 2:否")
    @JsonProperty(value = "isOpenGift")
    private int isOpenGift;


    @Schema(description = "企业地址")
    @JsonProperty(value = "address")
    private String address;

    @Schema(description = "商户状态 1-注册中；2-正常；3-冻结",defaultValue = "1")
    @JsonProperty(value = "status")
    private Integer status;

    @Schema(description = "国家(地址)",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "country")
    private String country;

    @Schema(description = "城市",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "city")
    private String city;

}
