package org.ares.cloud.platformInfo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MerchantInfoMapper {
    //根据商户id更新商户总存储量
    @Update("update merchant_info set SUM_MEMORY=#{sumMemory}  where id=#{id}")
    public void updateSumMemory(@Param("sumMemory") Integer sumMemory,@Param("id") String  id);
}
