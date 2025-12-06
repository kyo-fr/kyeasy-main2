package org.ares.cloud.address.application.converter;

import org.ares.cloud.address.application.command.AddressCommand;
import org.ares.cloud.address.application.dto.AddressDTO;
import org.ares.cloud.address.domain.model.aggregate.UserAddress;
import org.ares.cloud.address.domain.model.valueobject.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址转换器实现类
 */
@Component
public class AddressConverterImpl implements AddressConverter {

    /**
     * 命令对象转地址值对象
     */
    @Override
    public Address toAddress(AddressCommand command) {
        if (command == null) {
            return null;
        }
        
        return Address.builder()
                .type(command.getType())
                .name(command.getName())
                .phone(command.getPhone())
                .companyName(command.getCompanyName())
                .businessLicenseNumber(command.getBusinessLicenseNumber())
                .country(command.getCountry())
                .city(command.getCity())
                .postalCode(command.getPostalCode())
                .detail(command.getDetail())
                .latitude(command.getLatitude())
                .longitude(command.getLongitude())
                .isDefault(command.getIsDefault() != null ? command.getIsDefault() : false)
                .isEnabled(command.getIsEnabled() != null ? command.getIsEnabled() : true)
                .isInvoiceAddress(command.getIsInvoiceAddress() != null ? command.getIsInvoiceAddress() : false)
                .build();
    }

    /**
     * 聚合根转DTO
     */
    @Override
    public AddressDTO toDTO(UserAddress userAddress) {
        if (userAddress == null) {
            return null;
        }
        
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(userAddress.getId());
        addressDTO.setUserId(userAddress.getUserId());
        
        Address address = userAddress.getAddress();
        if (address != null) {
            addressDTO.setType(address.getType());
            addressDTO.setName(address.getName());
            addressDTO.setPhone(address.getPhone());
            addressDTO.setCompanyName(address.getCompanyName());
            addressDTO.setBusinessLicenseNumber(address.getBusinessLicenseNumber());
            addressDTO.setCountry(address.getCountry());
            addressDTO.setCity(address.getCity());
            addressDTO.setPostalCode(address.getPostalCode());
            addressDTO.setDetail(address.getDetail());
            addressDTO.setLatitude(address.getLatitude());
            addressDTO.setLongitude(address.getLongitude());
            addressDTO.setIsDefault(address.isDefault());
            addressDTO.setIsEnabled(address.isEnabled());
            addressDTO.setIsInvoiceAddress(address.isInvoiceAddress());
        }
        
        addressDTO.setCreateTime(userAddress.getCreateTime());
        addressDTO.setUpdateTime(userAddress.getUpdateTime());
        
        return addressDTO;
    }

    /**
     * 聚合根列表转DTO列表
     */
    @Override
    public List<AddressDTO> toDTOList(List<UserAddress> userAddresses) {
        if (userAddresses == null) {
            return null;
        }
        
        List<AddressDTO> addressDTOList = new ArrayList<>(userAddresses.size());
        for (UserAddress userAddress : userAddresses) {
            addressDTOList.add(toDTO(userAddress));
        }
        
        return addressDTOList;
    }
}