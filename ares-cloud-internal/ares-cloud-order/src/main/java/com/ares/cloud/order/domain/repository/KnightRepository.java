package com.ares.cloud.order.domain.repository;

import com.ares.cloud.order.domain.model.valueobject.KnightInfo;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/24 20:01
 */
public interface KnightRepository {
    /**
     * 根据id查询骑士信息
     * @param Id
     * @return
     */
    KnightInfo findById(String Id);
}
