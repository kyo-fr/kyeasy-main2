package org.ares.cloud.common.enums;


import org.ares.cloud.common.exception.BaseErrorInfoInterface;

/**
 * @author hugo  tangxkwork@163.com
 * @description 公共异常枚举
 * @date 2024/01/17/14:48
 **/
public enum ResponseCodeEnum implements BaseErrorInfoInterface {
    RECODE_SUCCESS(200, "成功!","message_success"),
    RECODE_ERR(500,"系统异常","message_error"),
    RECODE_BUS_ERR(501,"业务异常","message_bus_err"),
    RECODE_UNKNOWN_ERR(502, "未知错误","message_unknown_err"),
    RECODE_SERVER_BUSY (503, "服务器正忙，请稍后再试!","message_serve_busy"),
    RECODE_TOKEN_BE_OVERDUE(401,"token过期","message_token_be_overdue"),
    REQUEST_PARAMETER_ERROR(400,"请求参数错误","request_parameter_error"),
    RECODE_NOT_FOUND (404, "资源不存在!","recode_not_found"),
    RECODE_NOT_POWER(403,"未授权无法进行访问","message_not_power"),
    RECODE_TOKEN_FORMAT_ERR(415,"token认证失败不是正确的格式","message_token_format_err"),
    RECODE_FILE_DATA(461,"文件处理","file"),
    RECODE_GET_USER_INFO_ERR(462,"获取用户信息失败","message_get_user_info_err"),
    RECODE_TOKEN_NOT_AVAILABLE(401,"无权访问","message_token_no_available");

    /** 错误码 */
    private Integer resultCode;

    /** 错误描述 */
    private String resultMsg;
    /** 国际化的key */
    private String messKey;

    ResponseCodeEnum(Integer resultCode, String resultMsg, String messKey) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.messKey = messKey;
    }
    @Override
    public Integer getCode() {
        return resultCode;
    }


    public String getMsg() {
        return resultMsg;
    }

    public Exception getException() {
        return null;
    }

    @Override
    public String getMessageKey() {
        return this.messKey;
    }

    /**
     * 进行比较
     * @param code
     * @return
     */
    public Boolean selfEqual(Integer code) {
        int res = this.resultCode.compareTo(code);
        return res == 0;
    }
    // 根据 code 获取对应的枚举
    public static ResponseCodeEnum getByCode(int code) {
        for (ResponseCodeEnum value : ResponseCodeEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return RECODE_ERR;
    }
}
