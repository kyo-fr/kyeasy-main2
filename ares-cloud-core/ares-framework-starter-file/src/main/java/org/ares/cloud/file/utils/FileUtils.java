package org.ares.cloud.file.utils;

import cn.hutool.core.lang.UUID;

import java.time.LocalDate;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件工具类
 * @date 2024/10/11 21:12
 */
public class FileUtils {
    /**
     * 生成文件路径
     * @return 文件路径
     */
    public static String generateFilePath() {
        LocalDate today = LocalDate.now();
        return today.getYear() + "" + today.getMonthValue() + today.getDayOfMonth();
    }

    /**
     *  生成完成的地址
     * @param fileName 文件名
     * @return 上传地址
     */
    public static String generateCompletePath(String fileName) {
        String fileExtension = "";
        if (fileName != null && fileName.contains(".")) {
            fileExtension = fileName.substring(fileName.lastIndexOf("."));
        }
        // 生成新的唯一文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + fileExtension;

        // 将新文件名添加到指定路径中
        return generateFilePath() + "/" + newFileName;
    }
    /**
     *  生成完成的地址
     * @param fileName 文件名
     * @return 上传地址
     */
    public static String generateFileName(String fileName) {
        String fileExtension = "";
        if (fileName != null && fileName.contains(".")) {
            fileExtension = fileName.substring(fileName.lastIndexOf("."));
        }
        // 生成新的唯一文件名

        // 将新文件名添加到指定路径中
        return UUID.randomUUID() + fileExtension;
    }
    /**
     * 文件名
     * @param completePath 完整路径
     * @return 文件名
     */
    public static String extractFileName(String completePath) {
        if (completePath == null || completePath.isEmpty()) {
            throw new IllegalArgumentException("Invalid path");
        }
        // 找到最后一个斜杠的位置
        int lastSlashIndex = completePath.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            // 如果路径中不包含斜杠，直接返回整个路径作为文件名
            return completePath;
        }
        // 返回最后一个斜杠后面的部分，即文件名
        return completePath.substring(lastSlashIndex + 1);
    }
}
