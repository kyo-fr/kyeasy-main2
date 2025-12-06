package com.ares.cloud.pay.application.handlers;

import com.ares.cloud.pay.application.command.CreateOrderCommand;
import com.ares.cloud.pay.application.command.QueryPaymentOrderCommand;
import com.ares.cloud.pay.application.command.RefundCommand;
import com.ares.cloud.pay.application.dto.CreatePaymentOrderResponseDTO;
import com.ares.cloud.pay.application.dto.QueryPaymentOrderResponseDTO;
import com.ares.cloud.pay.application.dto.RefundPaymentResponseDTO;
import com.ares.cloud.pay.domain.command.CreatePaymentOrderCommand;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.domain.service.PaymentDomainService;
import com.ares.cloud.pay.domain.valueobject.PaymentCreateResult;
import com.ares.cloud.pay.domain.valueobject.PaymentQueryResult;
import com.ares.cloud.pay.domain.valueobject.RefundResult;
import jakarta.annotation.Resource;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 支付网关命令处理器
 * 处理支付订单相关的命令
 */
@Component
public class PaymentGatewayCommandHandler {
    
    @Resource
    private PaymentDomainService paymentDomainService;
    
    /**
     * 创建支付订单
     *
     * @param command 创建支付订单命令
     * @return 支付订单创建结果
     */
    public CreatePaymentOrderResponseDTO createPaymentOrder(CreateOrderCommand command) {
        // 参数校验
        if (command == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        CreatePaymentOrderCommand dc = CreatePaymentOrderCommand.create(
                command.getMerchantId(),
                command.getMerchantOrderNo(),
                Money.create(command.getAmount(),command.getPaymentRegion()),
                command.getPaymentRegion(),
                command.getSubject(),
                command.getDescription(),
                command.getReturnUrl(),
                command.getNotifyUrl(),
                command.getExpireTime(),
                command.getSign()
        );
        if (!StringUtils.hasText(command.getExt1())){
            dc.addCustomParam("ext1", command.getExt1());
        }
        if (!StringUtils.hasText(command.getExt2())){
            dc.addCustomParam("ext2", command.getExt2());
        }
        // 调用领域服务创建支付订单
        PaymentCreateResult result = paymentDomainService.createPaymentOrder(dc);
        
        // 转换为响应DTO
        CreatePaymentOrderResponseDTO responseDTO = new CreatePaymentOrderResponseDTO();
        responseDTO.setOrderNo(result.getOrderNo());
        responseDTO.setMerchantOrderNo(result.getMerchantOrderNo());
        responseDTO.setAmount(Money.create(result.getAmount(), result.getCurrency()));
        responseDTO.setPaymentRegion(result.getCurrency());
        responseDTO.setSubject(command.getSubject());
        responseDTO.setDescription(command.getDescription());
        responseDTO.setPayUrl(result.getPaymentUrl());
        responseDTO.setQrcodeUrl(result.getQrCodeUrl());
        responseDTO.setExpireTime(result.getExpireTime());
        responseDTO.setSign(result.getSign());
        
        return responseDTO;
    }
    
    /**
     * 查询支付订单状态
     *
     * @param command 查询支付订单命令
     * @return 支付订单查询结果
     */
    public QueryPaymentOrderResponseDTO queryPaymentOrder(QueryPaymentOrderCommand command) {
        // 参数校验
        if (command == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 调用领域服务查询支付订单
        PaymentQueryResult result = paymentDomainService.queryPaymentOrder(
                command.getMerchantId(), 
                command.getOrderNo(), 
                command.getSign()
        );
        
        // 转换为响应DTO
        QueryPaymentOrderResponseDTO responseDTO = new QueryPaymentOrderResponseDTO();
        responseDTO.setOrderNo(result.getOrderNo());
        responseDTO.setMerchantOrderNo(result.getMerchantOrderNo());
        responseDTO.setStatus(result.getStatus());
        responseDTO.setAmount(Money.create(result.getAmount(), result.getCurrency()));
        responseDTO.setCurrency(result.getCurrency());
        responseDTO.setPayTime(result.getPayTime());
        responseDTO.setSign(result.getSign());
        
        return responseDTO;
    }
    
    /**
     * 退款
     *
     * @param command 退款命令
     * @return 退款结果
     */
    public RefundPaymentResponseDTO refund(RefundCommand command) {
        // 参数校验
        if (command == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 调用领域服务退款
        RefundResult result = paymentDomainService.refund(
                command.getMerchantId(), 
                command.getOrderNo(), 
                command.getRefundAmount(), 
                command.getReason(), 
                command.getSign()
        );
        
        // 转换为响应DTO
        RefundPaymentResponseDTO responseDTO = new RefundPaymentResponseDTO();
        responseDTO.setOrderNo(result.getOrderNo());
        responseDTO.setMerchantOrderNo(result.getMerchantOrderNo());
        responseDTO.setRefundAmount(Money.create(result.getRefundAmount(), result.getCurrency()));
        responseDTO.setCurrency(result.getCurrency());
        responseDTO.setStatus(result.getStatus());
        responseDTO.setReason(result.getReason());
        responseDTO.setSign(result.getSign());
        
        return responseDTO;
    }

} 