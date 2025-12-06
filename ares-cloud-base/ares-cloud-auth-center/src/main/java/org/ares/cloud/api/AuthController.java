package org.ares.cloud.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.dto.ChangePasswordRequest;
import org.ares.cloud.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/v1")
@Tag(name = "认证服务")
public class AuthController {
    @Resource
    private AuthService authService;

    @GetMapping("logout")
    @Operation(
            summary = "退出登录",
            parameters = {
                    @Parameter(name = "Authorization", description = "用户的访问令牌", required = true, in = ParameterIn.HEADER)
            }
    )
    public Result<String> checkAccount() {
        authService.logout();
        return Result.success("");
    }

    @PostMapping("change_password")
    @Operation(
            summary = "修改用户密码",
            description = "更新当前登录用户的密码",
            parameters = {
                    @Parameter(name = "Authorization", description = "用户的访问令牌", required = true, in = ParameterIn.HEADER)
            }
    )
    public Result<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return Result.success("");
    }
    @GetMapping("info")
    @Operation(
            summary = "用户信息",
            description = "当前登录用户"
    )
    public Result<UserDto> info() {
        UserDto info = authService.info();
        return Result.success(info);
    }
}

