package org.ares.cloud.api.user.fallback;

import org.ares.cloud.api.user.UserServerClient;
import org.ares.cloud.api.user.dto.ChangePasswordReq;
import org.ares.cloud.api.user.dto.RecoverPasswordRequest;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: UserServerClient 的降级处理
 * 当用户服务不可用时，抛出 ServiceUnavailableException
 * @date 2024/10/7 23:42
 */
@Component
public class UserServerClientFallback implements UserServerClient {
    
    private static final String SERVICE_NAME = "user-service";
    
    @Override
    public UserDto loadByAccount(String account) {
        throw new ServiceUnavailableException(SERVICE_NAME);
    }

    @Override
    public UserDto loadAndChickPassword(String account, String password) {
        throw new ServiceUnavailableException(SERVICE_NAME);
    }

    @Override
    public String save(UserDto dto) {
        throw new ServiceUnavailableException(SERVICE_NAME);
    }

    @Override
    public UserDto get(String id) {
        throw new ServiceUnavailableException(SERVICE_NAME);
    }

    @Override
    public UserDto updateUserById(String id,Integer identity) {
        throw new ServiceUnavailableException(SERVICE_NAME);
    }

    @Override
    public String changePassword(ChangePasswordReq request) {
        throw new ServiceUnavailableException(SERVICE_NAME);
    }

    @Override
    public String recoverPassword(RecoverPasswordRequest request) {
        throw new ServiceUnavailableException(SERVICE_NAME);
    }

    @Override
    public UserDto getOrCreateTemporaryUser(String countryCode, String phone) {
        throw new ServiceUnavailableException(SERVICE_NAME);
    }

}

