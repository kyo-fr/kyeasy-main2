package org.ares.cloud.advice;

import jakarta.annotation.Resource;
import org.ares.cloud.common.constant.MateData;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.LocalUtils;
import org.ares.cloud.tuils.MessageUtils;
import java.util.logging.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/1 01:26
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @Resource
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleException(ServerWebExchange exchange, Exception ex) {
        // 获取请求头中的语言信息
        String language = exchange.getRequest().getHeaders().getFirst(MateData.Accept_Language);
        Locale locale = LocalUtils.getLocale(language);

        // 日志记录异常信息
        logger.severe("异常捕获：" + ex.getMessage());

        // 获取国际化错误信息
        String errorMessage = MessageUtils.get(messageSource, "系统错误", "系统错误", locale);

        Result<String> result = Result.error(errorMessage);
        return Mono.just(new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
