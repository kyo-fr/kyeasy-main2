package org.ares.cloud.address.application.converter;

import org.ares.cloud.address.application.command.AddressCommand;
import org.ares.cloud.address.application.dto.AddressDTO;
import org.ares.cloud.address.domain.model.aggregate.UserAddress;
import org.ares.cloud.address.domain.model.valueobject.Address;

import java.util.List;

/**
 * 地址转换器
 */
public interface AddressConverter {

    /**
     * 命令对象转地址值对象
     */
    Address toAddress(AddressCommand command);

    /**
     * 聚合根转DTO
     */
    AddressDTO toDTO(UserAddress userAddress);

    /**
     * 聚合根列表转DTO列表
     */
    List<AddressDTO> toDTOList(List<UserAddress> userAddresses);
}