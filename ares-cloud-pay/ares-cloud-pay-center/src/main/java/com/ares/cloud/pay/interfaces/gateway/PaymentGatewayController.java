package com.ares.cloud.pay.interfaces.gateway;

import com.ares.cloud.pay.application.handlers.PaymentGatewayHandler;
import com.ares.cloud.pay.application.dto.PaymentOrderInfoDTO;
import com.ares.cloud.pay.application.dto.PaymentStatusDTO;
import com.ares.cloud.pay.domain.enums.PaymentError;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.common.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付网关控制器
 * 处理支付票据验证和二维码生成
 */
@Controller
public class PaymentGatewayController {
    
    @Resource
    private PaymentGatewayHandler paymentGatewayHandler;
    
    /**
     * 支付页面
     * 通过票据获取订单信息并显示支付页面
     */
    @Hidden
    @GetMapping("/pay")
    public String payPage(@RequestParam("ticket") String ticket, Model model) {
        try {
            // 通过应用层处理器验证票据并获取订单信息
            PaymentOrderInfoDTO orderInfo = paymentGatewayHandler.validateTicketAndGetOrderInfo(ticket);
            
            // 生成支付二维码内容
            String qrCodeContent = paymentGatewayHandler.generatePaymentQRCode(orderInfo.getOrderNo());
            
            // 将订单信息添加到模型中
            model.addAttribute("orderInfo", orderInfo);
            model.addAttribute("ticket", ticket);
            model.addAttribute("paymentUrl", "/pay?ticket=" + ticket);
            model.addAttribute("qrCodeContent", qrCodeContent);
            
            // 返回支付页面模板
            return "pay";
        } catch (Exception e) {
            // 将错误信息添加到模型中
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorCode", e instanceof BusinessException ? ((BusinessException) e).getCode() : "SYSTEM_ERROR");
            return "error";
        }
    }
    
    /**
     * 扫描支付页面
     * 通过票据获取订单信息并生成二维码
     */
    @Hidden
    @GetMapping("/qrcode")
    public String qrcodePage(@RequestParam("ticket") String ticket, Model model) {
        try {
            // 通过应用层处理器验证票据并获取订单信息
            PaymentOrderInfoDTO orderInfo = paymentGatewayHandler.validateTicketAndGetOrderInfo(ticket);
            
            // 生成支付二维码内容
            String qrCodeContent = paymentGatewayHandler.generatePaymentQRCode(orderInfo.getOrderNo());
            
            // 将订单信息添加到模型中
            model.addAttribute("orderInfo", orderInfo);
            model.addAttribute("ticket", ticket);
            model.addAttribute("qrCodeUrl", "/qrcode?ticket=" + ticket);
            model.addAttribute("qrCodeContent", qrCodeContent);
            
            // 返回扫描支付页面模板
            return "qrcode";
        } catch (Exception e) {
            // 将错误信息添加到模型中
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorCode", e instanceof BusinessException ? ((BusinessException) e).getCode() : "SYSTEM_ERROR");
            return "error";
        }
    }
    
    /**
     * 支付成功页面
     * 显示支付成功信息，支持跳转到商户页面
     */
    @Hidden
    @GetMapping("/success")
    public String successPage(@RequestParam(value = "orderNo", required = false) String orderNo,
                             @RequestParam(value = "returnUrl", required = false) String returnUrl,
                             Model model) {
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("returnUrl", returnUrl);
        return "success";
    }
    
    /**
     * 查询支付订单状态API
     * 供支付页面轮询查询订单是否完成付款
     */
    @Hidden
    @GetMapping("/api/payment/status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPaymentStatus(@RequestParam("orderNo") String orderNo) {
        try {
            // 通过应用层处理器查询支付订单状态
            PaymentStatusDTO statusInfo = paymentGatewayHandler.queryPaymentStatus(orderNo);
            
            // 构建返回结果
            Map<String, Object> result = new java.util.HashMap<>();
            result.put("success", true);
            result.put("orderNo", statusInfo.getOrderNo());
            result.put("merchantOrderNo", statusInfo.getMerchantOrderNo());
            result.put("status", statusInfo.getStatus());
            result.put("amount", statusInfo.getAmount());
            result.put("currency", statusInfo.getCurrency());
            result.put("payTime", statusInfo.getPayTime());
            result.put("returnUrl", statusInfo.getReturnUrl());
            result.put("subject", statusInfo.getSubject());
            result.put("description", statusInfo.getDescription());
            
            return ResponseEntity.ok(result);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage(),
                "code", e.getCode()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "查询支付状态失败",
                "code", PaymentError.SYSTEM_ERROR.getCode()
            ));
        }
    }

} 