package org.ares.cloud.application.service;

import lombok.RequiredArgsConstructor;
import org.ares.cloud.application.command.CreatePaymentCommand;
import org.ares.cloud.application.converter.PaymentOrderConverter;
import org.ares.cloud.application.dto.PaymentResponseDTO;
import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.service.PaymentDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支付应用服务
 */
@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    private final PaymentDomainService paymentDomainService;
    private final PaymentOrderConverter paymentOrderConverter;
    
    /**
     * 创建支付订单
     */
    @Transactional
    public PaymentResponseDTO createPayment(CreatePaymentCommand command) {
        // 转换命令到领域模型
        PaymentOrder order = paymentOrderConverter.toPaymentOrder(command);
        
        // 调用领域服务创建支付
        String paymentUrl = paymentDomainService.createPayment(order);
        
        // 构建响应
        return PaymentResponseDTO.builder()
            .orderNo(order.getOrderNo())
            .paymentUrl(paymentUrl)
            .status(order.getStatus().name())
            .build();
    }
} 