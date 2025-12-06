package org.ares.cloud.common.utils;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/8/26 16:39
 */
public class UserUtils {
    /**
     * 获取账号
     * @param countryCode 国家代码
     * @param phone 手机号
     * @return 账号
     */
    public static String getAccount(String countryCode ,String phone) {
        if(StringUtils.isBlank(countryCode) || StringUtils.isBlank(phone)){
            return null;
        }
        if (StringUtils.startsWith(countryCode,"+")){
            return countryCode + phone;
        }
        return "+"+countryCode + phone;
    }
}
