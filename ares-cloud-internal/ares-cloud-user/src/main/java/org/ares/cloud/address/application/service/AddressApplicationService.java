package org.ares.cloud.address.application.service;

import org.ares.cloud.address.application.command.AddressCommand;
import org.ares.cloud.address.application.converter.AddressConverter;
import org.ares.cloud.address.application.dto.AddressDTO;
import org.ares.cloud.address.domain.model.aggregate.UserAddress;
import org.ares.cloud.address.domain.model.valueobject.Address;
import org.ares.cloud.address.domain.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 地址应用服务
 */
@Service
@RequiredArgsConstructor
public class AddressApplicationService {

    private final UserAddressRepository userAddressRepository;
    private final AddressConverter addressConverter;
    private final AddressClearService addressClearService;

    /**
     * 添加地址
     */
    @Transactional(rollbackFor = Exception.class)
    public AddressDTO addAddress(String userId, AddressCommand command) {
        // 转换为领域对象
        Address address = addressConverter.toAddress(command);
        
        // 创建聚合根
        UserAddress userAddress = UserAddress.builder()
                .userId(userId)
                .address(address)
                .createTime(System.currentTimeMillis())
                .version(0)
                .deleted(false)
                .build();

        // 验证
        userAddress.validate();

        // 拆到新事务里执行清除逻辑，避免并行模式下多次修改同一表
        if (command.getIsDefault() && command.getIsInvoiceAddress()) {
            addressClearService.clearDefaults(userId, true, false, false);
        } else {
            if (address.isDefault()) {
                addressClearService.clearDefaults(userId, false, true, false);
            }
            if (address.isInvoiceAddress()) {
                addressClearService.clearDefaults(userId, false, false, true);
            }
        }

        // 保存
        userAddressRepository.save(userAddress);
        
        // 转换为DTO返回
        return addressConverter.toDTO(userAddress);
    }

    /**
     * 更新地址
     */
    @Transactional(rollbackFor = Exception.class)
    public AddressDTO updateAddress(String id, AddressCommand command) {
        // 查询地址
        UserAddress userAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("地址不存在"));

        String userId = userAddress.getUserId();
        // 转换为领域对象
        Address address = addressConverter.toAddress(command);

        // 在新事务里执行清除逻辑，避免同一事务内对并行表多次DML
        if (command.getIsDefault() && command.getIsInvoiceAddress()) {
            addressClearService.clearDefaults(userId, true, false, false);
        } else {
            if (address.isDefault()) {
                addressClearService.clearDefaults(userId, false, true, false);
            }
            if (address.isInvoiceAddress()) {
                addressClearService.clearDefaults(userId, false, false, true);
            }
        }


        // 更新地址
        userAddress.update(address);
        
        // 保存
        userAddressRepository.update(userAddress);
        
        // 转换为DTO返回
        return addressConverter.toDTO(userAddress);
    }

    /**
     * 删除地址
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteAddress(String id) {
        userAddressRepository.delete(id);
    }




}