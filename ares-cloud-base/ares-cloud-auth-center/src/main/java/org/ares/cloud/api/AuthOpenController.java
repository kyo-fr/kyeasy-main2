package org.ares.cloud.api;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ares.cloud.api.auth.dto.AccessTokenClaims;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.UserAgentUtils;
import org.ares.cloud.dto.*;
import org.ares.cloud.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/open/v1")
@Tag(name = "认证服务")
public class AuthOpenController {
    @Resource
    private AuthService authService;

    @Parameters({
            @Parameter(
                    name = "countryCode",
                    description = "国家代码",
                    required = true,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string")
            ),
            @Parameter(
                    name = "phone",
                    description = "手机号",
                    required = true,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string")
            )
    })
    @GetMapping("check_account")
    @Operation(
            summary = "检查账号是否存在"
    )
    public Result<Boolean> checkAccount(@RequestParam("countryCode") String countryCode, @RequestParam("phone") String phone) {
        Boolean b = authService.checkAccount(countryCode, phone);
        return Result.success(b);
    }

    @PostMapping("verify_firebase")
    @Operation(
            summary = "firebase验证",
            description = "验证手firebase的token，匹配成功后返回一个签名在后续操作使用"
    )
    public Result<String> verifyFirebase(@Valid @RequestBody FirebaseCodeVerifyRequest request) {
        String b = authService.verifyFirebaseCode(request);
        return Result.success(b);
    }

    @PostMapping("verify_code")
    @Operation(
            summary = "验证码校验",
            description = "验证手机号与输入的验证码是否匹配，匹配成功后返回一个签名在后续操作使用"
    )
    public Result<String> verifyCode(@Valid @RequestBody VerificationCodeVerifyRequest request) {
        String b = authService.verifyCode(request);
        return Result.success(b);
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return Result.success();
    }
    // 登录问题
    // 登录先判断账号身份
    // 平台端直接登录不做处理
    // 商户账号登录用账号去获取商户信息，然后登录
    // 普通用户和骑手的话，用域名获取商户信息，然后登录
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest authRequest, HttpServletRequest request) {
        // 获取客户端请求的域名或IP+端口
        String domainOrIpPort = UserAgentUtils.getDomain(request);
        // 获取用户的设备类型
        String deviceType = UserAgentUtils.getDeviceType(request);
        authRequest.setDomain(domainOrIpPort);
        authRequest.setDeviceType(deviceType);
        LoginResponse login = authService.login(authRequest);
        return Result.success(login);
    }

    @Operation(summary = "平台登录")
    @PostMapping("/platform_login")
    public Result<LoginResponse> platformLogin(@Valid @RequestBody LoginRequest authRequest, HttpServletRequest request) {
        // 获取用户的设备类型
        String deviceType = UserAgentUtils.getDeviceType(request);
        authRequest.setDeviceType(deviceType);
        LoginResponse login = authService.platformLogin(authRequest);
        return Result.success(login);
    }

    @Operation(summary = "骑手登录")
    @PostMapping("/rider_login")
    public Result<LoginResponse> riderLogin(@Valid @RequestBody LoginRequest authRequest, HttpServletRequest request) {
        // 获取用户的设备类型
        String deviceType = UserAgentUtils.getDeviceType(request);
        authRequest.setDeviceType(deviceType);
        LoginResponse login = authService.riderLogin(authRequest);
        return Result.success(login);
    }


    @Parameter(
            name = "refToken",
            description = "登录返回的ref_token",
            required = true,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    @Operation(summary = "刷新token")
    @PostMapping("/refresh")
    public Result<LoginResponse> refreshToken(@RequestBody RefTokenRequest req) {
        LoginResponse loginResponse = authService.refreshToken(req.getRefToken());
        return Result.success(loginResponse);
    }

    @PostMapping("recover_password")
    @Operation(
            summary = "找回密码",
            description = "通过验证码找回"
//            parameters = {
//                    @Parameter(name = "Authorization", description = "用户的访问令牌", required = true, in = ParameterIn.HEADER)
//            }
    )
    public Result<String> recoverPassword(@Valid @RequestBody RecoverYourPasswordRequest request) {
        authService.recoverPassword(request);
        return Result.success("");
    }

    @Hidden
    @PostMapping("/validate")
    public AccessTokenClaims validateToken(@RequestBody String token) {
        return authService.validateToken(token);
    }
}

