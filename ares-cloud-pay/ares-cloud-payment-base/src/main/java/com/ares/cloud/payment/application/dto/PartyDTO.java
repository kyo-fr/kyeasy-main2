package com.ares.cloud.payment.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 交易方数据传输对象
 */
@Data
@Schema(description = "交易方数据传输对象")
public class PartyDTO {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private String id;
    
    /**
     * 交易方ID
     */
    @Schema(description = "交易方ID")
    private String partyId;
    
    /**
     * 关联发票ID
     */
    @Schema(description = "关联发票ID")
    private String invoiceId;
    
    /**
     * 交易方名称
     */
    @Schema(description = "交易方名称")
    private String name;
    
    /**
     * 交易方类型(1:商户,2:个人)
     */
    @Schema(description = "交易方类型(1:商户,2:个人)")
    private Integer partyType;
    
    /**
     * 用户状态(0:未注册,1:已注册)
     */
    @Schema(description = "用户状态(0:未注册,1:已注册)")
    private Integer userStatus;
    
    /**
     * 税号
     */
    @Schema(description = "税号")
    private String taxId;
    
    /**
     * 地址
     */
    @Schema(description = "地址")
    private String address;
    
    /**
     * 邮编
     */
    @Schema(description = "邮编")
    private String postalCode;
    
    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String phone;
    
    /**
     * 电子邮箱
     */
    @Schema(description = "电子邮箱")
    private String email;
    
    /**
     * 国家代码
     */
    @Schema(description = "国家代码")
    private String countryCode;
    
    /**
     * 国家
     */
    @Schema(description = "国家")
    private String country;
    
    /**
     * 城市
     */
    @Schema(description = "城市")
    private String city;
}