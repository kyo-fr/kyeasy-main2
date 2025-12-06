package com.ares.cloud.pay.domain.repository;

import com.ares.cloud.pay.domain.model.Account;

import java.util.List;

/**
 * 账户仓储接口
 */
public interface AccountRepository {
    
    /**
     * 保存账户
     */
    void save(Account account);
    
    /**
     * 根据ID查询账户
     */
    Account findById(String id);

    /**
     * 根据用户ID查询账户
     */
    Account findByUserId(String userId);
    
    /**
     * 根据手机号查询账户
     */
    Account findByPhone(String phone);
    
    /**
     * 根据账号查询账户
     */
    Account findByAccount(String account);
    
    /**
     * 根据国家代码和手机号查询账户
     */
    Account findByCountryCodeAndPhone(String countryCode, String phone);
    
    /**
     * 根据用户ID查询账户列表
     */
    List<Account> findListByUserId(String userId);
    
    /**
     * 更新账户状态
     */
    void updateStatus(String id, String status);
    
    /**
     * 更新账户密码
     */
    void updatePassword(String id, String password);
} 