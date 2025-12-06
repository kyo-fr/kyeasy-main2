package com.ares.cloud.pay.application.handlers;

import com.ares.cloud.pay.application.command.ScanCodePaymentCommand;
import com.ares.cloud.pay.application.command.TestPaymentCommand;
import com.ares.cloud.pay.application.dto.PaymentOrderInfoDTO;
import com.ares.cloud.pay.application.dto.PaymentStatusDTO;
import com.ares.cloud.pay.domain.command.CreatePaymentOrderCommand;
import com.ares.cloud.pay.domain.constant.PaymentConstants;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.domain.model.PaymentOrder;
import com.ares.cloud.pay.domain.repository.PaymentOrderRepository;
import com.ares.cloud.pay.domain.service.PaymentDomainService;
import com.ares.cloud.pay.domain.valueobject.PaymentCreateResult;
import com.ares.cloud.pay.domain.valueobject.PaymentExecuteResult;
import jakarta.annotation.Resource;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.IdUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * 支付网关处理器
 * 处理支付票据验证、支付执行等网关相关操作
 */
@Component
public class PaymentGatewayHandler {
    
    @Resource
    private PaymentDomainService paymentDomainService;
    
    @Resource
    private PaymentOrderRepository paymentOrderRepository;
    
    
    /**
     * 验证票据并获取订单信息
     *
     * @param ticket 支付票据
     * @return 订单信息DTO
     */
    public PaymentOrderInfoDTO validateTicketAndGetOrderInfo(String ticket) {
        // 验证票据
        if (!paymentDomainService.validatePaymentTicket(ticket)) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 根据支付票据获取订单信息
        PaymentOrder paymentOrder = paymentDomainService.getOrderByPaymentTicket(ticket);
        
        // 转换为DTO
        PaymentOrderInfoDTO dto = new PaymentOrderInfoDTO();
        dto.setOrderNo(paymentOrder.getOrderNo());
        dto.setMerchantOrderNo(paymentOrder.getMerchantOrderNo());
        dto.setMerchantId(paymentOrder.getMerchantId());
        dto.setAmount(paymentOrder.getAmount().toDecimal());
        dto.setCurrency(paymentOrder.getAmount().getCurrency());
        dto.setPaymentRegion(paymentOrder.getPaymentRegion());
        dto.setSubject(paymentOrder.getSubject());
        dto.setDescription(paymentOrder.getDescription());
        dto.setReturnUrl(paymentOrder.getReturnUrl());
        dto.setNotifyUrl(paymentOrder.getNotifyUrl());
        dto.setExpireTime(paymentOrder.getExpireTime());
        dto.setStatus(paymentOrder.getStatus().getCode());
        dto.setCreateTime(paymentOrder.getCreateTime());
        dto.setCustomParams(paymentOrder.getCustomParams());
        
        return dto;
    }
    
    /**
     * 生成支付二维码
     *
     * @param orderNo 订单号
     * @return 二维码内容字符串
     */
    public String generatePaymentQRCode(String orderNo) {
        return paymentDomainService.generatePaymentQRCode(orderNo);
    }
    
    /**
     * 二维码支付
     *
     * @param command 支付命令
     * @return 支付结果
     */
    public PaymentExecuteResult scanQRCodePayment(String userId, ScanCodePaymentCommand command) {
        return paymentDomainService.scanQRCodePayment(command.getQrCodeContent(), userId, command.getPaymentPassword());
    }
    
    /**
     * 创建测试支付订单
     * 使用平台商户信息创建测试支付订单
     *
     * @param command 测试支付命令
     * @return 支付订单创建结果
     */
    public PaymentCreateResult createTestPayment(TestPaymentCommand command) {
        // 参数校验
        if (command == null || command.getAmount() == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 生成商户订单号
        String merchantOrderNo = "TEST_" + System.currentTimeMillis() + "_" + IdUtils.fastSimpleUUID().substring(0, 8);
        
        // 转换金额（从元转换为分）
        BigDecimal amountInYuan = command.getAmount();
        Long amountInCents = amountInYuan.multiply(new BigDecimal("100")).longValue();
        
        // 创建Money对象
        Money amount = Money.create(amountInCents, command.getPaymentRegion(), Money.DEFAULT_SCALE);
        
        // 设置过期时间（30分钟后）
        Long expireTime = System.currentTimeMillis() + 30 * 60 * 1000;

        
        // 创建支付订单命令
        CreatePaymentOrderCommand createCommand = CreatePaymentOrderCommand.create(
            PaymentConstants.PLATFORM_MERCHANT_ID,
            merchantOrderNo,
            amount,
            command.getPaymentRegion(),
            command.getSubject(),
            command.getDescription(),
            command.getReturnUrl(),
            command.getNotifyUrl(),
            expireTime,
            ""
        );
        // 添加测试标记
        createCommand.addCustomParam("test", "true");
        createCommand.addCustomParam("testTime", String.valueOf(System.currentTimeMillis()));
        String s = paymentDomainService.generateCreateOrderSign(createCommand);
        createCommand.addSign(s);

        
        // 调用领域服务创建支付订单
        return paymentDomainService.createPaymentOrder(createCommand);
    }
    
    /**
     * 查询支付状态
     *
     * @param orderNo 订单号
     * @return 支付状态DTO
     */
    public PaymentStatusDTO queryPaymentStatus(String orderNo) {
        // 参数校验
        if (!StringUtils.hasText(orderNo)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 查询支付订单
        PaymentOrder order = paymentOrderRepository.findByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 转换为DTO
        PaymentStatusDTO dto = new PaymentStatusDTO();
        dto.setOrderNo(order.getOrderNo());
        dto.setMerchantOrderNo(order.getMerchantOrderNo());
        dto.setStatus(order.getStatus().getCode());
        dto.setAmount(order.getAmount());
        dto.setCurrency(order.getAmount() != null ? order.getAmount().getCurrency() : null);
        dto.setPayTime(order.getPayTime());
        dto.setReturnUrl(order.getReturnUrl());
        dto.setSubject(order.getSubject());
        dto.setDescription(order.getDescription());
        
        return dto;
    }

} 