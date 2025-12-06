package org.ares.cloud.file.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * 文件错误码
 */
public enum FileError implements BaseErrorInfoInterface {
    
    // 文件基础校验错误
    FILE_INFO_EMPTY("file_info_empty"),
    FILE_NAME_EMPTY("file_name_empty"),
    FILE_SIZE_INVALID("file_size_invalid"),
    FILE_TYPE_NOT_SUPPORTED("file_type_not_supported"),
    
    // 文件大小限制错误
    IMAGE_SIZE_EXCEEDED("image_size_exceeded"),
    VIDEO_SIZE_EXCEEDED("video_size_exceeded"),
    OTHER_FILE_SIZE_EXCEEDED("other_file_size_exceeded"),
    
    // 存储配额错误
    STORAGE_QUOTA_INSUFFICIENT("storage_quota_insufficient"),
    STORAGE_QUOTA_CHECK_FAILED("storage_quota_check_failed"),
    STORAGE_QUOTA_RESPONSE_ERROR("storage_quota_response_error"),
    STORAGE_QUOTA_VALUE_INVALID("storage_quota_value_invalid"),
    
    // 租户相关错误
    TENANT_ID_REQUIRED("tenant_id_required"),
    TENANT_INFO_INCOMPLETE("tenant_info_incomplete"),
    
    // 文件操作错误
    FILE_UPLOAD_FAILED("file_upload_failed"),
    FILE_SAVE_FAILED("file_save_failed"),
    FILE_DELETE_FAILED("file_delete_failed"),
    FILE_NOT_FOUND("file_not_found");

    private final Integer code;
    private final String messageKey;

    FileError(String messageKey) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = messageKey;
    }

    FileError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
