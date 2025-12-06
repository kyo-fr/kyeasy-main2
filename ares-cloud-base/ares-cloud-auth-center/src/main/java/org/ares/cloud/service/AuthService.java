package org.ares.cloud.service;

import org.ares.cloud.api.auth.dto.AccessTokenClaims;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.dto.*;

/**
 * @author hugo
 * @version 1.0
 * @description: 认证服务
 * @date 2024/10/7 23:48
 */
public interface AuthService {
    /**
     * 校验用户是否存在
     * @param countryCode 国家代码
     * @param phone 手机号
     * @return 是否存在
     */
    boolean checkAccount(String countryCode,String phone);

    /**
     * firebase验证
     * @param request 请求参数
     * @return 校验的签名
     */
    String verifyFirebaseCode(FirebaseCodeVerifyRequest request);

    /**
     * 验证码校验
     * @param request 验证码校验请求
     * @return 结果
     */
    String verifyCode(VerificationCodeVerifyRequest request);
    /**
     * 注册
     * @param request 注册参数
     */
    void register(RegisterRequest request);

    /**
     * 登录
     * @param request 请求信息
     * @return 登录返回
     */
    LoginResponse login(LoginRequest request);
    /**
     * 平台登录
     * @param request 请求信息
     * @return 登录返回
     */
    LoginResponse platformLogin(LoginRequest request);

    /**
     * 骑手登录
     * @param request 登录参数
     */
    LoginResponse riderLogin(LoginRequest request);
    /**
     * 刷新token
     * @param refreshToken token
     * @return 登录返回
     */
    LoginResponse refreshToken(String refreshToken);

    /**
     * 验证token
     * @param token token
     * @return 解析的结果
     */
    AccessTokenClaims validateToken(String token);

    /**
     * 修改米阿么
     * @param request 请求数据
     */
    void changePassword(ChangePasswordRequest request);

    /**
     * 当前登录信息
     * @return 用户信息
     */
    UserDto info();
    /**
     * 找回密码
     * @param request 请求数据
     */
    void recoverPassword(RecoverYourPasswordRequest request);
    /**
     * 退出登录
     */
    void logout();
}
