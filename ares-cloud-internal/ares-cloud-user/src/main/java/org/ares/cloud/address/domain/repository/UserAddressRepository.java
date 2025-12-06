package org.ares.cloud.address.domain.repository;

import org.ares.cloud.address.application.query.AddressQuery;
import org.ares.cloud.address.domain.model.aggregate.UserAddress;
import java.util.List;
import java.util.Optional;

/**
 * 用户地址仓储接口
 */
public interface UserAddressRepository {
    
    /**
     * 保存用户地址
     */
    void save(UserAddress userAddress);
    
    /**
     * 根据ID查询用户地址
     */
    Optional<UserAddress> findById(String id);
    
    /**
     * 查询用户的所有地址
     */
    List<UserAddress> findByUserId(String userId);
    
    /**
     * 查询用户的默认地址
     */
    Optional<UserAddress> findDefaultByUserId(String userId);
    
    /**
     * 更新用户地址
     */
    void update(UserAddress userAddress);
    
    /**
     * 删除用户地址
     */
    void delete(String id);
    
    /**
     * 取消用户的所有默认地址
     */
    void clearDefaultAddress(String userId);
    
    /**
     * 取消用户的所有默认发票地址
     */
    void clearDefaultInvoiceAddress(String userId);
    /**
     * 取消用户的所有默认发票地址
     */
    void clearAllDefaultInvoiceAddress(String userId);
    /**
     * 根据查询条件查询用户地址
     */
    List<UserAddress> findByQuery(String userId, AddressQuery query);
}