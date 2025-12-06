package org.ares.cloud.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.ares.cloud.domain.model.PaymentNotifyRecord;
import org.ares.cloud.domain.repository.PaymentNotifyRecordRepository;
import org.ares.cloud.infrastructure.persistence.converter.PaymentNotifyRecordConverter;
import org.ares.cloud.infrastructure.persistence.entity.PaymentNotifyRecordEntity;
import org.ares.cloud.infrastructure.persistence.mapper.PaymentNotifyRecordMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 支付通知记录仓储实现类
 */
@Repository
@RequiredArgsConstructor
public class PaymentNotifyRecordRepositoryImpl implements PaymentNotifyRecordRepository {

    private final PaymentNotifyRecordMapper notifyRecordMapper;
    private final PaymentNotifyRecordConverter notifyRecordConverter;

    /**
     * 保存通知记录
     *
     * @param record 通知记录
     */
    @Override
    public void save(PaymentNotifyRecord record) {
        PaymentNotifyRecordEntity entity = notifyRecordConverter.toEntity(record);
        if (notifyRecordMapper.selectById(record.getNotifyId()) == null) {
            notifyRecordMapper.insert(entity);
        } else {
            notifyRecordMapper.updateById(entity);
        }
    }

    /**
     * 查询需要重试的通知记录
     *
     * @param now 当前时间
     * @return 需要重试的通知记录列表
     */
    @Override
    public List<PaymentNotifyRecord> findNeedRetryRecords(LocalDateTime now) {
        List<PaymentNotifyRecordEntity> entities = notifyRecordMapper.selectNeedRetryRecords(now);
        return entities.stream()
            .map(notifyRecordConverter::toDomain)
            .collect(Collectors.toList());
    }
} 