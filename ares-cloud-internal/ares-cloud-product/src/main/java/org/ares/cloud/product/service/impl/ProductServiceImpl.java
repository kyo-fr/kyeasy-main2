package org.ares.cloud.product.service.impl;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.repository.ProductBaseInfoRepository;
import org.ares.cloud.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{


    @Resource
    private ProductBaseInfoRepository productBaseInfoRepository;

    @Override
    public void update(ProductBaseInfoEntity entity) {
        this.productBaseInfoRepository.updateById(entity);
    }

    @Override
    public ProductBaseInfoEntity getById(String id) {
        return this.productBaseInfoRepository.selectById(id);
    }



}
