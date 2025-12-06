package org.ares.cloud.common.utils;
import org.springframework.util.StringUtils;

import java.util.Locale;
/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/1 02:31
 */
public class LocalUtils {

    public static Locale getLocale(String locale) {
        Locale defLocale = Locale.SIMPLIFIED_CHINESE;
        // 如果请求中带有语言参数
        if (StringUtils.hasText(locale)) {
            try {
                String[] langRegion = locale.split("_");
                if (langRegion.length == 2) {
                   return new Locale(langRegion[0], langRegion[1]);
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
        return defLocale;
    }
}
