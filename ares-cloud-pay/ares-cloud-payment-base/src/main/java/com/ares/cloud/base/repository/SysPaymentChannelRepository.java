package com.ares.cloud.base.repository;

import com.ares.cloud.base.entity.SysPaymentChannelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 支付渠道 数据仓库
* @version 1.0.0
* @date 2025-05-13
*/
@Mapper
public interface SysPaymentChannelRepository extends BaseMapper<SysPaymentChannelEntity> {
	
}