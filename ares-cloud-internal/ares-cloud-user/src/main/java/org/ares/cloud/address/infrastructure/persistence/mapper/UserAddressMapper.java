package org.ares.cloud.address.infrastructure.persistence.mapper;

import org.apache.ibatis.annotations.*;
import org.ares.cloud.address.infrastructure.persistence.entity.UserAddressEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 用户地址Mapper接口
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddressEntity> {
    
    /**
     * 更新用户地址（带事务隔离级别设置）
     */
    @Update("UPDATE user_address SET user_id = #{userId}, type = #{type}, name = #{name}, phone = #{phone}, " +
            "company_name = #{companyName}, business_license_number = #{businessLicenseNumber}, city = #{city}, detail = #{detail}, latitude = #{latitude}, " +
            "longitude = #{longitude}, is_default = #{isDefault}, is_enabled = #{isEnabled}, " +
            "is_invoice_address = #{isInvoiceAddress}, create_time = #{createTime}, " +
            "update_time = #{updateTime}, version = version + 1 " +
            "WHERE id = #{id} AND version = #{version} AND deleted = 0")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    int updateWithVersion(UserAddressEntity entity);
    
    /**
     * 清除用户的所有默认地址
     */
    @Update("UPDATE user_address SET is_default = 0 WHERE user_id = #{userId} AND is_default = 1")
    void clearDefaultAddress(@Param("userId") String userId);
    
    /**
     * 清除用户的所有默认发票地址
     */
    @Update("UPDATE user_address SET is_invoice_address = 0 WHERE user_id = #{userId} AND is_invoice_address = 1")
    void clearDefaultInvoiceAddress(@Param("userId") String userId);
    /**
     * 清除用户的所有默认发票地址
     */
    @Update("UPDATE user_address SET is_default = 0,is_invoice_address = 0 WHERE user_id = #{userId} AND is_invoice_address = 1")
    void clearAllDefaultInvoiceAddress(@Param("userId") String userId);
    /**
     * 根据用户ID查询地址列表
     */
    @Select("SELECT * FROM user_address WHERE user_id = #{userId} AND deleted = 0")
    List<UserAddressEntity> selectByUserId(@Param("userId") String userId);
    
    /**
     * 查询用户的默认地址
     */
    @Select("SELECT * FROM user_address WHERE user_id = #{userId} AND is_default = 1 AND deleted = 0 ")
    List<UserAddressEntity> selectDefaultByUserId(@Param("userId") String userId);
}