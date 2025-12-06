package com.ares.cloud.base.repository;

import com.ares.cloud.base.entity.MerchantPaymentChannelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道 数据仓库
* @version 1.0.0
* @date 2025-05-13
*/
@Mapper
public interface MerchantPaymentChannelRepository extends BaseMapper<MerchantPaymentChannelEntity> {

    /**
     * 根据商户id删除所有支付渠道
     */
    @Delete("delete from merchant_pay_channel where merchant_id = #{merchantId}")
    void deleteByMerchantId(@Param("merchantId") String merchantId);

}