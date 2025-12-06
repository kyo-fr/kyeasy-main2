package com.ares.cloud.payment.domain.repository;

import com.ares.cloud.payment.domain.model.Invoice;

import java.util.List;
import java.util.Optional;

/**
 * 发票仓储接口
 * 负责发票聚合根的持久化操作
 */
public interface InvoiceRepository {
    
    /**
     * 保存发票
     *
     * @param invoice 发票聚合根
     * @return 保存后的发票ID
     */
    String save(Invoice invoice);
    
    /**
     * 根据ID查询发票
     *
     * @param invoiceId 发票ID
     * @return 发票聚合根
     */
    Optional<Invoice> findById(String invoiceId);
    
    /**
     * 根据订单ID查询发票
     *
     * @param orderId 订单ID
     * @return 发票聚合根列表
     */
    List<Invoice> findByOrderId(String orderId);
    
    /**
     * 更新发票状态
     *
     * @param invoice 发票聚合根
     * @return 是否更新成功
     */
    boolean update(Invoice invoice);
    
    /**
     * 删除发票（逻辑删除）
     *
     * @param invoiceId 发票ID
     * @return 是否删除成功
     */
    boolean delete(String invoiceId);

}