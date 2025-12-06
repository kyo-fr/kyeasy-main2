package org.ares.cloud.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author hugo
 * @version 1.0
 * @description: 启动注解
 * @date 2024/9/29 15:17
 * //"org.ares.cloud.feign.advice"
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication
@ComponentScan(basePackages = {"org.ares.cloud"}) // 默认扫描包
public @interface EnableAresServer {
    String[] additionalPackages() default {}; // 可选的其他包
}
