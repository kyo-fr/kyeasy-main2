package org.ares.cloud.redis.cache;

/**
 * @Author hugo  tangxkwork@163.com
 * @description 缓存的key
 * @date 2024/01/17/17:41
 **/
public class RedisKeys {
    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String key) {
        return "sys:captcha:" + key;
    }

    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String accessToken) {
        return "sys:token:" + accessToken;
    }

    public static String getLogKey() {
        return "sys:log";
    }
}
