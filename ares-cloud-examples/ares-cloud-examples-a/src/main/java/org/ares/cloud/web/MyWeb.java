package org.ares.cloud.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "测试")
public class MyWeb {
    // 手动声明日志对象
   private static final Logger log = LoggerFactory.getLogger(MyWeb.class);
    @GetMapping("/endpoint")
    @Operation(summary = "获取数据")
    @Parameters({
            @Parameter(
                    name = "param",
                    description = "参数",
                    required = true,
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string")
            ),
    })
    public String getData(@RequestParam("param") String param){
        log.info("sssasd");
        System.out.println("上下文中的用户id"+ApplicationContext.getUserId());
        return "hello"+param;
    }
}
