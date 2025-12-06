package org.ares.cloud.application.service;

import lombok.RequiredArgsConstructor;
import org.ares.cloud.application.dto.PaymentOrderDTO;
import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.repository.PaymentOrderRepository;
import org.ares.cloud.infrastructure.payment.exception.PaymentErrorCode;
import org.ares.cloud.infrastructure.payment.exception.PaymentException;
import org.springframework.stereotype.Service;

/**
 * 支付查询应用服务
 */
@Service
@RequiredArgsConstructor
public class PaymentQueryService {

    private final PaymentOrderRepository paymentOrderRepository;
    
    /**
     * 查询支付订单
     *
     * @param merchantId 商户ID
     * @param orderNo 订单号
     * @return 支付订单DTO
     */
    public PaymentOrderDTO queryOrder(String merchantId, String orderNo) {
//        PaymentOrder order = paymentOrderRepository.findByOrderNo(orderNo)
//            .orElseThrow(() -> PaymentErrorCode.ORDER_NOT_FOUND.exception());
//
        // 验证商户ID
//        if (!merchantId.equals(order.getMerchantId())) {
//            throw PaymentErrorCode.MERCHANT_NOT_FOUND.exception();
//        }
//
//        return convertToDTO(order);
        return null;
    }
    
    /**
     * 转换为DTO
     */
    private PaymentOrderDTO convertToDTO(PaymentOrder order) {
        PaymentOrderDTO dto = new PaymentOrderDTO();
        dto.setOrderNo(order.getOrderNo());
        dto.setMerchantId(order.getMerchantId());
        dto.setChannel(order.getChannel());
        dto.setAmount(order.getAmount());
        dto.setSubject(order.getSubject());
        dto.setStatus(order.getStatus());
        dto.setPlatform(order.getPlatform());
        dto.setCreateTime(order.getCreateTime());
        return dto;
    }
} 