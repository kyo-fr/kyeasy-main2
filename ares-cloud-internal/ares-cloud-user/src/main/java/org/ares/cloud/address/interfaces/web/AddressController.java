package org.ares.cloud.address.interfaces.web;

import org.ares.cloud.address.application.command.AddressCommand;
import org.ares.cloud.address.application.dto.AddressDTO;
import org.ares.cloud.address.application.query.AddressQuery;
import org.ares.cloud.address.application.service.AddressApplicationService;
import org.ares.cloud.address.application.service.AddressQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.model.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址管理接口
 */
@Tag(name = "地址管理")
@RestController
@RequestMapping("/api/user/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressApplicationService addressApplicationService;
    private final AddressQueryService addressQueryService;

    @Operation(summary = "添加地址")
    @PostMapping
    public Result<String> addAddress(@RequestBody @Valid AddressCommand command) {
        // 从当前用户上下文中获取用户ID
        String userId = ApplicationContext.getUserId();
        addressApplicationService.addAddress(userId, command);
        return Result.success();
    }

    @Parameter(
        name = "id",
        description = "地址ID",
        required = true,
        in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
        schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string")
    )
    @Operation(summary = "更新地址")
    @PutMapping("/{id}")
    public Result<AddressDTO> updateAddress(
            @PathVariable String id,
            @RequestBody @Valid AddressCommand command) {
        AddressDTO addressDTO = addressApplicationService.updateAddress(id, command);
        return Result.success(addressDTO);
    }

    @Parameter(
        name = "id",
        description = "地址ID",
        required = true,
        in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
        schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string")
    )
    @Operation(summary = "删除地址")
    @DeleteMapping("/{id}")
    public Result<String> deleteAddress(
            @PathVariable String id) {
        addressApplicationService.deleteAddress(id);
        return Result.success();
    }



    @Operation(summary = "获取地址列表")
    @GetMapping
    public Result<List<AddressDTO>> getAddressList() {
        // 从当前用户上下文中获取用户ID
        String userId = ApplicationContext.getUserId();
        List<AddressDTO> addresses = addressQueryService.getAddressList(userId);
        return Result.success(addresses);
    }
    
    @Operation(summary = "根据条件查询地址")
    @GetMapping("/query")
    public Result<List<AddressDTO>> queryAddresses(AddressQuery query) {
        // 从当前用户上下文中获取用户ID
        String userId = ApplicationContext.getUserId();
        List<AddressDTO> addresses = addressQueryService.findAddresses(userId, query);
        return Result.success(addresses);
    }

    @Operation(summary = "获取默认地址")
    @GetMapping("/default")
    public Result<AddressDTO> getDefaultAddress() {
        // 从当前用户上下文中获取用户ID
        String userId = ApplicationContext.getUserId();
        AddressDTO address = addressQueryService.getDefaultAddress(userId)
                .orElse(null); // 处理可能没有默认地址的情况
        return Result.success(address);
    }

    @Parameter(
        name = "id",
        description = "地址ID",
        required = true,
        in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
        schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string")
    )
    @Operation(summary = "获取地址详情")
    @GetMapping("/{id}")
    public Result<AddressDTO> getAddress(
            @PathVariable String id) {
        AddressDTO address = addressQueryService.getAddress(id);
        return Result.success(address);
    }
}