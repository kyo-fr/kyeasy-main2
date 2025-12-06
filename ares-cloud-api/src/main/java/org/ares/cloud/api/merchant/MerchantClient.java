package org.ares.cloud.api.merchant;

import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.api.merchant.dto.PlatformApprovalRecordDto;
import org.ares.cloud.api.merchant.dto.PlatformTaxRateDto;
import org.ares.cloud.api.merchant.fallback.MerchantClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "merchant-service",configuration = FeignConfig.class,fallback = MerchantClientFallback.class)
public interface MerchantClient {

    /**
     * 根据税率id查询税率数据
     * @param id 税率id
     * @return 税率数据
     */
    @GetMapping("/internal/platform/v1/tax/getTaxRateById")
    PlatformTaxRateDto getTaxRateById(@RequestParam String id);

    /**
     * 根据商户id查询商户信息
     * @param id 商户id
     * @return 商户信息
     */
    @GetMapping("/internal/merchant/v1/findById")
    MerchantInfo getMerchantInfoById(@RequestParam String id);

    /**
     * 根据id查询平台信息
     * @param id 平台id
     * @return 平台信息
     */
    @GetMapping("/internal/platform/v1/findById")
    MerchantInfo getPlatformInfoById(@RequestParam String id);
    
    /**
     * 根据商户用户id查询商户信息
     * @param userId 用户id
     * @return 商户信息
     */
    @GetMapping("/internal/merchant/v1/findByUserId")
    MerchantInfo getMerchantInfoByUserId(@RequestParam("userId") String userId);
    
    /**
     * 根据商户域名查询商户信息
     * @param domain 域名
     * @return 商户信息
     */
    @GetMapping("/internal/merchant/v1/findByDomain")
    MerchantInfo getMerchantInfoByDomain(@RequestParam("domain") String domain);

    /**
     * 商户存储消耗变更
     * @param dto 记录DTO
     * @return 操作结果
     */
    @PostMapping("/internal/platform/approvalRecord/v1/updatePlatformApprovalRecord")
    String updatePlatformApprovalRecord(@RequestBody PlatformApprovalRecordDto dto);

    /**
     * 根据商户名称查询商户信息
     * @param name 商户名称
     * @return 商户信息列表
     */
    @GetMapping("/internal/merchant/v1/findByName")
    List<MerchantInfo> getMerchantInfoByName(@RequestParam String name);

    /**
     * 获取商户存储配额信息
     * @param tenantId 租户ID
     * @return 存储配额信息（KB）
     */
    @GetMapping("/internal/platform/approvalRecord/v1/getAvailableStorage")
    Long getAvailableStorage(@RequestParam String tenantId);
}
