package org.ares.cloud.address.infrastructure.persistence.repository;

import org.ares.cloud.address.application.query.AddressQuery;
import org.ares.cloud.address.domain.model.aggregate.UserAddress;
import org.ares.cloud.address.domain.repository.UserAddressRepository;
import org.ares.cloud.address.infrastructure.persistence.converter.UserAddressConverter;
import org.ares.cloud.address.infrastructure.persistence.entity.UserAddressEntity;
import org.ares.cloud.address.infrastructure.persistence.mapper.UserAddressMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户地址仓储实现类
 */
@Repository
@RequiredArgsConstructor
public class UserAddressRepositoryImpl extends BaseServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressRepository {

    private final UserAddressMapper userAddressMapper;
    private final UserAddressConverter userAddressConverter;

    @Override
    public void save(UserAddress userAddress) {
        UserAddressEntity entity = userAddressConverter.toEntity(userAddress);
        userAddressMapper.insert(entity);
    }

    @Override
    public Optional<UserAddress> findById(String id) {
        UserAddressEntity entity = userAddressMapper.selectById(id);
        return Optional.ofNullable(entity).map(userAddressConverter::toDomain);
    }

    @Override
    public List<UserAddress> findByUserId(String userId) {
        List<UserAddressEntity> entities = userAddressMapper.selectByUserId(userId);
        return entities.stream()
                .filter(entity -> entity.getDeleted() == 0)
                .map(userAddressConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserAddress> findDefaultByUserId(String userId) {
        List<UserAddressEntity> entitys = userAddressMapper.selectDefaultByUserId(userId);
        if (entitys.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(entitys.get(0)).map(userAddressConverter::toDomain);
    }

    @Override
    public void update(UserAddress userAddress) {
        UserAddressEntity entity = userAddressConverter.toEntity(userAddress);
       this.updateById(entity);
    }

    @Override
    public void delete(String id) {
        // 逻辑删除，将deleted字段设置为true
        userAddressMapper.deleteById(id);
    }

    @Override
    public void clearDefaultAddress(String userId) {
        userAddressMapper.clearDefaultAddress(userId);
    }
    
    @Override
    public void clearDefaultInvoiceAddress(String userId) {
        userAddressMapper.clearDefaultInvoiceAddress(userId);
    }
    @Override
    public void clearAllDefaultInvoiceAddress(String userId) {
        userAddressMapper.clearAllDefaultInvoiceAddress(userId);
    }

    
    @Override
    public List<UserAddress> findByQuery(String userId, AddressQuery query) {
        LambdaQueryWrapper<UserAddressEntity> wrapper = Wrappers.lambdaQuery(UserAddressEntity.class)
                .eq(UserAddressEntity::getUserId, userId)
                .eq(UserAddressEntity::getDeleted, false);
        
        // 根据查询条件添加过滤
        if (query.getType() != null) {
            wrapper.eq(UserAddressEntity::getType, query.getType());
        }
        
        if (query.getIsDefault() != null) {
            wrapper.eq(UserAddressEntity::getIsDefault, query.getIsDefault());
        }
        
        if (query.getIsEnabled() != null) {
            wrapper.eq(UserAddressEntity::getIsEnabled, query.getIsEnabled());
        }
        
        if (query.getIsInvoiceAddress() != null) {
            wrapper.eq(UserAddressEntity::getIsInvoiceAddress, query.getIsInvoiceAddress());
        }
        
        List<UserAddressEntity> entities = userAddressMapper.selectList(wrapper);
        return entities.stream()
                .map(userAddressConverter::toDomain)
                .collect(Collectors.toList());
    }
}