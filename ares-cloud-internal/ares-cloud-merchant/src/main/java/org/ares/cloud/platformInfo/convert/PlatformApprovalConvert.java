package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.platformInfo.dto.PlatformApprovalDto;
import org.ares.cloud.platformInfo.entity.PlatformApprovalEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 平台审批 转换器
* @version 1.0.0
* @date 2024-10-31
*/
@Mapper(componentModel = "spring")
public interface PlatformApprovalConvert {
    /**
     * 父类转换
     * @param entity 实体
     * @return  数据传输对象
     */
    BaseDto toBaseDto(BaseEntity entity);

    /**
     * 父类转换
     * @param dto 数据传输对象
     * @return 实体
     */
    BaseEntity toBaseEntity(BaseDto dto);

    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */
    @InheritConfiguration(name = "toBaseDto")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "approvalId", source = "approvalId")
    @Mapping(target = "merchantSubscribeId", source = "merchantSubscribeId")
    @Mapping(target = "channelType", source = "channelType")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "subscribeType", source = "subscribeType")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "taxNum", source = "taxNum")
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "month", source = "month")
    @Mapping(target = "overdueDate", source = "overdueDate")
    @Mapping(target = "memory", source = "memory")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "contractId", source = "contractId")
    @Mapping(target = "taxRate", source = "taxRate")
    @Mapping(target = "usableMemory", source = "usableMemory")
    @Mapping(target = "paymentChannelId", source = "paymentChannelId")
    PlatformApprovalDto toDto(PlatformApprovalEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< PlatformApprovalDto> listToDto(List<PlatformApprovalEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "approvalId", source = "approvalId")
    @Mapping(target = "merchantSubscribeId", source = "merchantSubscribeId")
    @Mapping(target = "channelType", source = "channelType")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "subscribeType", source = "subscribeType")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "taxNum", source = "taxNum")
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "month", source = "month")
    @Mapping(target = "overdueDate", source = "overdueDate")
    @Mapping(target = "memory", source = "memory")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "contractId", source = "contractId")
    @Mapping(target = "taxRate", source = "taxRate")
    @Mapping(target = "usableMemory", source = "usableMemory")
    @Mapping(target = "paymentChannelId", source = "paymentChannelId")
    PlatformApprovalEntity toEntity( PlatformApprovalDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformApprovalEntity> listToEntities(List< PlatformApprovalDto> list);
}