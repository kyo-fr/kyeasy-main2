package org.ares.cloud.address.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ares.cloud.address.domain.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 地址命令对象
 */
@Data
@Schema(description = "地址命令对象")
public class AddressCommand {

    @Schema(description = "地址类型: 1->个人地址；2->公司地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "地址类型不能为空")
    private AddressType type;
    
    @Schema(description = "姓名")
    private String name;
    
    @Schema(description = "联系电话")
    private String phone;
    
    @Schema(description = "公司名称（公司地址必填）")
    private String companyName;
    
    @Schema(description = "企业营业号")
    private String businessLicenseNumber;
    
    @Schema(description = "国家")
    private String country;
    
    @Schema(description = "城市", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "城市不能为空")
    private String city;
    
    @Schema(description = "邮编")
    private String postalCode;
    
    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细地址不能为空")
    private String detail;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "纬度不能为空")
    private Double latitude;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能为空")
    private Double longitude;
    
    @Schema(description = "是否默认地址")
    private Boolean isDefault = false;
    
    @Schema(description = "是否启用")
    private Boolean isEnabled = true;

    @Schema(description = "是否发票地址")
    private Boolean isInvoiceAddress = false;
}