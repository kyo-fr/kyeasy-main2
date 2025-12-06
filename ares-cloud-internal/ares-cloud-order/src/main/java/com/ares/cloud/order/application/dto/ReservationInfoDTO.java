package com.ares.cloud.order.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 预订信息DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "预订信息DTO")
public class ReservationInfoDTO {
    
    @Schema(description = "预订ID")
    private String id;
    
    @Schema(description = "预订时间")
    private Long reservationTime;
    
    @Schema(description = "预订人姓名")
    private String reserverName;
    
    @Schema(description = "预订人电话")
    private String reserverPhone;
    
    @Schema(description = "就餐人数")
    private Integer diningNumber;
    
    @Schema(description = "包间要求")
    private String roomPreference;
    
    @Schema(description = "特殊餐饮要求")
    private String dietaryRequirements;
    
    @Schema(description = "预订备注")
    private String remarks;
} 