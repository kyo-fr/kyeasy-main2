package com.ares.cloud.order.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "修改预订信息命令")
public class UpdateReservationCommand {
    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "订单ID不能为空")
    private String orderId;

    @Schema(description = "预订时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预订时间不能为空")
    private Long reservationTime;

    @Schema(description = "预订人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "预订人姓名不能为空")
    private String reserverName;

    @Schema(description = "预订人电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "预订人电话不能为空")
    private String reserverPhone;

    @Schema(description = "预定人数")
    @NotNull(message = "预定人数不能为空")
    private Integer diningNumber;

    @Schema(description = "预订备注")
    private String remarks;
} 