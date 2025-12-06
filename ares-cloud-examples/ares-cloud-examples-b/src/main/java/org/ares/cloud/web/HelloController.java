package org.ares.cloud.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.ares.cloud.api.MyServiceClient;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.enums.ExamplesError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "打招呼")
public class HelloController {
    @Resource
    private MyServiceClient myServiceClient;  // 自动注入 Feign 客户端

    @Operation(summary = "Say Hello", description = "Returns a greeting message")
    @GetMapping("/hello")
    public String getData(@Parameter(description = "Name of the user") @RequestParam("param") String param){
        System.out.println("上下文中的用户id"+ ApplicationContext.getUserId());
        if ("nihao".equals(param)) {
            throw new BusinessException(ExamplesError.SAY_HELLO);
        }
        return this.myServiceClient.seyHello(param);
    }
}
