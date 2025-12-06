package org.ares.cloud.tuils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/1 01:39
 */
public class MessageUtils {
    /**
     * 获取国际化数据
     * @param msgKey 国际化key
     * @param def 默认值
     * @return 翻译后的数据
     */
    public static String get(MessageSource messageSource,String msgKey, String def,Locale locale) {
        try {
            return messageSource.getMessage(msgKey, null, locale);
        } catch (Exception e) {
            if (def != null && def.isEmpty()) {
                return def;
            }
            return "NOT_TRANSLATED";
        }
    }
}
