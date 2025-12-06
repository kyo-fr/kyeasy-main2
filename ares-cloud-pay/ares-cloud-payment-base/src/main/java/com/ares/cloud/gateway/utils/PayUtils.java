package com.ares.cloud.gateway.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: TODO 
 * @author hugo
 * @date 2025/3/8 11:56
 * @version 1.0
 */
public class PayUtils {
    /**
     * 是否是数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        String regex = "^([0-9]+(.[0-9]{1,2})?)|(-[0-9]+(.[0-9]{1,2})?)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.matches();
    }
}
