package com.ares.cloud.order.infrastructure.service;

import com.ares.cloud.order.domain.event.OrderPaidEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.payment.PayCenterServerClient;
import org.ares.cloud.api.payment.command.MerchantPriceReductionCommand;
import org.ares.cloud.api.payment.dto.TransferResultDTO;
import org.ares.cloud.api.user.UserServerClient;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.common.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 订单事件业务处理服务
 * 
 * 负责处理订单事件的具体业务逻辑：
 * 1. 生成发票
 * 2. 赠送礼物点（购买赠送）
 * 
 * @author ares-cloud
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventProcessService {
    
    private final com.ares.cloud.order.domain.service.InvoiceService invoiceService;
    private final com.ares.cloud.order.domain.repository.OrderRepository orderRepository;
    private final PayCenterServerClient payCenterServerClient;
    private final UserServerClient userServerClient;

    /**
     * 生成发票（使用 InvoiceService）
     * 支持全部支付和部分支付
     * 
     * @param event 订单支付事件
     */
    public void generateInvoice(OrderPaidEventMessage event) {
        log.debug("开始生成发票: 订单ID={}, 商户ID={}, 发票金额={}, 部分支付={}, 支付明细数={}",
                event.getOrderId(), event.getMerchantId(), event.getDeductAmount(),
                event.isPartialPay(),
                event.getPayItems() != null ? event.getPayItems().size() : 0);

        // 1. 获取订单信息
        com.ares.cloud.order.domain.model.aggregate.Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("订单不存在: " + event.getOrderId()));

        // 2. 使用事件的转换方法构建支付命令（一行代码！）
        com.ares.cloud.order.domain.model.command.PayCommand payCommand = 
                event.toPayCommand(order.getCurrency(), order.getCurrencyScale());

        // 3. 调用 InvoiceService 生成发票
        invoiceService.generateInvoice(order, payCommand);

        log.info("✅ 发票生成完成: 订单ID={}", event.getOrderId());
    }

    /**
     * 赠送礼物点（根据支付命令中的 giftPoints 字段）
     * 调用支付中心的转账接口进行购买赠送
     * 
     * 只有当 giftPoints 有值且大于0时才执行转账
     * 
     * 购买赠送（SHOPPING）：商户账户转给用户账户
     * - fromAccountId: merchantId（商户账户，付款方）
     * - toAccountId: userId（用户账户，收款方）
     * - amount: giftPoints（赠送金额）
     * 
     * @param event 订单支付事件
     */
    public void giveGiftPoints(OrderPaidEventMessage event) {
        // 检查是否有礼物点需要赠送
        if (event.getGiftPoints() == null || event.getGiftPoints().compareTo(BigDecimal.ZERO) <= 0) {
            log.debug("【购买赠送】无需赠送: 订单ID={}, giftPoints={}", 
                    event.getOrderId(), event.getGiftPoints());
            return;
        }
        
        transferGiftPointsToUser(event);
    }
    
    /**
     * 商户向用户赠送礼物点（购买赠送）
     * 从 OrderPaidEventMessage 中获取：
     * - merchantId: 商户账户（付款方）
     * - userId: 用户账户（收款方，通过 getOrCreateTemporaryUser 获取）
     * - giftPoints: 赠送金额（礼物点金额）
     * 
     * @param event 订单支付事件
     */
    private void transferGiftPointsToUser(OrderPaidEventMessage event) {
        BigDecimal giftPoints = event.getGiftPoints();
        
        log.info("【购买赠送】开始处理: 订单ID={}, 商户ID={}, 转账金额={}", 
                event.getOrderId(), event.getMerchantId(), giftPoints);
        
        // 1. 获取订单信息（获取币种区域）
        com.ares.cloud.order.domain.model.aggregate.Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("订单不存在: " + event.getOrderId()));
        
        String paymentRegion = order.getCurrency() != null ? order.getCurrency() : "CNY";
        
        log.debug("【购买赠送】订单币种: {}", paymentRegion);
        
        // 2. 获取或创建用户（参照 InvoiceServiceImpl）
        UserDto userDto = userServerClient.getOrCreateTemporaryUser(event.getCountryCode(), event.getUserPhone());
        if (userDto == null) {
            log.error("【购买赠送】获取用户失败: 国家码={}, 手机号={}", event.getCountryCode(), event.getUserPhone());
            throw new BusinessException("query_user_err");
        }
        
        String userId = userDto.getId();
        log.debug("【购买赠送】获取用户成功: userId={}, 账号={}, 临时用户={}", 
                userId, userDto.getAccount(), userDto.getIsTemporary() == 1);
        if (userId.equals(event.getMerchantId())) {
            log.error("【购买赠送】不能向自己赠送: 订单ID={}, 商户ID={}, 用户ID={}",
                    event.getOrderId(), event.getMerchantId(), userId);
            return;
        }
        // 3. 构建商户减价命令 - 商户向用户赠送礼物点
        MerchantPriceReductionCommand priceReductionCommand = new MerchantPriceReductionCommand();
        priceReductionCommand.setMerchantId(event.getMerchantId()); // 商户账户（付款方）
        priceReductionCommand.setUserId(userId);                    // 用户账户（收款方）
        priceReductionCommand.setAmount(giftPoints);                // 赠送金额（礼物点）
        priceReductionCommand.setPaymentRegion(paymentRegion);      // 使用订单的币种区域
        priceReductionCommand.setDescription(String.format("订单购买赠送 - 订单号: %s, 商户: %s 赠送 %s %s 给用户: %s（%s）", 
                event.getOrderId(), event.getMerchantId(), giftPoints, paymentRegion, 
                userId, userDto.getAccount()));
        // 购买赠送不需要密码验证，不设置 paymentPassword
        
        // 4. 调用支付中心商户减价接口
        log.debug("【购买赠送】调用支付中心商户减价接口: merchantId={}, userId={}, amount={}, paymentRegion={}", 
                event.getMerchantId(), userId, giftPoints, paymentRegion);
        
        TransferResultDTO result = payCenterServerClient.merchantPriceReduction(priceReductionCommand);
        
        // 5. 处理转账结果
        if (result == null) {
            log.error("【购买赠送】转账结果为空: 订单ID={}, 商户ID={}, 用户ID={}", 
                    event.getOrderId(), event.getMerchantId(), userId);
            throw new RuntimeException("转账结果为空");
        }
        
        if ("SUCCESS".equals(result.getStatus())) {
            log.info("✅ 【购买赠送】转账成功: 订单ID={}, 商户ID={}, 用户ID={}, 转账金额={}, 交易ID={}", 
                    event.getOrderId(), event.getMerchantId(), userId, 
                    giftPoints, result.getTransactionId());
        } else {
            log.error("【购买赠送】转账失败: 订单ID={}, 商户ID={}, 用户ID={}, 状态={}, 消息={}", 
                    event.getOrderId(), event.getMerchantId(), userId, 
                    result.getStatus(), result.getMessage());
            throw new RuntimeException("转账失败: " + result.getMessage());
        }
    }
}

