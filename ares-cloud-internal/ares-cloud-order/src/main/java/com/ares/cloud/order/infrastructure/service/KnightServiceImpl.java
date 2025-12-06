package com.ares.cloud.order.infrastructure.service;

import com.ares.cloud.order.domain.model.valueobject.KnightInfo;
import com.ares.cloud.order.domain.repository.KnightRepository;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.api.user.UserServerClient;
import org.ares.cloud.api.user.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/24 20:03
 */
@Component
@RequiredArgsConstructor
public class KnightServiceImpl implements KnightRepository {
    /**
     * 用户服务
     */
    private final UserServerClient userServerClient;
    @Override
    public KnightInfo findById(String Id) {
        UserDto user = userServerClient.get(Id);
        if(user == null) {
            return null;
        }
        KnightInfo knightInfo = new KnightInfo();
        knightInfo.setId(Id);
        knightInfo.setName(user.getNickname());
        knightInfo.setPhone(user.getPhone());
        knightInfo.setCountryCode(user.getCountryCode());
        return knightInfo;
    }
}
