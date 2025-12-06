package com.ares.cloud.base.convert;

import com.ares.cloud.base.dto.MerchantPaymentChannelDto;
import com.ares.cloud.base.entity.MerchantPaymentChannelEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.mapstruct.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道 转换器
* @version 1.0.0
* @date 2025-05-13
*/
@Mapper(componentModel = "spring")
public interface MerchantPaymentChannelConvert extends BaseConvert<MerchantPaymentChannelEntity, MerchantPaymentChannelDto> {

}