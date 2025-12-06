package org.ares.cloud.common.utils;

import cn.hutool.core.io.IoUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author hugo
 * @version 1.0
 * @description: 异常工具类
 * @date 2024/9/29 15:42
 */
public class ExceptionUtils {
    /**
     * 获取异常信息
     * @param e  异常
     * @return    返回异常信息
     */
    public static String getExceptionMessage(Exception e) {
        if (e == null) return "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);

        // 关闭IO流
        IoUtil.close(pw);
        IoUtil.close(sw);

        return sw.toString();
    }
}
