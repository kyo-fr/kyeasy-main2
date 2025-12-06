package org.ares.cloud.interfaces;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.infrastructure.payment.config.PaymentProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付结果页面控制器
 */
@Slf4j
@Controller
@RequestMapping("/payment/result")
@RequiredArgsConstructor
public class PaymentResultController {

    private final PaymentProperties paymentProperties;
    
    /**
     * 显示支付结果页面
     *
     * @param orderNo 订单号
     * @param status 支付状态
     * @param returnUrl 商户返回地址
     * @param model 模型
     * @return 页面模板
     */
    @GetMapping
    public String showResult(
        @RequestParam String orderNo,
        @RequestParam String status,
        @RequestParam String returnUrl,
        Model model
    ) {
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("status", status);
        model.addAttribute("returnUrl", returnUrl);
        
        String template = "SUCCESS".equals(status) 
            ? paymentProperties.getResultPage().getSuccessTemplate()
            : paymentProperties.getResultPage().getFailureTemplate();
            
        log.info("Show payment result page for order: {}, status: {}", orderNo, status);
        return template;
    }
} 