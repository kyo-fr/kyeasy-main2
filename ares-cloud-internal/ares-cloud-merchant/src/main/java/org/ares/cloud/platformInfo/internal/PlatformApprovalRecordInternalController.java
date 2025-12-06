package org.ares.cloud.platformInfo.internal;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.platformInfo.dto.PlatformApprovalRecordDto;
import org.ares.cloud.platformInfo.service.PlatformApprovalRecordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/platform/approvalRecord/v1")
@Slf4j
public class PlatformApprovalRecordInternalController {

    @Resource
    private PlatformApprovalRecordService platformApprovalRecordService;



    /**
     * 存储变更对外变更接口
     * @param dto 平台审批记录DTO
     * @return 操作结果
     */
    @PostMapping("/updatePlatformApprovalRecord")
    public String updatePlatformApprovalRecord(@RequestBody PlatformApprovalRecordDto dto) {
        log.info("存储变更对外变更接口dto：{}", JSONObject.toJSONString(dto));
        platformApprovalRecordService.updatePlatformApprovalRecord(dto);
        return "success";
    }


    /**
     * 获取可用空间
     * @param tenantId 租户ID
     */
    @GetMapping(value = "/getAvailableStorage")
    public Long getAvailableStorage(@RequestParam String tenantId) {
        // TODO: 获取可用空间,先返回1g
        return 1024 * 1024 * 1024L;
       //return platformApprovalRecordService.getAvailableStorage(tenantId);
    }
}
