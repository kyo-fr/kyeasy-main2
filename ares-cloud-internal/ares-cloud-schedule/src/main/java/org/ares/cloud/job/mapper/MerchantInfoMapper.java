package org.ares.cloud.job.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.ares.cloud.job.entity.MerchantInfoEntity;

import java.util.List;

@Mapper
public interface MerchantInfoMapper {

    @Select("SELECT user_id,SUM_MEMORY  FROM MERCHANT_INFO where SUM_MEMORY <= 0.4 ")
    List<MerchantInfoEntity>  getMerchantInfoBySumMemory();
}
