package com.ares.cloud.pay.domain.repository;

import com.ares.cloud.pay.domain.model.PaymentQRCode;
import java.util.List;

/**
 * 支付二维码仓储接口
 */
public interface PaymentQRCodeRepository {
    
    /**
     * 保存支付二维码
     *
     * @param qrCode 支付二维码
     */
    void save(PaymentQRCode qrCode);
    
    /**
     * 根据ID查询支付二维码
     *
     * @param id 二维码ID
     * @return 支付二维码
     */
    PaymentQRCode findById(String id);
    
    /**
     * 根据所有者ID和支付区域查询支付二维码
     *
     * @param ownerId 所有者ID
     * @param paymentRegion 支付区域
     * @return 支付二维码
     */
    PaymentQRCode findByOwnerIdAndRegion(String ownerId, String paymentRegion);
    
    /**
     * 根据所有者ID查询支付二维码列表
     *
     * @param ownerId 所有者ID
     * @return 支付二维码列表
     */
    List<PaymentQRCode> findByOwnerId(String ownerId);
    
    /**
     * 根据所有者类型查询支付二维码列表
     *
     * @param ownerType 所有者类型
     * @return 支付二维码列表
     */
    List<PaymentQRCode> findByOwnerType(String ownerType);
    
    /**
     * 根据支付区域查询支付二维码列表
     *
     * @param paymentRegion 支付区域
     * @return 支付二维码列表
     */
    List<PaymentQRCode> findByPaymentRegion(String paymentRegion);
    
    /**
     * 更新支付二维码状态
     *
     * @param id 二维码ID
     * @param status 新状态
     */
    void updateStatus(String id, String status);
} 