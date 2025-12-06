package org.ares.cloud.api.payment.fallback;

import org.ares.cloud.api.payment.PayBaseServerClient;
import org.ares.cloud.api.payment.command.CreateInvoiceCommand;
import org.ares.cloud.api.payment.command.RefundInvoiceFullCommand;
import org.ares.cloud.api.payment.command.VoidInvoiceCommand;
import org.ares.cloud.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: PayBaseServerClient 的降级处理
 * 当支付基础服务不可用时抛出 ServiceUnavailableException
 * @date 2025/4/14 15:33
 */
@Component
public class PayBaseServerClientFallback implements PayBaseServerClient {
    
    private static final String SERVICE_NAME = "ares-payment-base-service";
    
    @Override
    public String generateInvoice(CreateInvoiceCommand command) {
        throw new ServiceUnavailableException(SERVICE_NAME, "generateInvoice");
    }

    @Override
    public String voidInvoice(VoidInvoiceCommand command) {
        throw new ServiceUnavailableException(SERVICE_NAME, "voidInvoice");
    }

    @Override
    public String refundInvoice(RefundInvoiceFullCommand command) {
        throw new ServiceUnavailableException(SERVICE_NAME, "refundInvoice");
    }
}
