package org.ares.cloud.address.application.dto;

import org.ares.cloud.address.domain.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 地址DTO对象
 */
@Data
@Schema(description = "地址DTO对象")
public class AddressDTO {
    
    @Schema(description = "地址ID")
    private String id;
    
    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "地址类型: 1->个人地址；2->公司地址")
    private AddressType type;
    
    @Schema(description = "姓名")
    private String name;
    
    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "公司名称")
    private String companyName;
    
    @Schema(description = "企业营业号")
    private String businessLicenseNumber;
    
    @Schema(description = "国家")
    private String country;
    
    @Schema(description = "城市")
    private String city;
    
    @Schema(description = "邮编")
    private String postalCode;
    
    @Schema(description = "详细地址")
    private String detail;

    @Schema(description = "纬度")
    private Double latitude;

    @Schema(description = "经度")
    private Double longitude;
    
    @Schema(description = "是否默认地址")
    private Boolean isDefault;
    
    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "是否发票地址")
    private Boolean isInvoiceAddress;
    
    @Schema(description = "创建时间")
    private Long createTime;
    
    @Schema(description = "更新时间")
    private Long updateTime;
}