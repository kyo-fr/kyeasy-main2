package org.ares.cloud.address.application.query;

import org.ares.cloud.address.domain.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 地址查询对象
 */
@Data
@Schema(description = "地址查询对象")
public class AddressQuery {
    
    @Schema(description = "地址类型")
    private AddressType type;
    
    @Schema(description = "是否默认地址")
    private Boolean isDefault;
    
    @Schema(description = "是否启用")
    private Boolean isEnabled;
    
    @Schema(description = "是否发票地址")
    private Boolean isInvoiceAddress;
} 