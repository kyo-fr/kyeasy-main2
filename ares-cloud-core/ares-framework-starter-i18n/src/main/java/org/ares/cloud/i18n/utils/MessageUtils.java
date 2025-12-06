package org.ares.cloud.i18n.utils;

import org.ares.cloud.common.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;

/**
 * @Author hugo  tangxkwork@163.com
 * @description 国际化工具
 * @date 2024/01/17/15:36
 **/
public class MessageUtils {
    private static MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String msgKey) {
        return getArg(msgKey, null);
    }
    /**
     * 获取单个国际化翻译值
     */
    public static String getArg(String msgKey,Object[] arg) {
        return get(msgKey,arg, null);
    }
    /**
     * 获取国际化数据
     * @param msgKey 国际化key
     * @param def 默认值
     * @return 翻译后的数据
     */
    public static String get(String msgKey,String def) {
        try {
            Locale locale = ApplicationContext.getLocale();
            String message = messageSource.getMessage(msgKey, null, locale);
            if (message.equals(msgKey)) {
                return Objects.requireNonNullElse(def, "NOT_TRANSLATED");
            }
            return message;
        } catch (Exception e) {
            if (def != null && def.isEmpty()) {
                return def;
            }
            return "NOT_TRANSLATED";
        }
    }
    /**
     * 获取国际化数据
     * @param msgKey 国际化key
     * @param arg  参数占位
     * @param def 默认值
     * @return 翻译后的数据
     */
    public static String get(String msgKey,Object[] arg,String def) {
        try {
            Locale locale = ApplicationContext.getLocale();
            return messageSource.getMessage(msgKey, arg, locale);
        } catch (Exception e) {
            if (def != null && def.isEmpty()) {
                return def;
            }
            return "NOT_TRANSLATED";
        }
    }
}
