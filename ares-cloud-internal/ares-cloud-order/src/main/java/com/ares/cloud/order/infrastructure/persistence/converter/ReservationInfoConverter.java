package com.ares.cloud.order.infrastructure.persistence.converter;

import com.ares.cloud.order.application.dto.ReservationInfoDTO;
import com.ares.cloud.order.domain.model.valueobject.ReservationInfo;
import com.ares.cloud.order.infrastructure.persistence.entity.ReservationInfoDO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ReservationInfoConverter {
    
    /**
     * 将领域对象转换为DO
     */
    default ReservationInfoDO toReservationInfoDO(ReservationInfo domain, String orderId) {
        if (domain == null) {
            return null;
        }
        
        ReservationInfoDO reservationInfoDO = new ReservationInfoDO();
        reservationInfoDO.setId(domain.getId());
        reservationInfoDO.setOrderId(orderId);
        reservationInfoDO.setReservationTime(domain.getReservationTime());
        reservationInfoDO.setReserverName(domain.getReserverName());
        reservationInfoDO.setReserverPhone(domain.getReserverPhone());
        reservationInfoDO.setDiningNumber(domain.getDiningNumber());
        reservationInfoDO.setRemarks(domain.getRemarks());
        
        return reservationInfoDO;
    }
    
    /**
     * 将DO转换为领域对象
     */
    default ReservationInfo toReservationInfo(ReservationInfoDO reservationInfoDO) {
        if (reservationInfoDO == null) {
            return null;
        }
        
        return ReservationInfo.builder()    
                .id(reservationInfoDO.getId())
                .reservationTime(reservationInfoDO.getReservationTime())
                .reserverName(reservationInfoDO.getReserverName())
                .reserverPhone(reservationInfoDO.getReserverPhone())
                .diningNumber(reservationInfoDO.getDiningNumber())
                .remarks(reservationInfoDO.getRemarks())
                .build();
    }
    /**
     * 将领域对象转换为DTO
     */
    default ReservationInfoDTO toDTO(ReservationInfoDO domain) {
        if (domain == null) {
            return null;
        }

        return ReservationInfoDTO.builder()
                .id(domain.getId())
                .reservationTime(domain.getReservationTime())
                .reserverName(domain.getReserverName())
                .reserverPhone(domain.getReserverPhone())
                .diningNumber(domain.getDiningNumber())
                .roomPreference(domain.getRoomPreference())
                .dietaryRequirements(domain.getDietaryRequirements())
                .remarks(domain.getRemarks())
                .build();
    }
} 