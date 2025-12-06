package org.ares.cloud.file.utils;

import org.ares.cloud.file.enums.FileError;
import org.ares.cloud.file.enums.FileType;
import org.ares.cloud.file.domain.BasicFile;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ares-cloud
 * @description 文件校验工具类
 * @date 2025-01-24
 */
public class FileValidationUtils {

    /**
     * 图片文件最大大小（MB）
     */
    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB

    /**
     * 视频文件最大大小（MB）
     */
    private static final long MAX_VIDEO_SIZE = 100 * 1024 * 1024; // 100MB

    /**
     * 其他文件最大大小（MB）
     */
    private static final long MAX_OTHER_SIZE = 50 * 1024 * 1024; // 50MB

    /**
     * 校验文件是否合法
     * @param file 文件信息
     * @return 校验结果
     */
    public static ValidationResult validateFile(BasicFile file) {
        if (file == null) {
            return ValidationResult.error(FileError.FILE_INFO_EMPTY);
        }

        // 校验文件名
        if (StringUtils.isBlank(file.getOriginalFileName())) {
            return ValidationResult.error(FileError.FILE_NAME_EMPTY);
        }

        // 校验文件大小
        if (file.getFileSize() <= 0) {
            return ValidationResult.error(FileError.FILE_SIZE_INVALID);
        }

        // 根据文件类型校验大小
        FileType fileType = FileType.fromMimeType(file.getFileType());
        if (fileType == FileType.IMAGE && file.getFileSize() > MAX_IMAGE_SIZE) {
            return ValidationResult.error(FileError.IMAGE_SIZE_EXCEEDED, MAX_IMAGE_SIZE / 1024 / 1024);
        } else if (fileType == FileType.VIDEO && file.getFileSize() > MAX_VIDEO_SIZE) {
            return ValidationResult.error(FileError.VIDEO_SIZE_EXCEEDED, MAX_VIDEO_SIZE / 1024 / 1024);
        } else if (fileType == FileType.OTHER && file.getFileSize() > MAX_OTHER_SIZE) {
            return ValidationResult.error(FileError.OTHER_FILE_SIZE_EXCEEDED, MAX_OTHER_SIZE / 1024 / 1024);
        }

        return ValidationResult.success();
    }

    /**
     * 校验存储配额是否足够
     * @param fileSize 文件大小（字节）
     * @param availableQuota 可用配额（KB）
     * @return 校验结果
     */
    public static ValidationResult validateStorageQuota(long fileSize, long availableQuota) {
        // 将文件大小转换为KB（向上取整）
        long fileSizeInKB = (fileSize + 1023) / 1024;
        
        if (fileSizeInKB > availableQuota) {
            return ValidationResult.error(FileError.STORAGE_QUOTA_INSUFFICIENT, fileSizeInKB, availableQuota);
        }
        
        return ValidationResult.success();
    }

    /**
     * 校验结果类
     */
    public static class ValidationResult {
        private final boolean success;
        private final String message;
        private final FileError error;
        private final Object[] args;

        private ValidationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
            this.error = null;
            this.args = null;
        }

        private ValidationResult(FileError error, Object... args) {
            this.success = false;
            this.message = null;
            this.error = error;
            this.args = args;
        }

        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult error(String message) {
            return new ValidationResult(false, message);
        }

        public static ValidationResult error(FileError error, Object... args) {
            return new ValidationResult(error, args);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public FileError getError() {
            return error;
        }

        public Object[] getArgs() {
            return args;
        }
    }
}
