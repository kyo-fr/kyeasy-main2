package org.ares.cloud.filter;


import jakarta.annotation.Resource;
import org.ares.cloud.common.constant.Constants;
import org.ares.cloud.common.constant.MateData;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.LocalUtils;
import org.ares.cloud.tuils.MessageUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

/**
 * @author hugo
 * @version 1.0
 * @description: 翻译过滤器
 * @date 2024/9/30 16:47
 */
@Component
public class ResponseTranslationFilter implements GlobalFilter, Ordered {
    @Resource
    private MessageSource messageSource;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        // 获取请求头中的语言信息
        String language = exchange.getRequest().getHeaders().getFirst(MateData.Accept_Language);
        Locale locale = LocalUtils.getLocale(language);
        // 包装 ServerHttpResponse
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (Objects.equals(getStatusCode(), HttpStatus.OK)) {
                    // 判断 Content-Type，决定是否处理为文本内容
                    MediaType contentType = getHeaders().getContentType();
                    // 如果是文本类型内容，例如 JSON 或 HTML
                    if (contentType != null && (MediaType.APPLICATION_JSON.isCompatibleWith(contentType) ||
                            MediaType.TEXT_PLAIN.isCompatibleWith(contentType))) {
                        // 拦截响应体内容
                        Flux<DataBuffer> fluxBody = Flux.from(body);

                        // 缓存响应体的内容
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            DataBuffer combinedBuffer = bufferFactory.join(dataBuffers);
                            byte[] content = new byte[combinedBuffer.readableByteCount()];
                            combinedBuffer.read(content);
                            DataBufferUtils.release(combinedBuffer); // 释放 DataBuffer

                            // 将内容转换为 String
                            String responseBody = new String(content, StandardCharsets.UTF_8);
                            // 检查并翻译响应体内容
                            if (responseBody.contains("NOT_TRANSLATED")) {
                                try {
                                    Result result = Result.buildByJson(responseBody);
                                    String defMessage = MessageUtils.get(messageSource,"default.message","",locale);
                                    result.setMsg(MessageUtils.get(messageSource,result.getReason(), defMessage,locale));
                                    responseBody = result.toString();
                                } catch (Exception e) {
                                    e.fillInStackTrace();
                                }
                            }
                            // 将翻译后的内容重新写回响应体
                            byte[] newContent = responseBody.getBytes(StandardCharsets.UTF_8);
                            return bufferFactory.wrap(newContent);
                        }));
                    }
                }
                // 如果响应状态码不是 200，直接写回响应体
                return super.writeWith(body);
            }
        };
        // 将解析出来的 Locale 存入 exchange 的属性中，供后续使用
        exchange.getAttributes().put("locale", locale);
        // 使用装饰后的响应继续处理请求
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -2; // 使过滤器尽早执行
    }
}
