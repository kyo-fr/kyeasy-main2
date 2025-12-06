package org.ares.cloud.api.payment;

import org.ares.cloud.api.payment.command.GenericTransferCommand;
import org.ares.cloud.api.payment.command.MerchantDiscountCommand;
import org.ares.cloud.api.payment.command.MerchantPriceReductionCommand;
import org.ares.cloud.api.payment.dto.TransferResultDTO;
import org.ares.cloud.api.payment.fallback.PayCenterServerClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hugo
 * @version 1.0
 * @description: 支付中心客户端
 * @date 2024/10/7 23:30
 */
@FeignClient(name = "ares-pay-center-service",configuration = FeignConfig.class,fallback = PayCenterServerClientFallback.class)
public interface PayCenterServerClient {
    /**
     * 通用转账
     * @param command 转账命令
     * @return 转账结果
     */
    @PostMapping("/internal/v1/transfer/generic")
    TransferResultDTO genericTransfer(@RequestBody GenericTransferCommand command);
    
    /**
     * 商户优惠减免
     * 商户结算时给用户的百分比优惠减免，手续费5%由用户承担
     * 
     * @param command 优惠减免命令
     * @return 转账结果
     */
    @PostMapping("/internal/v1/transfer/merchant-discount")
    TransferResultDTO merchantDiscount(@RequestBody MerchantDiscountCommand command);
    
    /**
     * 商户减价
     * 商户结算时给用户的具体金额减价，手续费5%由用户承担
     * 
     * @param command 减价命令
     * @return 转账结果
     */
    @PostMapping("/internal/v1/transfer/merchant-price-reduction")
    TransferResultDTO merchantPriceReduction(@RequestBody MerchantPriceReductionCommand command);
}
