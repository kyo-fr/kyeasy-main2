package com.ares.cloud.payment.infrastructure.converter;

import com.ares.cloud.payment.application.dto.PartyDTO;
import com.ares.cloud.payment.domain.model.Party;
import com.ares.cloud.payment.infrastructure.persistence.entity.PartyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 交易方转换器
 */
@Component
@RequiredArgsConstructor
public class PartyConverter {
    
    /**
     * 将领域模型转换为实体
     *
     * @param party 交易方领域模型
     * @return 交易方实体
     */
    public PartyEntity toEntity(Party party) {
        if (party == null) {
            return null;
        }
        
        PartyEntity entity = new PartyEntity();
        entity.setId(party.getId());
        entity.setPartyId(party.getPartyId());
        entity.setInvoiceId(party.getInvoiceId());
        entity.setName(party.getName());
        entity.setPartyType(party.getPartyType());
        entity.setUserStatus(party.getUserStatus());
        entity.setTaxId(party.getTaxId());
        entity.setAddress(party.getAddress());
        entity.setPostalCode(party.getPostalCode());
        entity.setPhone(party.getPhone());
        entity.setEmail(party.getEmail());
        entity.setCountryCode(party.getCountryCode());
        entity.setCountry(party.getCountry());
        entity.setCity(party.getCity());
        entity.setCreateTime(System.currentTimeMillis());
        entity.setUpdateTime(System.currentTimeMillis());
        entity.setIsDeleted(0);
        
        return entity;
    }
    
    /**
     * 将实体转换为领域模型
     *
     * @param entity 交易方实体
     * @return 交易方领域模型
     */
    public Party toDomain(PartyEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Party.builder()
                .id(entity.getId())
                .partyId(entity.getPartyId())
                .invoiceId(entity.getInvoiceId())
                .name(entity.getName())
                .partyType(entity.getPartyType())
                .userStatus(entity.getUserStatus())
                .taxId(entity.getTaxId())
                .address(entity.getAddress())
                .postalCode(entity.getPostalCode())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .countryCode(entity.getCountryCode())
                .country(entity.getCountry())
                .city(entity.getCity())
                .build();
    }
    
    /**
     * 转换为DTO
     */
    public PartyDTO toDTO(PartyEntity entity) {
        if (entity == null) {
            return null;
        }
        
        PartyDTO dto = new PartyDTO();
        dto.setId(entity.getId());
        dto.setPartyId(entity.getPartyId());
        dto.setName(entity.getName());
        dto.setPartyType(entity.getPartyType());
        dto.setTaxId(entity.getTaxId());
        dto.setAddress(entity.getAddress());
        dto.setPostalCode(entity.getPostalCode());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setCountryCode(entity.getCountryCode());
        dto.setCountry(entity.getCountry());
        dto.setCity(entity.getCity());
        
        return dto;
    }
} 