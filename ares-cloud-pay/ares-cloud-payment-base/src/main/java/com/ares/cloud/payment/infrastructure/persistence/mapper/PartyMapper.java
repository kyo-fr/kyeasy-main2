package com.ares.cloud.payment.infrastructure.persistence.mapper;

import com.ares.cloud.payment.infrastructure.persistence.entity.PartyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交易方Mapper接口
 */
@Mapper
public interface PartyMapper extends BaseMapper<PartyEntity> {

} 