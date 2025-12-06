package org.ares.cloud.infrastructure.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.domain.model.PaymentNotifyRecord;
import org.ares.cloud.domain.repository.PaymentNotifyRecordRepository;
import org.ares.cloud.domain.service.PaymentDomainService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 支付通知任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentNotifyTask {

    private final PaymentNotifyRecordRepository notifyRepository;
    private final PaymentDomainService paymentDomainService;
    
    /**
     * 重试失败的通知
     */
    @Scheduled(fixedDelay = 30000)
    public void retryFailedNotify() {
        List<PaymentNotifyRecord> records = notifyRepository.findNeedRetryRecords(LocalDateTime.now());
        for (PaymentNotifyRecord record : records) {
            try {
                paymentDomainService.retryPaymentNotify(record);
            } catch (Exception e) {
                log.error("Failed to retry notify: {}", record.getNotifyId(), e);
            }
        }
    }
} 