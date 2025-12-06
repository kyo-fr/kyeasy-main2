package org.ares.cloud.address.application.service;

import org.ares.cloud.address.application.converter.AddressConverter;
import org.ares.cloud.address.application.dto.AddressDTO;
import org.ares.cloud.address.application.query.AddressQuery;
import org.ares.cloud.address.domain.model.aggregate.UserAddress;
import org.ares.cloud.address.domain.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 地址查询服务
 * 遵循CQRS模式，专门处理地址相关的查询操作
 */
@Service
@RequiredArgsConstructor
public class AddressQueryService {

    private final UserAddressRepository userAddressRepository;
    private final AddressConverter addressConverter;

    /**
     * 根据查询条件查询地址
     * 
     * @param userId 用户ID
     * @param query 查询条件
     * @return 地址列表
     */
    public List<AddressDTO> findAddresses(String userId, AddressQuery query) {
        // 如果查询条件为null，则查询所有地址
        if (query == null) {
            return getAddressList(userId);
        }
        
        // 如果查询默认地址
        if (query.getIsDefault() != null && query.getIsDefault()) {
            Optional<AddressDTO> defaultAddress = getDefaultAddress(userId);
            return defaultAddress.map(List::of).orElse(List.of());
        }
        
        // 根据查询条件查询地址
        List<UserAddress> addresses = userAddressRepository.findByQuery(userId, query);
        return addressConverter.toDTOList(addresses);
    }

    /**
     * 获取地址列表
     * 
     * @param userId 用户ID
     * @return 地址列表
     */
    public List<AddressDTO> getAddressList(String userId) {
        List<UserAddress> addresses = userAddressRepository.findByUserId(userId);
        return addressConverter.toDTOList(addresses);
    }

    /**
     * 获取默认地址
     * 
     * @param userId 用户ID
     * @return 默认地址，可能为空
     */
    public Optional<AddressDTO> getDefaultAddress(String userId) {
        return userAddressRepository.findDefaultByUserId(userId)
                .map(addressConverter::toDTO);
    }

    /**
     * 获取地址详情
     * 
     * @param id 地址ID
     * @return 地址详情
     * @throws IllegalArgumentException 如果地址不存在
     */
    public AddressDTO getAddress(String id) {
        return userAddressRepository.findById(id)
                .map(addressConverter::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("地址不存在"));
    }
}