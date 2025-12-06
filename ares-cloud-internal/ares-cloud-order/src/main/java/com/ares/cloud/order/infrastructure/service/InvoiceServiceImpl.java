package com.ares.cloud.order.infrastructure.service;


import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.model.command.PayCommand;
import com.ares.cloud.order.domain.model.entity.OrderItem;
import com.ares.cloud.order.domain.model.valueobject.PayItem;
import com.ares.cloud.order.domain.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.api.payment.PayBaseServerClient;
import org.ares.cloud.api.payment.PayCenterServerClient;
import org.ares.cloud.api.payment.command.CreateInvoiceCommand;
import org.ares.cloud.api.payment.command.InvoiceItemCommand;
import org.ares.cloud.api.payment.command.PartyCommand;
import org.ares.cloud.api.payment.command.PayItemCommand;
import org.ares.cloud.api.user.UserServerClient;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 发票服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    /**
     * 支付服务
     */
    private final PayBaseServerClient payBaseServerClient;
    /**
     * 用户服务
     */
    private final UserServerClient userServerClient;
    /**
     * 商户服务
     */
    private final MerchantClient merchantClient;
    /**
     * 会员服务
     */
    private final PayCenterServerClient memberServerClient;

    @Override
    public void generateInvoice(Order order, PayCommand command) {
        log.info("开始生成订单发票，订单ID：{}，支付命令：{}", order.getId(), command);


        // 2. 构建发票命令
        CreateInvoiceCommand invoiceCommand = buildInvoiceCommand(order, command);

        // 3. 调用发票服务生成发票
        try {
            String invoiceId = payBaseServerClient.generateInvoice(invoiceCommand);
            if (invoiceId == null || invoiceId.isEmpty()) {
                log.error("发票生成失败，订单ID：{}", order.getId());
                throw new RuntimeException("发票生成失败");
            }
            log.info("发票生成成功，订单ID：{}，发票ID：{}", order.getId(), invoiceId);
        } catch (Exception e) {
            log.error("发票生成失败，订单ID：{}，错误信息：{}", order.getId(), e.getMessage());
            throw new RuntimeException("发票生成失败", e);
        }
    }

    /**
     * 构建发票命令
     */
    private CreateInvoiceCommand buildInvoiceCommand(Order order, PayCommand command) {
        // 获取需要生成发票的订单项
        List<OrderItem> invoiceOrderItems = getInvoiceOrderItems(order, command);

        // 计算发票金额
        Money totalAmount = calculateTotalAmount(invoiceOrderItems,order);
        Money actualAmount = totalAmount.subtract(command.getDeductAmount());
        // 构建付款方命令
        PartyCommand payer = buildPayerPartyCommand(command);
        // 构建收款方命令
        PartyCommand payee = buildPayeePartyCommand(order,command);
        // 构建发票命令
        return CreateInvoiceCommand.builder()
                .merchantId(order.getMerchantId())
                .orderId(order.getId())
                .payer(payer)
                .payee(payee)
                .items(buildInvoiceItems(invoiceOrderItems))
                .preTaxAmount(totalAmount.toDecimal())
                .totalAmount(actualAmount.toDecimal())
                .deductAmount(command.getDeductAmount().toDecimal())
                .payItems(buildPayItems(command.getPayItems()))
                .currency(order.getCurrency())
                .scale(order.getCurrencyScale())
                .build();
    }

    /**
     * 获取需要生成发票的订单项
     */
    private List<OrderItem> getInvoiceOrderItems(Order order, PayCommand command) {
        if (command.isPartialPay()) {
            // 部分支付，只包含指定的订单项
            return order.getItems().stream()
                    .filter(item -> command.getOrderItemIds().contains(item.getId()))
                    .collect(Collectors.toList());
        } else {
            // 全部支付，包含所有订单项
            return order.getItems();
        }
    }

    /**
     * 计算订单项总金额
     */
    private Money calculateTotalAmount(List<OrderItem> orderItems,Order order) {
        return orderItems.stream()
                .map(OrderItem::getTotalAmount)
                .reduce(Money.zero(order.getCurrency(), order.getCurrencyScale()), Money::add);
    }

    /**
     * 构建收款方命令
     */
    private PartyCommand buildPayeePartyCommand(Order order, PayCommand command) {
        var merchantId = command.getMerchantId();
        if (!StringUtils.hasText(merchantId)) {
            merchantId = order.getMerchantId();
        }
        MerchantInfo merchantInfo = null;
        if (command.getIsPlatform() != null && command.getIsPlatform()) {
            merchantInfo = merchantClient.getPlatformInfoById(merchantId);
        } else {
            merchantInfo = merchantClient.getMerchantInfoById(merchantId);
        }
        if (merchantInfo == null) {
            throw new BusinessException("query_merchant_err");
        }
        return PartyCommand.builder()
                .partyId(merchantInfo.getId())
                .name(merchantInfo.getName())
                .partyType(1)
                .taxId(merchantInfo.getTaxNum())
                .address(merchantInfo.getAddress())
                .phone(merchantInfo.getPhone())
                //.email(merchantInfo.getEmail())
                .countryCode(merchantInfo.getCountryCode())
                .build();
    }
    /**
     * 构建付款方命令
     */
    private PartyCommand buildPayerPartyCommand(PayCommand command) {
        var userDto = userServerClient.getOrCreateTemporaryUser(command.getCountryCode(), command.getUserPhone());
        if (userDto == null) {
            throw new BusinessException("query_user_err");
        }
        return PartyCommand.builder()
                .partyId(userDto.getId())
                .name(userDto.getAccount())
                .partyType(2)
                .userStatus(userDto.getIsTemporary() != null && userDto.getIsTemporary() == 1 ? 0 : 1)
                .phone(userDto.getPhone())
                //.email(userDto.getEmail())
                .countryCode(userDto.getCountryCode())
                .build();
    }

    /**
     * 构建发票明细项命令
     */
    private List<InvoiceItemCommand> buildInvoiceItems(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> InvoiceItemCommand.builder()
                        .orderItemId(item.getId())
                        .productId(item.getProductId())
                        .orderItemId(item.getId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .originalPrice(item.getUnitPrice().toDecimal())
                        .unitPrice(item.getDiscountedPrice() != null ? item.getDiscountedPrice().toDecimal() : item.getUnitPrice().toDecimal())
                        //taxRate(item.getTaxRate())
                        //.taxAmount(item.getTaxAmount().getAmount())
                        .totalAmount(item.getTotalAmount().toDecimal())
                        //.remark(item.getRemark())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 构建支付项命令
     */
    private List<PayItemCommand> buildPayItems(List<PayItem> payItems) {
        return payItems.stream()
                .map(item -> PayItemCommand.builder()
                        .channelId(item.getChannelId())
                        .tradeNo(item.getTradeNo())
                        .amount(item.getAmount().toDecimal())
                        .payTime(DateUtils.getCurrentTimestampInUTC())
                        .remark(item.getRemark())
                        .build())
                .collect(Collectors.toList());
    }
}