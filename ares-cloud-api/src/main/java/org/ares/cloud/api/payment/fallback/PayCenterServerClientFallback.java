package org.ares.cloud.api.payment.fallback;

import org.ares.cloud.api.payment.PayCenterServerClient;
import org.ares.cloud.api.payment.command.GenericTransferCommand;
import org.ares.cloud.api.payment.command.MerchantDiscountCommand;
import org.ares.cloud.api.payment.command.MerchantPriceReductionCommand;
import org.ares.cloud.api.payment.dto.TransferResultDTO;
import org.ares.cloud.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: PayCenterServerClient 的降级处理
 * 当支付中心服务不可用时抛出 ServiceUnavailableException
 * @date 2025/9/15 00:46
 */
@Component
public class PayCenterServerClientFallback implements PayCenterServerClient {
    
    private static final String SERVICE_NAME = "ares-pay-center-service";
    
    @Override
    public TransferResultDTO genericTransfer(GenericTransferCommand command) {
        throw new ServiceUnavailableException(SERVICE_NAME, "genericTransfer");
    }
    
    @Override
    public TransferResultDTO merchantDiscount(MerchantDiscountCommand command) {
        throw new ServiceUnavailableException(SERVICE_NAME, "merchantDiscount");
    }
    
    @Override
    public TransferResultDTO merchantPriceReduction(MerchantPriceReductionCommand command) {
        throw new ServiceUnavailableException(SERVICE_NAME, "merchantPriceReduction");
    }
}
