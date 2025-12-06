package com.ares.cloud.pay.infrastructure.persistence.mapper;

import com.ares.cloud.pay.infrastructure.persistence.entity.AccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 账户Mapper接口
 */
@Mapper
public interface AccountMapper extends BaseMapper<AccountEntity> {
    
    /**
     * 根据用户ID查询账户
     *
     * @param userId 用户ID
     * @return 账户
     */
    @Select("SELECT * FROM accounts WHERE user_id = #{userId} AND deleted = 0")
    AccountEntity findByUserId(@Param("userId") String userId);
    
    /**
     * 根据手机号查询账户
     *
     * @param phone 手机号
     * @return 账户
     */
    @Select("SELECT * FROM accounts WHERE phone = #{phone} AND deleted = 0")
    AccountEntity findByPhone(@Param("phone") String phone);
    
    /**
     * 根据账号查询账户
     *
     * @param account 账号
     * @return 账户
     */
    @Select("SELECT * FROM accounts WHERE account = #{account} AND deleted = 0")
    AccountEntity findByAccount(@Param("account") String account);
    
    /**
     * 根据国家代码和手机号查询账户
     *
     * @param countryCode 国家代码
     * @param phone 手机号
     * @return 账户
     */
    @Select("SELECT * FROM accounts WHERE country_code = #{countryCode} AND phone = #{phone} AND deleted = 0")
    AccountEntity findByCountryCodeAndPhone(@Param("countryCode") String countryCode, @Param("phone") String phone);
    
    /**
     * 根据用户ID查询账户列表
     *
     * @param userId 用户ID
     * @return 账户列表
     */
    @Select("SELECT * FROM accounts WHERE user_id = #{userId} AND deleted = 0")
    List<AccountEntity> findListByUserId(@Param("userId") String userId);
    
    /**
     * 更新账户状态
     *
     * @param id 账户ID
     * @param status 状态
     */
    @Update("UPDATE accounts SET status = #{status}, update_time = #{updateTime} WHERE id = #{id}")
    void updateStatus(@Param("id") String id, @Param("status") String status, @Param("updateTime") Long updateTime);
    
    /**
     * 更新账户密码
     *
     * @param id 账户ID
     * @param password 密码
     */
    @Update("UPDATE accounts SET password = #{password}, update_time = #{updateTime} WHERE id = #{id}")
    void updatePassword(@Param("id") String id, @Param("password") String password, @Param("updateTime") Long updateTime);
} 