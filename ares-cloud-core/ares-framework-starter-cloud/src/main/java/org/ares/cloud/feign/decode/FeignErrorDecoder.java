package org.ares.cloud.feign.decode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.ares.cloud.common.exception.NotFoundException;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.exception.RpcCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;

/**
 * @author hugo
 * @version 1.0
 * @description: Feign 错误解码器，拦截 RPC 调用异常并处理
 * 将远程服务的异常信息完整地解析出来，包括业务异常和技术异常
 * @date 2024/10/16 14:46
 */
public class FeignErrorDecoder implements ErrorDecoder {
    private static final Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // 处理 404 错误
            if (response.status() == HttpStatus.NOT_FOUND.value()) {
                log.warn("RPC 调用 {} 返回 404，资源未找到", methodKey);
                return new NotFoundException();
            }

            // 读取响应体
            String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            
            // 尝试解析为标准的 Result 格式
            if (body != null && !body.isEmpty()) {
                try {
                    Result<String> result = objectMapper.readValue(body, new TypeReference<Result<String>>() {});
                    
                    if (result != null) {
                        // 从 Result 构建 RpcCallException，保留所有上下文信息
                        Integer code = result.getCode();
                        String msg = result.getMsg();
                        String reason = result.getReason();
                        String exception = result.getException();
                        
                        // 使用 RpcCallException 统一包装，保留完整的 Result 信息
                        RpcCallException rpcException = new RpcCallException(code, msg, reason, exception);
                        
                        if (reason != null && !reason.isEmpty()) {
                            log.warn("RPC 调用 {} 返回业务异常: code={}, msg={}, reason={}, exception={}", 
                                    methodKey, code, msg, reason, exception);
                        } else {
                            log.warn("RPC 调用 {} 返回异常: code={}, msg={}, exception={}", 
                                    methodKey, code, msg, exception);
                        }
                        
                        return rpcException;
                    }
                } catch (Exception parseException) {
                    // JSON 解析失败，可能不是标准格式
                    log.warn("RPC 调用 {} 返回的响应体无法解析为 Result 格式: {}", methodKey, body);
                }
            }
            
            // 如果无法解析为标准格式，根据 HTTP 状态码创建 RpcCallException
            String errorMessage = String.format("RPC 调用失败: %s", methodKey);
            String exceptionDetail = String.format("HTTP状态码: %d, 响应: %s", response.status(), body);
            
            RpcCallException rpcException = new RpcCallException(
                    response.status(), 
                    errorMessage,
                    "rpc_call_error",
                    exceptionDetail
            );
            
            log.error("RPC 调用 {} 失败: HTTP {}, Body: {}", methodKey, response.status(), body);
            return rpcException;
            
        } catch (Exception e) {
            // 解码过程中发生异常，包装为 RpcCallException
            log.error("解析 RPC 调用 {} 的错误响应时发生异常", methodKey, e);
            return new RpcCallException(e);
        }
    }
}

