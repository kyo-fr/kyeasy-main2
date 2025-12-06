package org.ares.cloud.businessId.convert;

import org.ares.cloud.businessId.dto.BusinessIdDto;
import org.ares.cloud.businessId.entity.BusinessIdEntity;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统业务ID转换器实现类
 * 手动实现的转换器，替代 MapStruct 自动生成的实现
 * 
 * @author ares-cloud
 * @version 1.0.0
 * @date 2024-10-01
 */
@Primary
@Component
public class BusinessIdConvertImpl implements BusinessIdConvert {

    @Override
    public TenantDto toBaseDto(TenantEntity entity) {
        if (entity == null) {
            return null;
        }

        TenantDto tenantDto = new TenantDto();
        
        // 基础字段映射
        tenantDto.setId(entity.getId());
        tenantDto.setCreator(entity.getCreator());
        tenantDto.setCreateTime(entity.getCreateTime());
        tenantDto.setUpdater(entity.getUpdater());
        tenantDto.setUpdateTime(entity.getUpdateTime());
        tenantDto.setVersion(entity.getVersion());
        tenantDto.setTenantId(entity.getTenantId());

        return tenantDto;
    }

    @Override
    public TenantEntity toBaseEntity(TenantDto dto) {
        if (dto == null) {
            return null;
        }

        TenantEntity tenantEntity = new TenantEntity();
        
        // 基础字段映射
        tenantEntity.setId(dto.getId());
        tenantEntity.setCreator(dto.getCreator());
        tenantEntity.setCreateTime(dto.getCreateTime());
        tenantEntity.setUpdater(dto.getUpdater());
        tenantEntity.setUpdateTime(dto.getUpdateTime());
        tenantEntity.setVersion(dto.getVersion());
        tenantEntity.setTenantId(dto.getTenantId());

        return tenantEntity;
    }

    @Override
    public BusinessIdDto toDto(BusinessIdEntity entity) {
        if (entity == null) {
            return null;
        }

        BusinessIdDto businessIdDto = new BusinessIdDto();
        
        // 继承父类字段映射
        businessIdDto.setId(entity.getId());
        businessIdDto.setCreator(entity.getCreator());
        businessIdDto.setCreateTime(entity.getCreateTime());
        businessIdDto.setUpdater(entity.getUpdater());
        businessIdDto.setUpdateTime(entity.getUpdateTime());
        businessIdDto.setVersion(entity.getVersion());
        // BusinessIdEntity 继承 BaseEntity，没有 tenantId 字段
        
        // 业务字段映射
        businessIdDto.setModuleName(entity.getModuleName());
        businessIdDto.setPrefix(entity.getPrefix());
        businessIdDto.setMaxSequence(entity.getMaxSequence());
        businessIdDto.setCycleType(entity.getCycleType());
        businessIdDto.setCurrentDate(entity.getCurrentDate());
        businessIdDto.setDateTemp(entity.getDateTemp());

        return businessIdDto;
    }

    @Override
    public List<BusinessIdDto> listToDto(List<BusinessIdEntity> list) {
        if (list == null) {
            return null;
        }

        List<BusinessIdDto> result = new ArrayList<>(list.size());
        for (BusinessIdEntity entity : list) {
            result.add(toDto(entity));
        }

        return result;
    }

    @Override
    public BusinessIdEntity toEntity(BusinessIdDto dto) {
        if (dto == null) {
            return null;
        }

        BusinessIdEntity businessIdEntity = new BusinessIdEntity();
        
        // 继承父类字段映射
        businessIdEntity.setId(dto.getId());
        businessIdEntity.setCreator(dto.getCreator());
        businessIdEntity.setCreateTime(dto.getCreateTime());
        businessIdEntity.setUpdater(dto.getUpdater());
        businessIdEntity.setUpdateTime(dto.getUpdateTime());
        businessIdEntity.setVersion(dto.getVersion());
        // BusinessIdEntity 继承 BaseEntity，没有 tenantId 字段
        
        // 业务字段映射
        businessIdEntity.setModuleName(dto.getModuleName());
        businessIdEntity.setPrefix(dto.getPrefix());
        businessIdEntity.setMaxSequence(dto.getMaxSequence());
        businessIdEntity.setCycleType(dto.getCycleType());
        businessIdEntity.setCurrentDate(dto.getCurrentDate());
        businessIdEntity.setDateTemp(dto.getDateTemp());

        return businessIdEntity;
    }

    @Override
    public List<BusinessIdEntity> listToEntities(List<BusinessIdDto> list) {
        if (list == null) {
            return null;
        }

        List<BusinessIdEntity> result = new ArrayList<>(list.size());
        for (BusinessIdDto dto : list) {
            result.add(toEntity(dto));
        }

        return result;
    }
}
