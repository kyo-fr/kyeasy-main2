package org.ares.cloud.businessId.internal;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.businessId.enums.BusinessIdError;
import org.ares.cloud.businessId.service.BusinessIdService;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.exception.RpcCallException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hugo
 * @version 1.0
 * @description: 业务id内部服务
 * @date 2024/10/13 13:27
 */
@RestController
@RequestMapping("/internal/base/v1/business_id")
public class BusinessIdInternalController {
    @Resource
    private BusinessIdService businessIdService;

    @Hidden
    @GetMapping("generate_random_business_id")
    public String generateRandomBusinessId(){
        try {
            return businessIdService.generateRandomBusinessId();
        } catch (Exception e) {
            throw new RpcCallException(new BusinessException(BusinessIdError.GENERATE_BUSINESS_ID_ERROR));
        }
    }

    /**
     * 生成业务id
     * @param modelName 业务模块名
     * @return 业务id
     */
    @Hidden
    @GetMapping("generate_business_id")
    public ResponseEntity<String> generateBusinessId(@RequestParam("modelName") String modelName){
        String s = businessIdService.generateBusinessId(modelName);
        return ResponseEntity.ok(s);
    }

    /**
     * 生成16位短雪花ID
     * 供内部服务调用，用于生成全局唯一的16位数字ID
     * 格式：秒级时间戳(10位) + 机器ID(2位) + 序列号(4位)
     * 
     * @return 16位雪花ID
     */
    @Hidden
    @GetMapping("generate_snowflake_id")
    public String generateSnowflakeId(){
        try {
            return businessIdService.generateSnowflakeId();
        } catch (Exception e) {
            throw new RpcCallException(new BusinessException(BusinessIdError.GENERATE_BUSINESS_ID_ERROR));
        }
    }
}
