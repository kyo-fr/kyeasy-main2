package org.ares.cloud.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/payment/page")
@RequiredArgsConstructor
public class PaymentPageController {
    
    @GetMapping("/h5")
    public String h5PaymentPage(
            @RequestParam String token,
            @RequestParam String orderId,
            @RequestParam String amount,
            @RequestParam String returnUrl,
            Model model) {
        model.addAttribute("clientToken", token);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("returnUrl", returnUrl);
        return "payment/braintree-h5";
    }
    
    @GetMapping("/pc")
    public String pcPaymentPage(
            @RequestParam String token,
            @RequestParam String orderId,
            @RequestParam String amount,
            @RequestParam String returnUrl,
            Model model) {
        model.addAttribute("clientToken", token);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("returnUrl", returnUrl);
        return "payment/braintree-pc";
    }
} 