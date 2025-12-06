package com.ares.cloud.pay.domain.service;

import com.ares.cloud.pay.domain.command.CreatePaymentOrderCommand;
import com.ares.cloud.pay.domain.command.TransferDomainCommand;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.domain.enums.PaymentMethod;
import com.ares.cloud.pay.domain.enums.PaymentStatus;
import com.ares.cloud.pay.domain.model.*;
import com.ares.cloud.pay.domain.repository.PaymentOrderRepository;
import com.ares.cloud.pay.domain.valueobject.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.IdUtils;
import org.ares.cloud.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.HashMap;
import java.util.Base64;

import com.ares.cloud.pay.domain.event.PaymentOrderCreatedEvent;
import com.ares.cloud.pay.domain.event.PaymentOrderStatusChangedEvent;
import com.ares.cloud.pay.domain.event.PaymentSuccessEvent;
import com.ares.cloud.pay.domain.event.PaymentFailedEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 支付领域服务
 * 提供完整的商户接入支付功能，包括支付地址生成、签名验证、回调通知等
 */
@Slf4j
@Service
public class PaymentDomainService {
    
    @Resource
    private PaymentOrderRepository paymentOrderRepository;

    @Resource
    private TransferDomainService transferDomainService;
    
    @Resource
    private MerchantDomainService merchantDomainService;
    
    @Resource
    private AccountDomainService accountDomainService;

    @Resource
    private RedisUtil redisUtil;
    
    @Resource
    private ApplicationEventPublisher eventPublisher;
    
    @Value("${payment.gateway.url:http://localhost:9300}")
    private String paymentGatewayUrl;
    
    @Value("${payment.notify.url:http://localhost:9300/api/payment/notify}")
    private String paymentNotifyUrl;
    
    @Value("${payment.ticket.expire:1800}")
    private int ticketExpireSeconds;
    
    // ==================== 公共方法 ====================
    
    /**
     * 创建支付订单
     * 商户调用此接口创建支付订单，返回支付地址和签名
     *
     * @param command 创建支付订单命令
     * @return 支付订单创建结果
     */
    @Transactional
    public PaymentCreateResult createPaymentOrder(CreatePaymentOrderCommand command) {
        // 验证签名
        String expectedSign = generateCreateOrderSign(command);
        command.validateSign(expectedSign);
        
        // 检查外部订单号是否已存在
        PaymentOrder existingOrder = paymentOrderRepository.findByMerchantOrderNo(command.getMerchantOrderNo());
        if (existingOrder != null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_ALREADY_EXISTS);
        }
        
        // 创建支付订单
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(IdUtils.fastSimpleUUID());
        paymentOrder.setOrderNo(PaymentOrder.generateOrderNo());
        paymentOrder.setMerchantOrderNo(command.getMerchantOrderNo());
        paymentOrder.setMerchantId(command.getMerchantId());
        paymentOrder.setAmount(command.getAmount());
        paymentOrder.setPaymentRegion(command.getPaymentRegion());
        paymentOrder.setSubject(command.getSubject());
        paymentOrder.setDescription(command.getDescription());
        paymentOrder.setReturnUrl(command.getReturnUrl());
        paymentOrder.setNotifyUrl(command.getNotifyUrl());
        paymentOrder.setExpireTime(command.getExpireTime() != null ? command.getExpireTime() : System.currentTimeMillis() + 30 * 60 * 1000);
        paymentOrder.setStatus(PaymentStatus.WAITING);
        paymentOrder.setPaymentMethod(PaymentMethod.BALANCE);
        paymentOrder.setCreateTime(System.currentTimeMillis());
        paymentOrder.setUpdateTime(System.currentTimeMillis());
        
        // 保存自定义参数
        Map<String, String> customParams = command.getCustomParams();
        if (customParams != null && !customParams.isEmpty()) {
            paymentOrder.setCustomParams(new HashMap<>(customParams));
        }
        
        // 保存支付订单到数据库
        paymentOrderRepository.save(paymentOrder);
        
