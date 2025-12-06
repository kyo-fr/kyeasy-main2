package com.ares.cloud.pay.infrastructure.persistence.mapper;

import com.ares.cloud.pay.infrastructure.persistence.entity.WalletEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 钱包Mapper接口
 */
@Mapper
public interface WalletMapper extends BaseMapper<WalletEntity> {
    
    /**
     * 根据所有者ID和支付区域查询钱包
     *
     * @param ownerId 所有者ID
     * @param paymentRegion 支付区域
     * @return 钱包
     */
    @Select("SELECT * FROM wallets WHERE owner_id = #{ownerId} AND payment_region = #{paymentRegion} AND deleted = 0")
    WalletEntity findByOwnerIdAndRegion(@Param("ownerId") String ownerId, @Param("paymentRegion") String paymentRegion);
    
    /**
     * 根据所有者ID查询钱包列表
     *
     * @param ownerId 所有者ID
     * @return 钱包列表
     */
    @Select("SELECT * FROM wallets WHERE owner_id = #{ownerId} AND deleted = 0")
    List<WalletEntity> findByOwnerId(@Param("ownerId") String ownerId);
    
    /**
     * 根据所有者类型查询钱包列表
     *
     * @param ownerType 所有者类型
     * @return 钱包列表
     */
    @Select("SELECT * FROM wallets WHERE owner_type = #{ownerType} AND deleted = 0")
    List<WalletEntity> findByOwnerType(@Param("ownerType") String ownerType);
    
    /**
     * 根据支付区域查询钱包列表
     *
     * @param paymentRegion 支付区域
     * @return 钱包列表
     */
    @Select("SELECT * FROM wallets WHERE payment_region = #{paymentRegion} AND deleted = 0")
    List<WalletEntity> findByPaymentRegion(@Param("paymentRegion") String paymentRegion);
} 