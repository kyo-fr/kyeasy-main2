package com.ares.cloud.base.convert;

import com.ares.cloud.base.dto.SysPaymentChannelDto;
import com.ares.cloud.base.entity.SysPaymentChannelEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.mapstruct.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 支付类型 转换器
* @version 1.0.0
* @date 2025-01-03
*/
@Mapper(componentModel = "spring")
public interface SysPaymentChannelConvert extends BaseConvert<SysPaymentChannelEntity, SysPaymentChannelDto> {

}