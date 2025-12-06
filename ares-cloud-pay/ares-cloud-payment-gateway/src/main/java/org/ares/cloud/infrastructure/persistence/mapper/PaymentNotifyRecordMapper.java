package org.ares.cloud.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ares.cloud.infrastructure.persistence.entity.PaymentNotifyRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PaymentNotifyRecordMapper extends BaseMapper<PaymentNotifyRecordEntity> {
    
    @Select("SELECT * FROM payment_notify_records WHERE notify_success = false AND retry_count < 3 AND next_retry_time <= #{now}")
    List<PaymentNotifyRecordEntity> selectNeedRetryRecords(LocalDateTime now);
} 