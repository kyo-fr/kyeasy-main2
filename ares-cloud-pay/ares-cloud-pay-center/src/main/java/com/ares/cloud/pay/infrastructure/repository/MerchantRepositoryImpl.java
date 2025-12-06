package com.ares.cloud.pay.infrastructure.repository;

import com.ares.cloud.pay.domain.model.Merchant;
import com.ares.cloud.pay.domain.repository.MerchantRepository;
import com.ares.cloud.pay.infrastructure.persistence.converter.MerchantConverter;
import com.ares.cloud.pay.infrastructure.persistence.entity.MerchantEntity;
import com.ares.cloud.pay.infrastructure.persistence.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商户仓储实现类
 */
@Repository
public class MerchantRepositoryImpl implements MerchantRepository {
    
    @Autowired
    private MerchantMapper merchantMapper;
    
    @Autowired
    private MerchantConverter merchantConverter;
    
    @Override
    public void save(Merchant merchant) {
        MerchantEntity entity = merchantConverter.toEntity(merchant);
        merchantMapper.insert(entity);
    }
    
    @Override
    public Merchant findById(String id) {
        MerchantEntity entity = merchantMapper.selectById(id);
        return merchantConverter.toDto(entity);
    }
    
    @Override
    public Merchant findByMerchantNo(String merchantNo) {
        MerchantEntity entity = merchantMapper.findByMerchantNo(merchantNo);
        return merchantConverter.toDto(entity);
    }
    
    @Override
    public List<Merchant> findByTenantId(String tenantId) {
        List<MerchantEntity> entities = merchantMapper.findByTenantId(tenantId);
        return merchantConverter.listToDto(entities);
    }
    
    @Override
    public List<Merchant> findByStatus(String status) {
        List<MerchantEntity> entities = merchantMapper.findByStatus(status);
        return merchantConverter.listToDto(entities);
    }
    
    @Override
    public void updateStatus(String id, String status) {
        MerchantEntity entity = new MerchantEntity();
        entity.setId(id);
        entity.setStatus(status);
        merchantMapper.updateById(entity);
    }
    
    @Override
    public void updateSupportedRegions(String id, List<String> supportedRegions) {
        Merchant merchant = findById(id);
        if (merchant != null) {
            merchant.setSupportedRegions(supportedRegions);
            MerchantEntity entity = merchantConverter.toEntity(merchant);
            merchantMapper.updateById(entity);
        }
    }
    
    @Override
    public void updatePayPassword(String id, String payPassword) {
        MerchantEntity entity = new MerchantEntity();
        entity.setId(id);
        entity.setPayPassword(payPassword);
        merchantMapper.updateById(entity);
    }
    
    @Override
    public void updatePaySecret(String id, String paySecret) {
        MerchantEntity entity = new MerchantEntity();
        entity.setId(id);
        entity.setPaySecret(paySecret);
        merchantMapper.updateById(entity);
    }
    
    @Override
    public void updateBasicInfo(String id, String merchantName, String contactPerson, 
                               String contactPhone, String contactEmail, String address, 
                               String businessLicense, String legalRepresentative) {
        MerchantEntity entity = new MerchantEntity();
        entity.setId(id);
        entity.setMerchantName(merchantName);
        entity.setContactPerson(contactPerson);
        entity.setContactPhone(contactPhone);
        entity.setContactEmail(contactEmail);
        entity.setAddress(address);
        entity.setBusinessLicense(businessLicense);
        entity.setLegalRepresentative(legalRepresentative);
        merchantMapper.updateById(entity);
    }
} 