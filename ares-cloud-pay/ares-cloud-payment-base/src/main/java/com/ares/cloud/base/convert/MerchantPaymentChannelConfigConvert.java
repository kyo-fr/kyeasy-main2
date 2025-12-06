package com.ares.cloud.base.convert;

import com.ares.cloud.base.dto.MerchantPaymentChannelConfigDto;
import com.ares.cloud.base.entity.MerchantPaymentChannelConfigEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.mapstruct.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道配置 转换器
* @version 1.0.0
* @date 2025-05-13
*/
@Mapper(componentModel = "spring")
public interface MerchantPaymentChannelConfigConvert extends BaseConvert<MerchantPaymentChannelConfigEntity, MerchantPaymentChannelConfigDto> {

}