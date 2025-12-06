package org.ares.cloud.platformInfo.internal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.api.order.commod.PayCommand;
import org.ares.cloud.platformInfo.dto.PlatformApprovalSettlementDto;
import org.ares.cloud.platformInfo.entity.PlatformApprovalEntity;
import org.ares.cloud.platformInfo.repository.PlatformApprovalRepository;
import org.ares.cloud.platformInfo.service.PlatformApprovalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/platform/approval/v1")
public class PlatformApprovalInternalController {
    @Resource
    private PlatformApprovalService   platformApprovalService;
    @Resource
    private PlatformApprovalRepository platformApprovalRepository;



    /*
     * 存储变更对外变更接口
     */
    @PostMapping("/manualSettlementOrder")
    @Hidden
    public void create(String orderId) {
        //根据订单号查询审批信息
        PlatformApprovalEntity platformApprovalEntity = platformApprovalRepository.selectOne(new QueryWrapper<PlatformApprovalEntity>().eq("ORDER_ID", orderId));
        if (platformApprovalEntity != null){
            PlatformApprovalSettlementDto dto = new PlatformApprovalSettlementDto();
            dto.setApprovalId(platformApprovalEntity.getApprovalId());
            dto.setOrderId(orderId);
            dto.setUserId(platformApprovalEntity.getUserId());
            PayCommand payCommand = new PayCommand();
            payCommand.setChannelId(platformApprovalEntity.getPaymentChannelId());
            payCommand.setAmount(platformApprovalEntity.getTotalPrice());
            List<PayCommand> payCommands = List.of(payCommand);
            dto.setPayChannels(payCommands);
            platformApprovalService.manualSettlementOrder(dto);
        }
    }
}
