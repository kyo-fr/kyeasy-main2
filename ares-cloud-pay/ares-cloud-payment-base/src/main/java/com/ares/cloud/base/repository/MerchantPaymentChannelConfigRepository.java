package com.ares.cloud.base.repository;

import com.ares.cloud.base.entity.MerchantPaymentChannelConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道配置 数据仓库
* @version 1.0.0
* @date 2025-05-13
*/
@Mapper
public interface MerchantPaymentChannelConfigRepository extends BaseMapper<MerchantPaymentChannelConfigEntity> {

    /**
     * 根据商户ID和支付渠道key 查询支付渠道配置
     */
    @Select("select * from merchant_payment_channel_config where merchant_id = #{merchantId} and payment_merchant = #{paymentMerchant}")
    MerchantPaymentChannelConfigEntity selectByMerchantIdAndPaymentMerchant(@Param("merchantId") String merchantId, @Param("paymentMerchant") String paymentMerchant);

}