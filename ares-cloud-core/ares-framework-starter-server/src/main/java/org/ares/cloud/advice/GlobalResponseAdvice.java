package org.ares.cloud.advice;

import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.i18n.utils.MessageUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/30 18:43
 */
@Component
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 仅在返回类型为 Result 时拦截
        return returnType.getParameterType().isAssignableFrom(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            Result<?> result = (Result<?>) body;
            // 对 reason 字段进行国际化翻译处理
            if (StringUtils.isNotBlank(result.getReason())) {
               result.setMsg(MessageUtils.get(result.getReason(),result.getMsg()));
            }
            // 设置时间戳
            result.setTime(System.currentTimeMillis());
            // 可以在这里设置 requestId 等其他字段
            result.setRequestId(ApplicationContext.getTraceId());
        }
        return body;
    }
}
