package com.ares.cloud.order.domain.model.valueobject;

import com.ares.cloud.order.domain.enums.OrderError;
import lombok.Builder;
import lombok.Data;
import org.ares.cloud.common.exception.RequestBadException;
import org.springframework.util.StringUtils;

/**
 * 预订信息值对象
 */
@Data
@Builder
public class ReservationInfo {

    /**
     * 预订ID
     */
    private String id;
    
    /**
     * 预订时间
     */
    private Long reservationTime;
    
    /**
     * 预订人姓名
     */
    private String reserverName;
    
    /**
     * 预订人电话
     */
    private String reserverPhone;
    
    /**
     * 就餐人数
     */
    private Integer diningNumber;
    
    /**
     * 预订备注
     */
    private String remarks;
    
    /**
     * 验证预订信息
     */
    public void validate() {
        if (reservationTime == null) {
            throw new RequestBadException(OrderError.RESERVATION_TIME_REQUIRED);
        }
        if (!StringUtils.hasText(reserverName)) {
            throw new RequestBadException(OrderError.RESERVER_NAME_REQUIRED);
        }
        if (!StringUtils.hasText(reserverPhone)) {
            throw new RequestBadException(OrderError.RESERVER_PHONE_REQUIRED);
        }
//        if (diningNumber == null || diningNumber <= 0) {
//            throw new RequestBadException(OrderError.DINING_NUMBER_REQUIRED);
//        }
    }
} 