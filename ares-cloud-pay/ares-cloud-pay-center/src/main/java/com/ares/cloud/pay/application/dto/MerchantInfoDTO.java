package com.ares.cloud.pay.application.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 商户信息DTO
 * 封装商户的基本信息，用于在系统中传递商户的核心数据
 * <p>
 * 该DTO包含商户的标识信息、名称、类型和状态，用于商户信息的展示和传递
 * <p>
 * 使用场景：
 * 1. 商户信息展示
 * 2. 商户列表查询
 * 3. 商户信息传递
 */
@Data
@Schema(description = "商户信息对象", title = "商户信息")
public class MerchantInfoDTO {
    
    /**
     * 商户ID
     * <p>
     * 商户的唯一标识符，用于系统内部识别和管理商户。
     * 格式通常为字母merchant开头加上数字，确保全局唯一性。
     */
    @Schema(description = "商户ID", example = "merchant_123456")
    private String merchantId;
    
    /**
     * 商户名称
     * <p>
     * 商户的正式名称，用于显示和识别商户身份。
     * 建议使用商户的营业执照上的正式名称。
     */
    @Schema(description = "商户名称", example = "示例商户有限公司")
    private String merchantName;
    
    /**
     * 商户类型
     * <p>
     * 商户的类型分类，用于区分不同类型的商户。
     * 支持的类型包括：BUSINESS（企业商户）、INDIVIDUAL（个人商户）。
     */
    @Schema(description = "商户类型", example = "BUSINESS", allowableValues = {"BUSINESS", "INDIVIDUAL"})
    private String merchantType;
    
    /**
     * 商户状态
     * <p>
     * 商户的当前状态，用于标识商户是否可用。
     * 支持的状态包括：ACTIVE（激活）、FROZEN（冻结）、CLOSED（关闭）。
     */
    @Schema(description = "商户状态", example = "ACTIVE", allowableValues = {"ACTIVE", "FROZEN", "CLOSED"})
    private String status;
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private MerchantInfoDTO result = new MerchantInfoDTO();
        
        public Builder merchantId(String merchantId) {
            result.merchantId = merchantId;
            return this;
        }
        
        public Builder merchantName(String merchantName) {
            result.merchantName = merchantName;
            return this;
        }
        
        public Builder merchantType(String merchantType) {
            result.merchantType = merchantType;
            return this;
        }
        
        public Builder status(String status) {
            result.status = status;
            return this;
        }
        
        public MerchantInfoDTO build() {
            return result;
        }
    }
} 