package org.ares.cloud.rider.repository;

import org.apache.ibatis.annotations.Select;
import org.ares.cloud.rider.entity.RiderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 骑手 数据仓库
* @version 1.0.0
* @date 2025-08-26
*/
@Mapper
public interface RiderRepository extends BaseMapper<RiderEntity> {

    /**
     * 根据国家编码和手机号查询
     * @param countryCode  国家编码
     * @param phone 手机号
     */
    @Select("select * from rider where country_code = #{countryCode} and phone = #{phone} and deleted = 0")
    RiderEntity findByCountryCodeAndPhone(String countryCode, String phone);

}