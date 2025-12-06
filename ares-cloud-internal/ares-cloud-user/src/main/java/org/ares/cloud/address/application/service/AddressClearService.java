package org.ares.cloud.address.application.service;

import lombok.RequiredArgsConstructor;
import org.ares.cloud.address.domain.repository.UserAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/8/13 01:07
 */
@Service
@RequiredArgsConstructor
public class AddressClearService {
    private final UserAddressRepository userAddressRepository;
    /**
     * 用新事务执行清除默认地址的逻辑
     * @param userId 用户ID
     * @param clearAllInvoice 是否清除所有发票地址
     * @param clearDefault 是否清除默认地址
     * @param clearInvoice 是否清除默认发票地址
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void clearDefaults(String userId, boolean clearAllInvoice, boolean clearDefault, boolean clearInvoice) {
        if (clearAllInvoice) {
            userAddressRepository.clearAllDefaultInvoiceAddress(userId);
        }
        if (clearDefault) {
            userAddressRepository.clearDefaultAddress(userId);
        }
        if (clearInvoice) {
            userAddressRepository.clearDefaultInvoiceAddress(userId);
        }
    }
}
