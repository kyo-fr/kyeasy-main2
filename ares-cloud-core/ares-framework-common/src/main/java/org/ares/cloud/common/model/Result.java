package org.ares.cloud.common.model;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import org.ares.cloud.common.enums.ResponseCodeEnum;
import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.ares.cloud.common.exception.DefMessageErrorInterface;
import org.ares.cloud.common.utils.ExceptionUtils;
import org.ares.cloud.common.utils.JsonUtils;

import java.io.Serializable;

@Schema(description = "响应")
public class Result<T> implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    @Schema(description = "编码 200表示成功，其他值表示失败")
    private Integer code = 200;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String msg = "success";
    /**
     * 错误原因
     */
    @Schema(description = "错误原因")
    private String reason;

    /**
     * 时间戳
     */
    @Schema(description = "请求时间戳")
    private long time;
    /**
     * 异常信息
     */
    private String exception;

    /**
     * 业务数据
     */
    @Schema(description = "响应数据")
    private T data;
    /**
     * 请求id
     */
    private String requestId;

    public Result() {
        this.time = System.currentTimeMillis();
    }

    /**
     * 根据异常构建返回的构造方法
     * @param errorInfo 错误类型
     */
    public  Result(BaseErrorInfoInterface errorInfo) {
        this.code = errorInfo.getCode();
        this.reason = errorInfo.getMessageKey();
    }
    /**
     * 根据异常构建返回的构造方法
     * @param errorInfo 错误类型
     */
    public  Result(BaseErrorInfoInterface errorInfo,Exception e) {
        this.code = errorInfo.getCode();
        this.reason = errorInfo.getMessageKey();
        this.time = System.currentTimeMillis();
        this.exception = ExceptionUtils.getExceptionMessage(e);
    }
    /**
     * 根据异常构建返回的构造方法
     * @param errorInfo 错误类型
     */
    public  Result(DefMessageErrorInterface errorInfo) {
        this.code = errorInfo.getCode();
        this.msg = errorInfo.getMsg();
        this.reason = errorInfo.getMessageKey();
    }
    /**
     * 根据异常构建返回的构造方法
     * @param errorInfo 错误类型
     */
    public  Result(DefMessageErrorInterface errorInfo,Exception e) {
        this.code = errorInfo.getCode();
        this.msg = errorInfo.getMsg();
        this.reason = errorInfo.getMessageKey();
        this.time = System.currentTimeMillis();
        this.exception = ExceptionUtils.getExceptionMessage(e);
    }
    /**
     * 通过参数的构造方法
     * @param object 数据
     */
    public Result(Integer code,String msg,T object) {
        this.code = code;
        this.msg = msg;
        this.data =  object;
        this.time = System.currentTimeMillis();
    }
    /**
     * 通过参数的构造方法
     */
    public Result(Integer code,String msg,Long time,T object) {
        this.code = code;
        this.msg = msg;
        this.data =  object;
        this.time =  time;
        this.time = System.currentTimeMillis();
    }
    /**
     * 成功
     * @return 成功不返回数据
     */
    public static  Result<String> success() {
        Result<String> rb = new Result<>();
        rb.setCode(ResponseCodeEnum.RECODE_SUCCESS.getCode());
        rb.setMsg(ResponseCodeEnum.RECODE_SUCCESS.getMsg());
        rb.setReason(ResponseCodeEnum.RECODE_SUCCESS.getMessageKey());
        return rb;
    }
    /**
     * 成功
     * @param data 数据
     * @return 成功的返回
     */
    public static <T>  Result<T> success(T data) {
        Result<T> rb = new Result<>();
        rb.setCode(ResponseCodeEnum.RECODE_SUCCESS.getCode());
        rb.setMsg(ResponseCodeEnum.RECODE_SUCCESS.getMsg());
        rb.setReason(ResponseCodeEnum.RECODE_SUCCESS.getMessageKey());
        rb.setData(data);
        return rb;
    }

    /**
     * 成功
     * @return 成功
     */
    public static  Result<Object> success(Object data,String msg) {
        Result<Object> rb = new Result<>();
        rb.setCode(ResponseCodeEnum.RECODE_SUCCESS.getCode());
        rb.setMsg(msg);
        rb.setReason(ResponseCodeEnum.RECODE_SUCCESS.getMessageKey());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static  Result<String> error(DefMessageErrorInterface errorInfo) {
        Result<String> rb = new Result<>();
        rb.setCode(errorInfo.getCode());
        rb.setMsg(errorInfo.getMsg());
        rb.setReason(errorInfo.getMessageKey());
        rb.setData(null);
        return rb;
    }
    /**
     * 失败
     */
    public static  Result<String> error(BaseErrorInfoInterface errorInfo) {
        Result<String> rb = new Result<>();
        rb.setCode(errorInfo.getCode());
        rb.setReason(errorInfo.getMessageKey());
        rb.setData(null);
        return rb;
    }
    /**
     * 失败
     */
    public static  Result<String> error(BaseErrorInfoInterface errorInfo, Exception exception) {
        Result<String> rb = error(errorInfo);
        rb.setException(ExceptionUtils.getExceptionMessage(exception));
        return rb;
    }
    /**
     * 失败
     */
    public static  Result<String> error(Integer code, String message) {
        Result<String> rb = new Result<>();
        rb.setCode(code);
        rb.setMsg(message);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static Result<String> error( String message) {
        Result<String> rb = new Result<>();
        rb.setCode(ResponseCodeEnum.RECODE_ERR.getCode());
        rb.setMsg(message);
        rb.setReason(ResponseCodeEnum.RECODE_ERR.getMessageKey());
        rb.setData(null);
        return rb;
    }
    /**
     * 失败
     */
    public static  Result<String> error(Integer code, String message,Exception exception) {
        Result<String> rb = error(code, message);
        rb.setException(ExceptionUtils.getExceptionMessage(exception));
        return rb;
    }

    /**
     * 失败
     */
    public static Result<String> error( String message,Exception exception) {
        Result<String> rb = error(message);
        rb.setException(ExceptionUtils.getExceptionMessage(exception));
        return rb;
    }
    /**
     * 构建返回
     * @param commonErrorEnum 枚举消息
     * @param object 根据枚举
     */
    public  Result<T>  ResultEnum(ResponseCodeEnum commonErrorEnum, T object) {
        return new Result<>(commonErrorEnum.getCode(), commonErrorEnum.getMsg(), object);
    }

    /**
     * 构建根据枚举构建错误返回
     * @param commonErrorEnum 枚举消息
     */
    public static Result<String>  ResultEnumErr(ResponseCodeEnum commonErrorEnum) {
        Result<String> result =  new Result<>();
        return result.ResultEnum(commonErrorEnum,null);
    }
    /**
     * 构建根据枚举构建错误返回
     * @param commonErrorEnum 枚举消息
     * @param message  消息
     */
    public static Result<String>  ResultEnumErr(ResponseCodeEnum commonErrorEnum, String message) {
        if(StrUtil.isBlank(message)){
            message =  commonErrorEnum.getMsg();
        }
        return error(commonErrorEnum.getCode(),message);
    }

    /**
     * 根据 json构建
     * @param json 字符串
     * @return 实体
     */
    public static Result buildByJson(String json) {
        return JsonUtils.parseObject(json, Result.class);
    }

    /**
     * 重新写toString方法
     * @return 数据
     */
    @Override
    public String toString() {
        this.time = System.currentTimeMillis();
        return JsonUtils.toJsonString(this);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
