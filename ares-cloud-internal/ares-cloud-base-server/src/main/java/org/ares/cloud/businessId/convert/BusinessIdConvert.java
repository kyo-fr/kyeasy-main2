package org.ares.cloud.businessId.convert;

import org.ares.cloud.businessId.entity.BusinessIdEntity;
import org.ares.cloud.businessId.dto.BusinessIdDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 系统业务id 转换器
* @version 1.0.0
* @date 2024-10-13
*/
public interface BusinessIdConvert {
    /**
    * 父类转换
    * @param entity 实体
    * @return  数据传输对象
    */
    TenantDto toBaseDto(TenantEntity entity);

    /**
    * 父类转换
    * @param dto 数据传输对象
    * @return 实体
    */
    TenantEntity toBaseEntity(TenantDto dto);

    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */

    BusinessIdDto toDto(BusinessIdEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< BusinessIdDto> listToDto(List<BusinessIdEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    BusinessIdEntity toEntity( BusinessIdDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<BusinessIdEntity> listToEntities(List< BusinessIdDto> list);
}