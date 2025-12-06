package com.ares.cloud.pay.infrastructure.persistence.converter;

import com.ares.cloud.pay.domain.model.Merchant;
import com.ares.cloud.pay.infrastructure.persistence.entity.MerchantEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ares.cloud.common.convert.BaseConvert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商户转换器
 */
@Component
public class MerchantConverter implements BaseConvert<MerchantEntity, Merchant> {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public Merchant toDto(MerchantEntity entity) {
        if (entity == null) {
            return null;
        }
        Merchant merchant = new Merchant();
        merchant.setId(entity.getId());
        merchant.setMerchantNo(entity.getMerchantNo());
        merchant.setMerchantName(entity.getMerchantName());
        merchant.setMerchantType(entity.getMerchantType());
        merchant.setStatus(entity.getStatus());
        merchant.setPaySecret(entity.getPaySecret());
        merchant.setPayPassword(entity.getPayPassword());
        merchant.setContactPerson(entity.getContactPerson());
        merchant.setContactPhone(entity.getContactPhone());
        merchant.setContactEmail(entity.getContactEmail());
        merchant.setAddress(entity.getAddress());
        merchant.setBusinessLicense(entity.getBusinessLicense());
        merchant.setLegalRepresentative(entity.getLegalRepresentative());
        merchant.setCreateTime(entity.getCreateTime());
        merchant.setUpdateTime(entity.getUpdateTime());
        
        // 处理支持的支付区域
        if (entity.getSupportedRegions() != null) {
            try {
                List<String> regions = objectMapper.readValue(
                    entity.getSupportedRegions(),
                    new TypeReference<List<String>>() {}
                );
                merchant.setSupportedRegions(regions);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse supported regions", e);
            }
        }
        
        return merchant;
    }
    
    @Override
    public List<Merchant> listToDto(List<MerchantEntity> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public MerchantEntity toEntity(Merchant dto) {
        if (dto == null) {
            return null;
        }
        MerchantEntity entity = new MerchantEntity();
        entity.setId(dto.getId());
        entity.setMerchantNo(dto.getMerchantNo());
        entity.setMerchantName(dto.getMerchantName());
        entity.setMerchantType(dto.getMerchantType());
        entity.setStatus(dto.getStatus());
        entity.setPaySecret(dto.getPaySecret());
        entity.setPayPassword(dto.getPayPassword());
        entity.setContactPerson(dto.getContactPerson());
        entity.setContactPhone(dto.getContactPhone());
        entity.setContactEmail(dto.getContactEmail());
        entity.setAddress(dto.getAddress());
        entity.setBusinessLicense(dto.getBusinessLicense());
        entity.setLegalRepresentative(dto.getLegalRepresentative());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        
        // 处理支持的支付区域
        if (dto.getSupportedRegions() != null) {
            try {
                entity.setSupportedRegions(objectMapper.writeValueAsString(dto.getSupportedRegions()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to serialize supported regions", e);
            }
        }
        
        return entity;
    }
    
    @Override
    public List<MerchantEntity> listToEntities(List<Merchant> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 