package org.ares.cloud.api.user;

import org.ares.cloud.api.user.dto.ChangePasswordReq;
import org.ares.cloud.api.user.dto.RecoverPasswordRequest;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.api.user.fallback.UserServerClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hugo
 * @version 1.0
 * @description: 客户端
 * @date 2024/10/7 23:30
 */
@FeignClient(name = "user-service",contextId = "userServerClient",configuration = FeignConfig.class,fallback = UserServerClientFallback.class)
public interface UserServerClient {
    /**
     * 根据账号获取用户
     * @param account 账号
     * @return 用户信息
     */
    @GetMapping("/internal/user/v1/users/get_by_account")
    UserDto loadByAccount(@RequestParam("account") String account);
    /**
     /**
     * 加载和校验账号密码
     * @param account 长啊后
     * @param password 密码
     * @return 用户
     */
    @GetMapping("/internal/user/v1/users/get_and_chick_account")
    UserDto loadAndChickPassword(@RequestParam("account") String account,@RequestParam("password") String password);
    /**
     * 保存账号
     * @param dto 信息
     * @return 用户id
     */
    @PostMapping("/internal/user/v1/users")
    String save(@RequestBody UserDto dto);

    /**
     * 获取用户详情
     * @param id 用户id
     * @return 用户详情
     */
    @GetMapping("/internal/user/v1/users/{id}")
    UserDto get(@PathVariable("id") String id);


    /**
     * 更新用户信息
     * @param id 用户id
     * @return 用户详情
     */
    @GetMapping("/internal/user/v1/users/updateUserById")
    UserDto updateUserById(@RequestParam("id") String id,@RequestParam("identity") Integer identity);
    /**
     * 修改用户密码
     * @param request 请求数据
     * @return 结果
     */
    @PutMapping("/internal/user/v1/users/change_password")
    String changePassword(@RequestBody ChangePasswordReq request);
    /**
     * 找回用户密码
     * @param request 请求数据
     * @return 结果
     */
    @PutMapping("/internal/user/v1/users/recover_password")
    String recoverPassword(@RequestBody RecoverPasswordRequest request);
    /**
     * 查询不存在则创建临时用户
     * @param phone 手机号
     * @param countryCode 国家编号
     * @return 用户ID
     */
    @GetMapping("/internal/user/v1/users/temporary")
    UserDto getOrCreateTemporaryUser( @RequestParam("countryCode") String countryCode,@RequestParam("phone") String phone);
}