        // 发布支付订单创建事件
        eventPublisher.publishEvent(new PaymentOrderCreatedEvent(paymentOrder));
        
        // 生成支付票据
        String paymentTicket = generatePaymentTicket(paymentOrder);
        
        // 将订单信息存储到Redis
        saveOrderToRedis(paymentTicket, paymentOrder);
        
        // 生成支付地址（只包含票据）
        String paymentUrl = generatePaymentUrl(paymentTicket);
        
        // 生成扫描支付页面地址（只包含票据）
        String qrCodeUrl = generateQrCodeUrl(paymentTicket);
        
        // 生成支付参数签名
        String signature = generatePaymentSignature(paymentOrder, paymentTicket);
        
        // 构建返回结果
        return PaymentCreateResult.builder()
                .orderNo(paymentOrder.getOrderNo())
                .merchantOrderNo(command.getMerchantOrderNo())
                .paymentUrl(paymentUrl)
                .qrCodeUrl(qrCodeUrl)
                .amount(command.getAmount().getAmount())
                .currency(command.getAmount().getCurrency())
                .sign(signature)
                .expireTime(paymentOrder.getExpireTime())
                .build();
    }

    /**
     * 查询支付订单状态
     *
     * @param merchantId 商户ID
     * @param orderNo 订单号
     * @param sign 签名
     * @return 支付订单状态
     */
    public PaymentQueryResult queryPaymentOrder(String merchantId, String orderNo, String sign) {
        // 参数校验
        if (!StringUtils.hasText(merchantId) || !StringUtils.hasText(orderNo) || !StringUtils.hasText(sign)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 验证签名
        String expectedSign = generateQueryOrderSign(merchantId, orderNo);
        if (!expectedSign.equals(sign)) {
            throw new BusinessException(PaymentError.INVALID_SIGNATURE);
        }
        
        // 查询支付订单
        PaymentOrder paymentOrder = paymentOrderRepository.findByOrderNo(orderNo);
        if (paymentOrder == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 验证商户权限
        if (!merchantId.equals(paymentOrder.getMerchantId())) {
            throw new BusinessException(PaymentError.UNAUTHORIZED);
        }
        
        // 生成查询结果签名
        String signature = generateQuerySignature(paymentOrder);
        
        return PaymentQueryResult.builder()
                .orderNo(paymentOrder.getOrderNo())
                .merchantOrderNo(paymentOrder.getMerchantOrderNo())
                .status(paymentOrder.getStatus().getCode())
                .amount(paymentOrder.getAmount().getAmount())
                .currency(paymentOrder.getAmount().getCurrency())
                .payTime(paymentOrder.getPayTime())
                .signature(signature)
                .build();
    }

    /**
     * 退款
     *
     * @param merchantId 商户ID
     * @param orderNo 订单号
     * @param refundAmount 退款金额
     * @param reason 退款原因
     * @param sign 签名
     * @return 退款结果
     */
    @Transactional
    public RefundResult refund(String merchantId, String orderNo, Money refundAmount, String reason, String sign) {
        // 参数校验
        if (!StringUtils.hasText(merchantId) || !StringUtils.hasText(orderNo) || 
            refundAmount == null || !StringUtils.hasText(sign)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 验证签名
        String expectedSign = generateRefundOrderSign(merchantId, orderNo, refundAmount, reason);
        if (!expectedSign.equals(sign)) {
            throw new BusinessException(PaymentError.INVALID_SIGNATURE);
        }
        
        // 查询支付订单
        PaymentOrder paymentOrder = paymentOrderRepository.findByOrderNo(orderNo);
        if (paymentOrder == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 验证商户权限
        if (!merchantId.equals(paymentOrder.getMerchantId())) {
            throw new BusinessException(PaymentError.UNAUTHORIZED);
        }
        
        // 检查订单状态
        if (!PaymentStatus.SUCCESS.equals(paymentOrder.getStatus())) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_ORDER_STATUS);
        }
        
        // 检查退款金额
        if (refundAmount.isGreaterThan(paymentOrder.getAmount())) {
            throw new BusinessException(PaymentError.REFUND_AMOUNT_EXCEEDED);
        }
        
        // 更新订单状态
        PaymentStatus refundStatus = refundAmount.equals(paymentOrder.getAmount()) ? 
                PaymentStatus.REFUNDED : PaymentStatus.PARTIAL_REFUNDED;
        paymentOrder.setStatus(refundStatus);
        paymentOrder.setUpdateTime(System.currentTimeMillis());
        paymentOrderRepository.updateStatus(orderNo, refundStatus);
        
        // 生成退款结果签名
        String signature = generateRefundSignature(paymentOrder, refundAmount, reason);
        
        return RefundResult.builder()
                .orderNo(orderNo)
                .merchantOrderNo(paymentOrder.getMerchantOrderNo())
                .refundAmount(refundAmount.getAmount())
                .currency(refundAmount.getCurrency())
                .status(refundStatus.getCode())
                .reason(reason)
                .sign(signature)
                .build();
    }
    
    /**
     * 关闭支付订单
     *
     * @param merchantId 商户ID
     * @param orderNo 订单号
     * @return 关闭结果
     */
    @Transactional
    public boolean closePaymentOrder(String merchantId, String orderNo) {
        // 参数校验
        if (!StringUtils.hasText(merchantId) || !StringUtils.hasText(orderNo)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 查询支付订单
        PaymentOrder paymentOrder = paymentOrderRepository.findByOrderNo(orderNo);
        if (paymentOrder == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 验证商户权限
        if (!merchantId.equals(paymentOrder.getMerchantId())) {
            throw new BusinessException(PaymentError.UNAUTHORIZED);
        }
        
        // 检查订单状态
        if (!PaymentStatus.WAITING.equals(paymentOrder.getStatus())) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_ORDER_STATUS);
        }
        
        // 关闭订单
        paymentOrder.setStatus(PaymentStatus.CLOSED);
        paymentOrder.setUpdateTime(System.currentTimeMillis());
        paymentOrderRepository.updateStatus(orderNo, PaymentStatus.CLOSED);
        
        return true;
    }
    /**
     * 根据支付票据获取订单信息
     */
    public PaymentOrder getOrderByPaymentTicket(String paymentTicket) {
        String redisKey = generatePaymentTicketKey(paymentTicket);
        String orderNo = (String) redisUtil.get(redisKey);
        
        if (orderNo == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 从数据库查询完整的订单信息
        PaymentOrder paymentOrder = paymentOrderRepository.findByOrderNo(orderNo);
        if (paymentOrder == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        return paymentOrder;
    }

    /**
     * 验证支付票据
     */
    public boolean validatePaymentTicket(String paymentTicket) {
        String redisKey = generatePaymentTicketKey(paymentTicket);
        return redisUtil.hasKey(redisKey);
    }

    /**
     * 根据订单号执行支付
     * 通用的支付逻辑，包含订单验证、密码验证、转账、状态更新等
     *
     * @param orderNo 订单号
     * @param userId 用户ID
     * @param paymentPassword 支付密码
     * @param paymentType 支付类型（用于日志和消息）
     * @return 支付结果
     */
    @Transactional
    public PaymentExecuteResult executePaymentByOrderNo(String orderNo, String userId, String paymentPassword, String paymentType) {
        // 查询支付订单
        PaymentOrder paymentOrder = paymentOrderRepository.findByOrderNo(orderNo);
        if (paymentOrder == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 验证订单状态
        if (!PaymentStatus.WAITING.equals(paymentOrder.getStatus())) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_ORDER_STATUS);
        }
        
        // 验证订单是否过期
        if (System.currentTimeMillis() > paymentOrder.getExpireTime()) {
            paymentOrder.setStatus(PaymentStatus.CLOSED);
            paymentOrder.setUpdateTime(System.currentTimeMillis());
            paymentOrderRepository.updateStatus(orderNo, PaymentStatus.CLOSED);
            
            // 发布支付失败事件
            eventPublisher.publishEvent(new PaymentFailedEvent(paymentOrder, "订单已过期"));
            
            throw new BusinessException(PaymentError.PAYMENT_ORDER_EXPIRED);
        }
        
        // 验证用户支付密码
        validateUserPaymentPassword(userId, paymentPassword);
        
        // 执行资金转账
        TransferResult transferResult = executePaymentTransfer(paymentOrder, userId);
        
        if (transferResult.isSuccess()) {
            // 保存旧状态
            String oldStatus = paymentOrder.getStatus().getCode();
            
            // 更新订单状态为成功
            paymentOrder.setStatus(PaymentStatus.SUCCESS);
            paymentOrder.setPayTime(System.currentTimeMillis());
            paymentOrder.setUpdateTime(System.currentTimeMillis());
            paymentOrderRepository.updateStatus(orderNo, PaymentStatus.SUCCESS);
            
            // 发布支付订单状态变更事件
            eventPublisher.publishEvent(new PaymentOrderStatusChangedEvent(paymentOrder, oldStatus, PaymentStatus.SUCCESS.getCode()));
            
            // 发布支付成功事件
            eventPublisher.publishEvent(new PaymentSuccessEvent(paymentOrder, transferResult.getTransactionId()));
            
            // 发送支付成功通知
            sendPaymentNotification(paymentOrder, "SUCCESS");
            
            return PaymentExecuteResult.success(orderNo, transferResult.getTransactionId(), paymentType + "成功");
        } else {
            // 保存旧状态
            String oldStatus = paymentOrder.getStatus().getCode();
            
            // 更新订单状态为失败
            paymentOrder.setStatus(PaymentStatus.FAILED);
            paymentOrder.setUpdateTime(System.currentTimeMillis());
            paymentOrderRepository.updateStatus(orderNo, PaymentStatus.FAILED);
            
            // 发布支付订单状态变更事件
            eventPublisher.publishEvent(new PaymentOrderStatusChangedEvent(paymentOrder, oldStatus, PaymentStatus.FAILED.getCode()));
            
            // 发布支付失败事件
            eventPublisher.publishEvent(new PaymentFailedEvent(paymentOrder, transferResult.getMessage()));
            
            return PaymentExecuteResult.failure(orderNo, transferResult.getMessage());
        }
    }


    /**
     * 生成支付二维码
     * 为支付订单生成二维码内容，包含订单信息、金额、有效期和票据
     *
     * @param orderNo 订单号
     * @return 二维码内容字符串
     */
    public String generatePaymentQRCode(String orderNo) {
        // 查询支付订单
        PaymentOrder paymentOrder = paymentOrderRepository.findByOrderNo(orderNo);
        if (paymentOrder == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 检查订单状态
        if (!PaymentStatus.WAITING.equals(paymentOrder.getStatus())) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_ORDER_STATUS);
        }
        
        // 检查订单是否过期
        if (System.currentTimeMillis() > paymentOrder.getExpireTime()) {
            paymentOrder.setStatus(PaymentStatus.CLOSED);
            paymentOrder.setUpdateTime(System.currentTimeMillis());
            paymentOrderRepository.updateStatus(orderNo, PaymentStatus.CLOSED);
            throw new BusinessException(PaymentError.PAYMENT_ORDER_EXPIRED);
        }
        
        // 生成二维码票据
        String qrCodeTicket = generateQRCodeTicket(paymentOrder);
        
        // 将二维码信息存储到Redis
        saveQRCodeToRedis(qrCodeTicket, paymentOrder);
        
        // 生成二维码内容（query参数格式）
        return generateQRCodeContent(paymentOrder, qrCodeTicket);
    }
    
    /**
     * 二维码支付
     * 通过扫描二维码进行支付
     *
     * @param qrCodeContent 二维码内容
     * @param userId 用户ID
     * @param paymentPassword 支付密码
     * @return 支付结果
     */
    @Transactional
    public PaymentExecuteResult scanQRCodePayment(String qrCodeContent, String userId, String paymentPassword) {
        // 解析二维码内容
        QRCodePaymentInfo qrCodeInfo = parseQRCodeContent(qrCodeContent);

        // 验证二维码票据
        if (!validateQRCodeTicket(qrCodeInfo.getTicket())) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 从Redis获取订单号
        String orderNo = getQRCodeFromRedis(qrCodeInfo.getTicket());
        
        // 调用通用支付方法
        PaymentExecuteResult result = executePaymentByOrderNo(orderNo, userId, paymentPassword, "二维码支付");
        
        // 如果支付成功，清理Redis中的二维码票据
        if (result.isSuccess()) {
            clearQRCodeTicket(qrCodeInfo.getTicket());
        }
        
        return result;
    }
    
    /**
     * 执行支付
     * 用户通过支付票据进行支付操作
     *
     * @param paymentTicket 支付票据
     * @param userId 用户ID
     * @param paymentPassword 支付密码
     * @return 支付结果
     */
    @Transactional
    public PaymentExecuteResult executePayment(String paymentTicket, String userId, String paymentPassword) {
        // 验证支付票据
        if (!validatePaymentTicket(paymentTicket)) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        
        // 根据支付票据获取订单信息
        PaymentOrder paymentOrder = getOrderByPaymentTicket(paymentTicket);
        
        // 调用通用支付方法
        PaymentExecuteResult result = executePaymentByOrderNo(paymentOrder.getOrderNo(), userId, paymentPassword, "支付");
        
        // 如果支付成功，清理Redis中的票据
        if (result.isSuccess()) {
            clearPaymentTicket(paymentTicket);
        }
        
        return result;
    }
    /**
     * 生成创建订单签名
     */
    public String generateCreateOrderSign(CreatePaymentOrderCommand command) {
        Map<String, String> params = new HashMap<>();
        params.put("merchantId", command.getMerchantId());
        params.put("externalOrderNo", command.getMerchantOrderNo());
        params.put("amount", command.getAmount().getAmount().toString());
        params.put("currency", command.getAmount().getCurrency());
        params.put("paymentRegion", command.getPaymentRegion());
        params.put("subject", command.getSubject());
        if (StringUtils.hasText(command.getDescription())) {
            params.put("description", command.getDescription());
        }
        if (StringUtils.hasText(command.getReturnUrl())) {
            params.put("returnUrl", command.getReturnUrl());
        }
        if (StringUtils.hasText(command.getNotifyUrl())) {
            params.put("notifyUrl", command.getNotifyUrl());
        }
        if (command.getExpireTime() != null) {
            params.put("expireTime", command.getExpireTime().toString());
        }

        // 添加自定义参数到签名
        Map<String, String> customParams = command.getCustomParams();
        if (customParams != null && !customParams.isEmpty()) {
            for (Map.Entry<String, String> entry : customParams.entrySet()) {
                if (StringUtils.hasText(entry.getValue())) {
                    params.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return merchantDomainService.generateMerchantSignature(command.getMerchantId(), params);
    }
    // ==================== 私有方法 ====================
    /**
     * 验证二维码票据
     */
    private boolean validateQRCodeTicket(String qrCodeTicket) {
        String redisKey = generateQRCodeTicketKey(qrCodeTicket);
        return redisUtil.hasKey(redisKey);
    }

    /**
     * 将二维码信息存储到Redis（只保存订单号）
     */
    private void saveQRCodeToRedis(String qrCodeTicket, PaymentOrder paymentOrder) {
        String orderNo = paymentOrder.getOrderNo();
        String redisKey = generateQRCodeTicketKey(qrCodeTicket);
        redisUtil.set(redisKey, orderNo, ticketExpireSeconds);
    }

    /**
     * 从Redis获取二维码对应的订单号（只返回订单号）
     */
    private String getQRCodeFromRedis(String qrCodeTicket) {
        String redisKey = generateQRCodeTicketKey(qrCodeTicket);
        String redisOrderNo = (String) redisUtil.get(redisKey);
        if (redisOrderNo == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }
        return redisOrderNo;
    }

    /**
     * 生成查询订单签名
     */
    private String generateQueryOrderSign(String merchantId, String orderNo) {
        Map<String, String> params = new HashMap<>();
        params.put("merchantId", merchantId);
        params.put("orderNo", orderNo);
        return merchantDomainService.generateMerchantSignature(merchantId, params);
    }
    
    /**
     * 生成退款订单签名
     */
    private String generateRefundOrderSign(String merchantId, String orderNo, Money refundAmount, String reason) {
        Map<String, String> params = new HashMap<>();
        params.put("merchantId", merchantId);
        params.put("orderNo", orderNo);
        params.put("refundAmount", refundAmount.getAmount().toString());
        params.put("currency", refundAmount.getCurrency());
        if (StringUtils.hasText(reason)) {
            params.put("reason", reason);
        }

        return merchantDomainService.generateMerchantSignature(merchantId, params);
    }
    

    
    /**
     * 生成支付票据
     */
    private String generatePaymentTicket(PaymentOrder paymentOrder) {
        // 生成票据：订单号 + 时间戳 + 随机数
        String ticketData = paymentOrder.getOrderNo() + "_" + 
                           System.currentTimeMillis() + "_" +
                IdUtils.fastSimpleUUID().substring(0, 8);
        
        // 使用商户密钥加密票据
        String encryptedTicket = merchantDomainService.encryptMerchantParameter(paymentOrder.getMerchantId(), ticketData);
        
        // 使用 Base64 编码，确保票据在 URL 中安全传输
        return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedTicket.getBytes());
    }
    
    /**
     * 将订单信息存储到Redis（只保存订单号）
     */
    private void saveOrderToRedis(String paymentTicket, PaymentOrder paymentOrder) {
        String orderNo = paymentOrder.getOrderNo();
        String redisKey = generatePaymentTicketKey(paymentTicket);
        redisUtil.set(redisKey, orderNo, ticketExpireSeconds);
    }
    
    /**
     * 生成支付票据的Redis key
     */
    private String generatePaymentTicketKey(String paymentTicket) {
        return "payment:ticket:" + paymentTicket;
    }
    
    /**
     * 生成二维码票据的Redis key
     */
    private String generateQRCodeTicketKey(String qrCodeTicket) {
        return "payment:qrcode:" + qrCodeTicket;
    }
    
    /**
     * 生成支付地址（只包含票据）
     */
    private String generatePaymentUrl(String paymentTicket) {
        return paymentGatewayUrl + "/pay?ticket=" + paymentTicket;
    }
    
    /**
     * 生成扫描支付页面地址（只包含票据）
     */
    private String generateQrCodeUrl(String paymentTicket) {
        return paymentGatewayUrl + "/qrcode?ticket=" + paymentTicket;
    }
    
    /**
     * 生成支付参数签名
     */
    private String generatePaymentSignature(PaymentOrder paymentOrder, String paymentTicket) {
        Map<String, String> params = new HashMap<>();
        params.put("orderNo", paymentOrder.getOrderNo());
        params.put("merchantOrderNo", paymentOrder.getMerchantOrderNo());
        params.put("amount", paymentOrder.getAmount().getAmount().toString());
        params.put("currency", paymentOrder.getAmount().getCurrency());
        params.put("paymentUrl", generatePaymentUrl(paymentTicket));
        params.put("qrCodeUrl", generateQrCodeUrl(paymentTicket));
        params.put("expireTime", paymentOrder.getExpireTime().toString());

        return merchantDomainService.generateMerchantSignature(paymentOrder.getMerchantId(), params);
    }
    
    /**
     * 生成查询签名
     */
    private String generateQuerySignature(PaymentOrder paymentOrder) {
        Map<String, String> params = new HashMap<>();
        params.put("orderNo", paymentOrder.getOrderNo());
        params.put("merchantOrderNo", paymentOrder.getMerchantOrderNo());
        params.put("status", paymentOrder.getStatus().getCode());
        params.put("amount", paymentOrder.getAmount().getAmount().toString());
        params.put("currency", paymentOrder.getAmount().getCurrency());
        if (paymentOrder.getPayTime() != null) {
            params.put("payTime", paymentOrder.getPayTime().toString());
        }


        return merchantDomainService.generateMerchantSignature(paymentOrder.getMerchantId(), params);
    }
    
    /**
     * 生成回调签名
     */
    private String generateCallbackSignature(PaymentOrder paymentOrder, String status, Money amount) {
        Map<String, String> params = new HashMap<>();
        params.put("orderNo", paymentOrder.getOrderNo());
        params.put("merchantOrderNo", paymentOrder.getMerchantOrderNo());
        params.put("status", status);
        params.put("amount", amount.getAmount().toString());
        params.put("currency", amount.getCurrency());


        return merchantDomainService.generateMerchantSignature(paymentOrder.getMerchantId(), params);
    }
    
    /**
     * 生成退款签名
     */
    private String generateRefundSignature(PaymentOrder paymentOrder, Money amount, String reason) {
        Map<String, String> params = new HashMap<>();
        params.put("orderNo", paymentOrder.getOrderNo());
        params.put("merchantOrderNo", paymentOrder.getMerchantOrderNo());
        params.put("refundAmount", amount.getAmount().toString());
        params.put("currency", amount.getCurrency());
        params.put("reason", reason != null ? reason : "");
        return merchantDomainService.generateMerchantSignature(paymentOrder.getMerchantId(), params);
    }

    
    /**
     * 执行支付转账
     */
    private TransferResult executePaymentTransfer(PaymentOrder paymentOrder, String userId) {
        try {
            // 创建用户间转账命令（从用户账户向商户账户转账）
            TransferDomainCommand transferCommand = TransferDomainCommand.create(
                userId,
                paymentOrder.getMerchantId(),
                paymentOrder.getAmount(),
                paymentOrder.getPaymentRegion(),
                "支付订单：" + paymentOrder.getOrderNo()
            );
            
            // 执行转账
            TransferResult transferResult = transferDomainService.transferBetweenUsers(transferCommand, null);
            
            if (!transferResult.isSuccess()) {
                return transferResult;
            }
            
            return TransferResult.success(transferResult.getTransactionId(), "支付成功");
        } catch (Exception e) {
            return TransferResult.failure("支付失败：" + e.getMessage());
        }
    }
    
    /**
     * 验证用户支付密码
     */
    private void validateUserPaymentPassword(String userId, String paymentPassword) {
        // 这里应该调用账户服务验证支付密码
        // 暂时使用简单的验证逻辑
        if (!StringUtils.hasText(paymentPassword)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }

       boolean valid = accountDomainService.validatePassword(userId, paymentPassword);
        if (!valid) {
            throw new BusinessException(PaymentError.INVALID_PASSWORD);
        }
    }
    
    /**
     * 解析二维码内容
     */
    private QRCodePaymentInfo parseQRCodeContent(String qrCodeContent) {
        if (!StringUtils.hasText(qrCodeContent)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 解析query参数格式：orderNo=xxx&amount=xxx&currency=xxx&expireTime=xxx&ticket=xxx
        QRCodePaymentInfo qrCodeInfo = new QRCodePaymentInfo();
        
        String[] params = qrCodeContent.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                
                switch (key) {
                    case "orderNo":
                        qrCodeInfo.setOrderNo(value);
                        break;
                    case "amount":
                        qrCodeInfo.setAmount(Long.parseLong(value));
                        break;
                    case "currency":
                        qrCodeInfo.setCurrency(value);
                        break;
                    case "expireTime":
                        qrCodeInfo.setExpireTime(Long.parseLong(value));
                        break;
                    case "ticket":
                        qrCodeInfo.setTicket(value);
                        break;
                }
            }
        }
        
        // 验证必要参数
        if (!StringUtils.hasText(qrCodeInfo.getOrderNo()) || 
            !StringUtils.hasText(qrCodeInfo.getTicket())) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }

        // 验证二维码有效性
       validateQRCode(qrCodeInfo);
        
        return qrCodeInfo;
    }

    /**
     * 验证二维码有效性
     */
    private void validateQRCode(QRCodePaymentInfo qrCodeInfo) {
        // 验证订单是否存在
        PaymentOrder paymentOrder = paymentOrderRepository.findByOrderNo(qrCodeInfo.getOrderNo());
        if (paymentOrder == null) {
            throw new BusinessException(PaymentError.PAYMENT_ORDER_NOT_FOUND);
        }

        // 验证支付区域是否支持
        if (!isValidPaymentRegion(paymentOrder.getPaymentRegion())) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }

        // 验证金额是否有效
        if (paymentOrder.getAmount().isZero() || paymentOrder.getAmount().isNegative()) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_AMOUNT);
        }
    }

    /**
     * 验证支付区域是否有效
     */
    private boolean isValidPaymentRegion(String paymentRegion) {
        return "EUR".equals(paymentRegion) || "USD".equals(paymentRegion) || 
               "CNY".equals(paymentRegion) || "CHF".equals(paymentRegion) || 
               "GBP".equals(paymentRegion);
    }
    
    /**
     * 发送支付通知
     */
    private void sendPaymentNotification(PaymentOrder paymentOrder, String status) {
        try {
            // 构建通知参数
            Map<String, String> notifyParams = new HashMap<>();
            notifyParams.put("orderNo", paymentOrder.getOrderNo());
            notifyParams.put("merchantOrderNo", paymentOrder.getMerchantOrderNo());
            notifyParams.put("status", status);
            notifyParams.put("amount", paymentOrder.getAmount().getAmount().toString());
            notifyParams.put("currency", paymentOrder.getAmount().getCurrency());
            notifyParams.put("payTime", paymentOrder.getPayTime().toString());
            
            // 生成通知签名
            String signature = generateCallbackSignature(paymentOrder, status, paymentOrder.getAmount());
            notifyParams.put("sign", signature);
            
            // TODO: 异步发送通知
            // paymentNotifyService.sendNotification(paymentOrder.getNotifyUrl(), notifyParams);
            
        } catch (Exception e) {
            // 记录通知发送失败日志，但不影响支付流程
            log.error("Failed to send payment notification for order: {}", paymentOrder.getOrderNo(), e);
        }
    }
    
    /**
     * 清理支付票据
     */
    private void clearPaymentTicket(String paymentTicket) {
        String redisKey = generatePaymentTicketKey(paymentTicket);
        redisUtil.del(redisKey);
    }
    
    /**
     * 生成二维码内容（query参数格式）
     */
    private String generateQRCodeContent(PaymentOrder paymentOrder, String qrCodeTicket) {
        return "orderNo=" + paymentOrder.getOrderNo() + 
               "&amount=" + paymentOrder.getAmount().getAmount() + 
               "&currency=" + paymentOrder.getAmount().getCurrency() + 
               "&expireTime=" + paymentOrder.getExpireTime() + 
               "&ticket=" + qrCodeTicket;
    }
    
    /**
     * 生成二维码票据
     */
    private String generateQRCodeTicket(PaymentOrder paymentOrder) {
        // 生成票据：订单号 + 时间戳 + 随机数
        String ticketData = paymentOrder.getOrderNo() + "_" + 
                           System.currentTimeMillis() + "_" +
                IdUtils.fastSimpleUUID().substring(0, 8);
        
        // 使用商户密钥加密票据
        String encryptedTicket = merchantDomainService.encryptMerchantParameter(paymentOrder.getMerchantId(), ticketData);
        
        // 使用 Base64 编码，确保票据在 URL 中安全传输
        return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedTicket.getBytes());
    }
    
    /**
     * 清理二维码票据
     */
    private void clearQRCodeTicket(String qrCodeTicket) {
        String redisKey = generateQRCodeTicketKey(qrCodeTicket);
        redisUtil.del(redisKey);
    }
} 