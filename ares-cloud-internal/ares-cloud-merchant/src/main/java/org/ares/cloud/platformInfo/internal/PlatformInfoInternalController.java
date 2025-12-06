package org.ares.cloud.platformInfo.internal;

import ch.qos.logback.classic.spi.PlatformInfo;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.exception.RpcCallException;
import org.ares.cloud.merchantInfo.entity.MerchantInfoEntity;
import org.ares.cloud.platformInfo.dto.PlatformTaxRateDto;
import org.ares.cloud.platformInfo.entity.PlatformInfoEntity;
import org.ares.cloud.platformInfo.service.PlatformInfoService;
import org.ares.cloud.platformInfo.service.PlatformTaxRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/platform/v1")
public class PlatformInfoInternalController {

    @Resource
    private PlatformTaxRateService platformTaxRateService;

    @Resource
    private PlatformInfoService platformInfoService;

    /**
     * 根据税率id查询税率信息
     * @param id 税率id
     * @return 税率信息
     */
    @Hidden
    @GetMapping("/tax/getTaxRateById")
    public PlatformTaxRateDto getTaxRateById(@RequestParam("id") String id) {
        return platformTaxRateService.loadById(id);
    }

    /**
     * 查询商户
     * @param id 主键
     * @return 商户信息
     */
    @Hidden
    @GetMapping("/findById")
    public MerchantInfo findById(@RequestParam String id) {
        try {
            PlatformInfoEntity platformInfo = platformInfoService.getById(id);
            if (platformInfo == null) {
                return null;
            }
            MerchantInfo res = new MerchantInfo();
            res.setId(platformInfo.getId());
            res.setCurrencyScale(2);
            // 假设还有其他字段需要赋值
            res.setName(platformInfo.getPlatformName());
            res.setAddress(platformInfo.getAddress());
            res.setPhone(platformInfo.getPlatformPhone());
            // 新增字段赋值
            res.setTaxNum(platformInfo.getTaxNum());
            res.setCountryCode(platformInfo.getCountry());
            res.setLanguage(platformInfo.getLanguage());
            res.setEnterpriseEmail(platformInfo.getEmail());
            if (platformInfo.getCurrency() == null) {
                res.setCurrency("EUR");
            } else {
                res.setCurrency(platformInfo.getCurrency());
            }
            res.setTaxNum(platformInfo.getTaxNum());
            return res;
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }
}
