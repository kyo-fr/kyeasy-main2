package org.ares.cloud.feign.decode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import org.ares.cloud.common.exception.BaseException;
import org.ares.cloud.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author hugo
 * @version 1.0
 * @description: Feign 响应解码器
 * 处理 HTTP 200 的响应：
 * 1. 能解析为 Result 格式且 code != 200 → 抛出 BaseException（业务错误）
 * 2. 不能解析为 Result 格式 → 使用默认解码，如有异常则抛出 RpcCallException
 * 3. 正常的业务数据返回 → 正常解码
 * 
 * 工作原理：
 * - 先读取响应体内容
 * - 尝试解析为 Result 格式
 *   - 成功解析且 code != 200 → 抛出 BaseException（保留所有错误上下文）
 *   - 解析失败或不是 Result → 使用默认解码器，异常包装为 RpcCallException
 * 
 * @date 2024/10/23
 */
public class FeignResponseDecoder implements Decoder {
    
    private static final Logger log = LoggerFactory.getLogger(FeignResponseDecoder.class);
    
    private final Decoder delegate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public FeignResponseDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.delegate = new SpringDecoder(messageConverters);
    }
    
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        // 只处理 HTTP 200 的情况
        if (response.status() != 200) {
            // 非 200 状态码交给 delegate 默认处理
            return delegate.decode(response, type);
        }
        
        // 读取响应体
        Response.Body body = response.body();
        if (body == null) {
            return null;
        }
        
        String bodyStr = Util.toString(body.asReader(StandardCharsets.UTF_8));
        
        // 先尝试解析为 Result 格式
        try {
            Result<?> result = objectMapper.readValue(bodyStr, new TypeReference<Result<Object>>() {});
            
            // 成功解析为 Result 格式
            if (result != null && result.getCode() != null) {
                // 检查是否是错误响应
                if (result.getCode() != 200) {
                    // 抛出 BaseException
                    String methodKey = response.request().httpMethod() + " " + response.request().url();
                    
                    log.warn("RPC 调用 {} 返回业务错误: code={}, msg={}, reason={}, exception={}", 
                            methodKey, result.getCode(), result.getMsg(), result.getReason(), result.getException());
                    
                    BaseException baseException = buildBaseException(result);
                    
                    // 获取异常消息（优先使用 getMsg()，因为某些构造函数没有调用 super(msg)）
                    String exceptionMessage = baseException.getMsg();
                    if (exceptionMessage == null || exceptionMessage.isEmpty()) {
                        exceptionMessage = baseException.getMessage();
                    }
                    if (exceptionMessage == null || exceptionMessage.isEmpty()) {
                        exceptionMessage = "RPC 调用返回业务错误，code=" + result.getCode();
                    }
                    
                    // 抛出 FeignServerException，将 BaseException 作为 cause
                    throw new FeignException.FeignServerException(
                            result.getCode(),
                            exceptionMessage,
                            response.request(),
                            bodyStr.getBytes(StandardCharsets.UTF_8),
                            null
                    ) {
                        @Override
                        public synchronized Throwable getCause() {
                            return baseException;
                        }
                    };
                }
                // code == 200，继续正常解码
            }
        } catch (FeignException e) {
            // 重新抛出 FeignException
            throw e;
        } catch (Exception e) {
            // 不是 Result 格式，继续正常解码
            log.debug("响应不是 Result 格式: {}", e.getMessage());
        }
        
        // 不是 Result 或者 code == 200，使用默认解码器
        Response rebuiltResponse = response.toBuilder()
                .body(bodyStr, StandardCharsets.UTF_8)
                .build();
        
        return delegate.decode(rebuiltResponse, type);
    }
    
    /**
     * 根据 Result 构建 BaseException
     * @param result Result 对象
     * @return BaseException
     */
    private BaseException buildBaseException(Result<?> result) {
        // 安全获取错误码
        Integer code = result.getCode() != null ? result.getCode() : 500;
        
        // 安全获取错误消息
        String msg = result.getMsg();
        if (msg == null || msg.isEmpty()) {
            msg = "RPC 调用返回错误";
        }
        
        // 安全获取 reason（messageKey）
        String reason = result.getReason();
        
        BaseException baseException;
        
        if (reason != null && !reason.isEmpty()) {
            // 有 reason（messageKey），构建完整的异常
            baseException = new BaseException(code, msg, reason);
        } else {
            // 只有 code 和 msg
            baseException = new BaseException(code, msg);
        }
        
        // 如果 Result 中有 exception 信息，也保存到 BaseException 中
        String exception = result.getException();
        if (exception != null && !exception.isEmpty()) {
            baseException.setException(new RuntimeException(exception));
        }
        
        return baseException;
    }
}

