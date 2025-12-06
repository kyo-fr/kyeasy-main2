package org.ares.cloud.domain.repository;

import org.ares.cloud.domain.model.PaymentNotifyRecord;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 支付通知记录仓储接口
 */
public interface PaymentNotifyRecordRepository {
    
    /**
     * 保存通知记录
     *
     * @param record 通知记录
     */
    void save(PaymentNotifyRecord record);
    
    /**
     * 查询需要重试的通知记录
     *
     * @param now 当前时间
     * @return 需要重试的通知记录列表
     */
    List<PaymentNotifyRecord> findNeedRetryRecords(LocalDateTime now);
} 