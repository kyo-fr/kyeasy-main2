//package org.ares.cloud.platformInfo.internal;
//
//import io.swagger.v3.oas.annotations.Hidden;
//import jakarta.annotation.Resource;
//import org.ares.cloud.merchantInfo.dto.MerchantInfoDto;
//import org.ares.cloud.merchantInfo.service.MerchantInfoService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping("/internal/merchant/v1")
//public class MerchantInfoInternalController {
//
//    @Resource
//    private MerchantInfoService merchantInfoService;
//
//    /**
//     * 根据id获取商户信息
//     *
//     * @param id
//     * @return
//     */
//    @Hidden
//    @GetMapping("/findById")
//    public ResponseEntity<MerchantInfoDto> getMerchantInfoById(@RequestParam("id") String id) {
//        return ResponseEntity.ok(merchantInfoService.getMerchantInfoById(id));
//    }
//
//}
