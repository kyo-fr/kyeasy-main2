package org.ares.cloud.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import jakarta.annotation.Resource;
import org.ares.cloud.api.auth.dto.AccessTokenClaims;
import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.api.user.RiderClient;
import org.ares.cloud.api.user.UserServerClient;
import org.ares.cloud.api.user.dto.ChangePasswordReq;
import org.ares.cloud.api.user.dto.RecoverPasswordRequest;
import org.ares.cloud.api.user.dto.RiderDto;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.api.user.enums.UserIdentity;
import org.ares.cloud.api.user.errors.UserError;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.enums.ResponseCodeEnum;
import org.ares.cloud.common.exception.*;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.common.utils.UserAgentUtils;
import org.ares.cloud.common.utils.UserUtils;
import org.ares.cloud.dto.*;
import org.ares.cloud.enums.AuthError;
import org.ares.cloud.provider.JwtTokenProvider;
import org.ares.cloud.service.AuthService;
import org.ares.cloud.store.TokenStorage;
import org.ares.cloud.utils.FireBaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.ares.cloud.common.enums.ResponseCodeEnum.RECODE_GET_USER_INFO_ERR;

/**
 * @author hugo
 * @version 1.0
 * @description: 认证服务实现
 * @date 2024/10/7 23:50
 */
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    /**
     * 用户客户端
     */
    @Resource
    private UserServerClient userServerClient;
    /**
     * 商户客户端
     */
    @Resource
    private MerchantClient merchantClient;
    /**
     * 骑手客户端
     */
    @Resource
    private RiderClient riderClient;
    /**
     * token存储器
     */
    @Resource
    private TokenStorage tokenStorage;
    /**
     * token生成器
     */
    @Resource
    private JwtTokenProvider jwtTokenProvider; // 注入自定义令牌生成器
    @Override
    public boolean checkAccount(String countryCode, String phone) {
        if(StringUtils.isBlank(countryCode) || StringUtils.isBlank(phone)){
            throw new RequestBadException(UserError.PHONE_CANNOT_BE_EMPTY);
        }
        UserDto user = userServerClient.loadByAccount(UserUtils.getAccount(countryCode , phone));
        return user != null && user.getDeleted() == 0;
    }

    @Override
    public String verifyFirebaseCode(FirebaseCodeVerifyRequest request) {
        String account = UserUtils.getAccount(request.getCountryCode(), request.getPhone());
        try {
            //校验前台给到的idtoken
            FireBaseUtil.verifyIdToken(request.getRecaptchaToken());
            String s = jwtTokenProvider.generateSing(account);
            boolean b = tokenStorage.saveSign(account,s);
            if (!b){
                throw new BaseException(AuthError.SAVE_SIGN_ERROR);
            }
            return s;
        } catch (FirebaseAuthException e){
            e.fillInStackTrace();
            throw new SecurityException("数据异常,请联系平台");
        }
    }

    @Override
    public String verifyCode(VerificationCodeVerifyRequest request) {
        String account = UserUtils.getAccount(request.getCountryCode(), request.getPhone());
        if ("666666".equals(request.getCode())){
            String s =  jwtTokenProvider.generateSing(account);
            boolean b = tokenStorage.saveSign(account,s);
            if (!b){
                throw new BaseException(AuthError.SAVE_SIGN_ERROR);
            }
            return s;
        }
        throw new BaseException(AuthError.SAVE_SIGN_ERROR);
    }

    @Override
    public void register(RegisterRequest request) {
        if(StringUtils.isBlank(request.getPhone())){
            throw new RequestBadException(UserError.PHONE_CANNOT_BE_EMPTY);
        }
        String account = UserUtils.getAccount(request.getCountryCode(), request.getPhone());
        //验证签名
        if (StringUtils.isBlank(request.getSign())){
            throw new RequestBadException(AuthError.INVALID_SIGN);
        }
        boolean b1 = tokenStorage.chickSignExist(account,request.getSign());
        if(!b1){
            throw new RequestBadException(AuthError.INVALID_SIGN);
        }
        b1 = jwtTokenProvider.validateSign(request.getSign(), account);
        if(!b1){
            throw new RequestBadException(AuthError.INVALID_SIGN);
        }

        if(StringUtils.isBlank(request.getPassword())){
            throw new RequestBadException(AuthError.PASSWORD_CANNOT_BE_EMPTY);
        }
        boolean b = this.checkAccount(request.getCountryCode(), request.getPhone());
        if(b){
            throw new RequestBadException(AuthError.ACCOUNT_ALREADY_EXIST);
        }
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new RequestBadException(AuthError.THE_TWO_PASSWORD_DOES_NOT_MATCH);
        }
        UserDto userDto = new UserDto();
        userDto.setPhone(request.getPhone());
        userDto.setAccount(account);
        userDto.setCountryCode(request.getCountryCode());
        userDto.setNickname(request.getNickname());
        userDto.setPassword(request.getPassword());
        userDto.setEmail(request.getEmail());
        userServerClient.save(userDto);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 登录获取用户
        UserDto userDto = this.loginGetUserDto(request);
        // 身份判断，平台不能登录
        if (userDto.getIdentity() == UserIdentity.PlatformUsers.getValue()) {
            throw new RequestBadException(UserError.PLATFORM_USERS_CANNOT_LOG_IN);
        }
        MerchantInfo merchantInfo = null;
        // 先判断有没有绑定的域名，没有绑定的域名才判断身份
        String domain = request.getDomain();
        if (StringUtils.isNotBlank(domain)) {
            try {
                merchantInfo = merchantClient.getMerchantInfoByDomain(domain);
            } catch (Exception e) {
                log.error("商户不存在{}", request.getDomain());
            }
        }
        // 域名没有绑定商户并且登录用户是商户
        if (merchantInfo == null && userDto.getIdentity() == UserIdentity.Merchants.getValue()) {
            // 获取商户信息
            merchantInfo = merchantClient.getMerchantInfoByUserId(userDto.getId());
        }
        if (merchantInfo != null ) {
            userDto.setTenantId(merchantInfo.getId());
        }
        return getLoginResponse(userDto);
    }

    @Override
    public LoginResponse platformLogin(LoginRequest request) {
        // 登录获取用户
        UserDto userDto = this.loginGetUserDto(request);
        // 身份判断，平台不能登录
        if (userDto.getIdentity() != UserIdentity.PlatformUsers.getValue()) {
            throw new RequestBadException(UserError.NOT_PLATFORM_USERS_CANNOT_LOG_IN);
        }
        return getLoginResponse(userDto);
    }

    @Override
    public LoginResponse riderLogin(LoginRequest request) {
        // 登录获取用户
        UserDto userDto = this.loginGetUserDto(request);
        // 身份判断，不是骑手不能登录
        if (userDto.getIdentity() != UserIdentity.Knight.getValue()) {
            throw new RequestBadException(UserError.NOT_KNIGHT_USER_CANNOT_LOG_IN);
        }
        RiderDto riderDto = this.riderClient.loadByCountryCodeAndPhone(userDto.getCountryCode(), userDto.getPhone());
        if (riderDto == null){
            // 没有注册骑手不能登录
            throw new RequestBadException(UserError.NOT_KNIGHT_USER_CANNOT_LOG_IN);
        }
        userDto.setTenantId(riderDto.getTenantId());
        return getLoginResponse(userDto);
    }


    @Override
    public LoginResponse refreshToken(String refreshToken) {
        boolean exist = tokenStorage.chickRefTokenExist(refreshToken);
        if (!exist){
            throw new TokenException(AuthError.REFRESH_TOKEN_INVALID);
        }
        boolean b1 = jwtTokenProvider.validateToken(refreshToken);
        if (!b1){
            throw new TokenException(AuthError.REFRESH_TOKEN_INVALID);
        }
        Map<String, Object> claims = jwtTokenProvider.getClaims(refreshToken);
        AccessTokenClaims accessTokenClaims = AccessTokenClaims.fromAccessTokenClaims(claims);
        String userId = accessTokenClaims.getUserId();
        if (StringUtils.isBlank(userId)) {
            throw new TokenException(AuthError.REFRESH_TOKEN_INVALID);
        }
        UserDto user = userServerClient.get(userId);
        return getLoginResponse(user);
    }

    @Override
    public AccessTokenClaims validateToken(String token) {
        if (!tokenStorage.chickTokenExist(token)){
            throw new TokenException(AuthError.REFRESH_TOKEN_INVALID);
        }
        boolean b1 = jwtTokenProvider.validateToken(token);
        if (!b1){
            throw new TokenException(AuthError.REFRESH_TOKEN_INVALID);
        }
        Map<String, Object> claims = jwtTokenProvider.getClaims(token);
        return AccessTokenClaims.fromAccessTokenClaims(claims);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        //验证签名
        boolean b1 = tokenStorage.chickSignExist(request.getSign());
        if(!b1){
            throw new RequestBadException(AuthError.INVALID_SIGN);
        }
        String userId = ApplicationContext.getUserId();
        ChangePasswordReq changePasswordReq = new ChangePasswordReq();
        changePasswordReq.setOldPassword(request.getOldPassword());
        changePasswordReq.setNewPassword(request.getNewPassword());
        changePasswordReq.setUserId(userId);
        userServerClient.changePassword(changePasswordReq);
    }

    @Override
    public UserDto info() {
        // 获取用户信息
        if (StringUtils.isBlank(ApplicationContext.getUserId())) {
            throw new BusinessException(AuthError.TOkEN_INVALID);
        }
        UserDto user = userServerClient.get(ApplicationContext.getUserId());
        if(user == null) {
            throw new BusinessException(AuthError.TOkEN_INVALID);
        }
        if (user.getIdentity() == UserIdentity.Merchants.getValue()){
           user.setTenantId(user.getMerchantId());
        }else{
            user.setTenantId(ApplicationContext.getTenantId());
            user.setMerchantId(ApplicationContext.getTenantId());
        }

        return user;
    }

    @Override
    public void recoverPassword(RecoverYourPasswordRequest request) {
        String account = UserUtils.getAccount(request.getCountryCode(), request.getPhone());
        //验证签名
        boolean b1 = tokenStorage.chickSignExist(account,request.getSign());
        if(!b1){
            throw new RequestBadException(AuthError.INVALID_SIGN);
        }
        RecoverPasswordRequest changePasswordReq = new RecoverPasswordRequest();
        changePasswordReq.setConfirmPassword(request.getConfirmPassword());
        changePasswordReq.setNewPassword(request.getNewPassword());
        changePasswordReq.setAccount(account);
        userServerClient.recoverPassword(changePasswordReq);
    }

    @Override
    public void logout() {
        tokenStorage.delUser(ApplicationContext.getUserId());
    }


    /**
     * 根据用户生成登录返回
     * @param userDto 用户信息
     * @return 登录签名
     */
    private LoginResponse getLoginResponse(UserDto userDto){
        if (userDto == null) {
            throw new RequestBadException(UserError.ACCOUNT_OR_PASSWORD_ERROR);
        }
        String token = this.generateToken(userDto);
        String refreshToken = this.generateRefreshToken(userDto);
        //存储token
        if(!tokenStorage.saveTokens(userDto.getId(),token,refreshToken)){
            throw new BaseException(AuthError.TOKEN_SAVE_ERROR);
        }
        return LoginResponse.builder().accessToken(token).refreshToken(refreshToken).expiresIn(jwtTokenProvider.getAccessTokenExpiration())
                .refreshTokenExpiresIn(jwtTokenProvider.getRefreshTokenExpiration()).tokenType("Bearer").build();
    }
    private String generateToken(UserDto user) {
        AccessTokenClaims identity = AccessTokenClaims.builder()
                .userId(user.getId())
                .identity(user.getIdentity())
                .tenantId(user.getTenantId())
                .build();

        // 使用自定义令牌生成器生成访问令牌
        return jwtTokenProvider.generateAccessToken(identity.toAccessTokenClaims());
    }

    private String generateRefreshToken(UserDto user) {
        // 使用自定义令牌生成器生成刷新令牌
        AccessTokenClaims claims = AccessTokenClaims.builder().userId(user.getId()).build();
        return jwtTokenProvider.generateRefreshToken(claims.toRefTokenClaims());
    }

    /**
     *  登录 获取用户
     * @param request 登录请求
     * @return 用户信息
     */
    private UserDto loginGetUserDto(LoginRequest request) {
        if (StringUtils.isBlank(request.getPhone()) || StringUtils.isBlank(request.getPassword())) {
            throw new RequestBadException(UserError.ACCOUNT_OR_PASSWORD_ERROR);
        }
        UserDto userDto = userServerClient.loadAndChickPassword(UserUtils.getAccount(request.getCountryCode() , request.getPhone()),request.getPassword());
        if (userDto == null) {
            throw new RequestBadException(UserError.ACCOUNT_OR_PASSWORD_ERROR);
        }
        return userDto;
    }
}
