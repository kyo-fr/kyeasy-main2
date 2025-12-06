package org.ares.cloud.base.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.base.service.IAppleMapTokenService;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.s3.dto.ObjectStorageDto;
import org.ares.cloud.s3.query.ObjectStorageQuery;
import org.ares.cloud.s3.service.ObjectStorageService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author hugo tangxkwork@163.com
* @description 苹果地图 控制器
* @version 1.0.0
* @date 2024-10-12
*/
@RestController
@RequestMapping("/api/base/app/map")
@Tag(name="苹果地图")
@AllArgsConstructor
public class AppleMapController {
    @Resource
    private IAppleMapTokenService appleMapTokenService;



    @GetMapping("token")
    @Operation(summary = "生成token")
    public Result<String> generateToken(){
         String token = appleMapTokenService.generateToken();
         return Result.success(token);
    }





}