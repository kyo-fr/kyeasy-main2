package com.ares.cloud.gateway.web;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import com.ares.cloud.gateway.service.IPayPalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hugo
 * @version 1.0.0
 * @description TODO paypal支付
 * @date 2022/10/20 11:58
 */
@RestController()
@Tag(name ="Braintree支付中心")
@Slf4j
@RequestMapping("/api/ares/openApi/pay/paypal")
public class PayPalController {
    @Resource
    private IPayPalService service;
    /**
     * 支付回调
     */
    @PostMapping("/callback")
    public void callback(@RequestBody JSONObject data) {
       service.callback(data);
    }
}
