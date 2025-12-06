package org.ares.cloud.infrastructure.payment.factory;

import jakarta.annotation.PostConstruct;
import org.ares.cloud.domain.model.enums.PaymentChannel;
import org.ares.cloud.domain.service.PaymentChannelService;
import org.ares.cloud.infrastructure.payment.exception.PaymentException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentChannelFactory {
    private final List<PaymentChannelService> paymentChannelServices;
    private Map<PaymentChannel, PaymentChannelService> channelServiceMap;
    
    public PaymentChannelFactory(List<PaymentChannelService> paymentChannelServices) {
        this.paymentChannelServices = paymentChannelServices;
    }
    
    @PostConstruct
    public void init() {
        channelServiceMap = paymentChannelServices.stream()
            .collect(Collectors.toMap(
                PaymentChannelService::getChannelType,
                Function.identity()
            ));
    }
    
    public PaymentChannelService getPaymentChannel(PaymentChannel channel) {
        PaymentChannelService service = channelServiceMap.get(channel);
        if (service == null) {
            throw new PaymentException("Unsupported payment channel: " + channel);
        }
        return service;
    }
} 