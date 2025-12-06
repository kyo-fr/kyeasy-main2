package com.ares.cloud.order.infrastructure.persistence.converter;

import com.ares.cloud.order.application.dto.DeliveryInfoDTO;
import com.ares.cloud.order.domain.model.valueobject.DeliveryInfo;
import org.ares.cloud.common.model.Money;
import com.ares.cloud.order.infrastructure.persistence.entity.DeliveryInfoDO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = MoneyConverter.class)
public interface DeliveryInfoConverter extends MoneyConverter {
    default DeliveryInfoDO toDeliveryInfoDO(DeliveryInfo deliveryInfo) {
        if (deliveryInfo == null) {
            return null;
        }
        
        DeliveryInfoDO deliveryInfoDO = new DeliveryInfoDO();
        deliveryInfoDO.setId(deliveryInfo.getId());
        deliveryInfoDO.setDeliveryType(deliveryInfo.getDeliveryType());
        deliveryInfoDO.setRiderId(deliveryInfo.getRiderId());
        deliveryInfoDO.setDeliveryCompany(deliveryInfo.getDeliveryCompany());
        deliveryInfoDO.setTrackingNo(deliveryInfo.getTrackingNo());
        deliveryInfoDO.setDeliveryPhone(deliveryInfo.getDeliveryPhone());
        deliveryInfoDO.setDeliveryName(deliveryInfo.getDeliveryName());
        deliveryInfoDO.setDeliveryAddress(deliveryInfo.getDeliveryAddress());
        deliveryInfoDO.setDeliveryLatitude(deliveryInfo.getDeliveryLatitude());
        deliveryInfoDO.setDeliveryLongitude(deliveryInfo.getDeliveryLongitude());
        deliveryInfoDO.setDeliveryDistance(deliveryInfo.getDeliveryDistance());
        deliveryInfoDO.setDeliveryCountry(deliveryInfo.getDeliveryCountry());
        deliveryInfoDO.setDeliveryCity(deliveryInfo.getDeliveryCity());
        deliveryInfoDO.setDeliveryPostalCode(deliveryInfo.getDeliveryPostalCode());
        deliveryInfoDO.setDeliveryFee(getMoneyAmount(deliveryInfo.getDeliveryFee()));
        deliveryInfoDO.setCurrency(getMoneyCurrency(deliveryInfo.getDeliveryFee()));
        deliveryInfoDO.setCurrencyScale(getMoneyScale(deliveryInfo.getDeliveryFee()));
        deliveryInfoDO.setReceiverName(deliveryInfo.getReceiverName());
        deliveryInfoDO.setReceiverPhone(deliveryInfo.getReceiverPhone());
        return deliveryInfoDO;
    }
    
    default DeliveryInfo toDeliveryInfo(DeliveryInfoDO deliveryInfoDO) {
        if (deliveryInfoDO == null) {
            return null;
        }
        
        return DeliveryInfo.builder()
                .id(deliveryInfoDO.getId())
            .deliveryType(deliveryInfoDO.getDeliveryType())
            .riderId(deliveryInfoDO.getRiderId())
            .deliveryCompany(deliveryInfoDO.getDeliveryCompany())
            .trackingNo(deliveryInfoDO.getTrackingNo())
            .deliveryPhone(deliveryInfoDO.getDeliveryPhone())
             .deliveryName(deliveryInfoDO.getDeliveryName())
            .deliveryAddress(deliveryInfoDO.getDeliveryAddress())
            .deliveryLatitude(deliveryInfoDO.getDeliveryLatitude())
            .deliveryLongitude(deliveryInfoDO.getDeliveryLongitude())
            .deliveryDistance(deliveryInfoDO.getDeliveryDistance())
            .deliveryCountry(deliveryInfoDO.getDeliveryCountry())
            .deliveryCity(deliveryInfoDO.getDeliveryCity())
            .deliveryPostalCode(deliveryInfoDO.getDeliveryPostalCode())
            .deliveryFee(createMoney(deliveryInfoDO.getDeliveryFee(), deliveryInfoDO.getCurrency(), deliveryInfoDO.getCurrencyScale()))
            .receiverName(deliveryInfoDO.getReceiverName())
            .receiverPhone(deliveryInfoDO.getReceiverPhone())
            .build();
    }

    default DeliveryInfoDTO toDeliveryInfoDTO(DeliveryInfoDO deliveryInfoDO) {
        if (deliveryInfoDO == null) {
            return null;
        }
        
        DeliveryInfoDTO dto = new DeliveryInfoDTO();
        dto.setId(deliveryInfoDO.getId());
        dto.setDeliveryType(deliveryInfoDO.getDeliveryType());
        dto.setRiderId(deliveryInfoDO.getRiderId());
        dto.setDeliveryCompany(deliveryInfoDO.getDeliveryCompany());
        dto.setTrackingNo(deliveryInfoDO.getTrackingNo());
        dto.setDeliveryPhone(deliveryInfoDO.getDeliveryPhone());
        dto.setDeliveryName(deliveryInfoDO.getDeliveryName());
        dto.setDeliveryStartTime(deliveryInfoDO.getDeliveryStartTime());
        dto.setDeliveryAddress(deliveryInfoDO.getDeliveryAddress());
        dto.setDeliveryLatitude(deliveryInfoDO.getDeliveryLatitude());
        dto.setDeliveryLongitude(deliveryInfoDO.getDeliveryLongitude());
        dto.setDeliveryDistance(deliveryInfoDO.getDeliveryDistance());
        dto.setDeliveryCountry(deliveryInfoDO.getDeliveryCountry());
        dto.setDeliveryCity(deliveryInfoDO.getDeliveryCity());
        dto.setDeliveryPostalCode(deliveryInfoDO.getDeliveryPostalCode());
        dto.setDeliveryFee(toDecimal(
            deliveryInfoDO.getDeliveryFee(),
            deliveryInfoDO.getCurrency(),
            deliveryInfoDO.getCurrencyScale()
        ));
        dto.setReceiverName(deliveryInfoDO.getReceiverName());
        dto.setReceiverPhone(deliveryInfoDO.getReceiverPhone());
        return dto;
    }
}