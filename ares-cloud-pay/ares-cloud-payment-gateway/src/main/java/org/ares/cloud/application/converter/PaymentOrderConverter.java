package org.ares.cloud.application.converter;

import org.ares.cloud.application.command.CreatePaymentCommand;
import org.ares.cloud.common.utils.IdUtils;
import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.model.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 支付订单转换器
 */
@Component
public class PaymentOrderConverter {
    
    /**
     * 命令转领域模型
     */
    public PaymentOrder toPaymentOrder(CreatePaymentCommand command) {
        return PaymentOrder.builder()
            .orderNo(IdUtils.getOrderNo())
            .merchantId(command.getMerchantId())
            .channel(command.getChannel())
            .amount(command.getAmount())
            .subject(command.getSubject())
            .status(PaymentStatus.PENDING)
            .platform(command.getPlatform())
            .returnUrl(command.getReturnUrl())
            .notifyUrl(command.getNotifyUrl())
            .expireTime(LocalDateTime.now().plusMinutes(command.getExpireMinutes()))
            .createTime(LocalDateTime.now())
            .build();
    }
} 