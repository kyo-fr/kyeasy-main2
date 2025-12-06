package com.ares.cloud.gateway.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author hugo
 * @version 1.0.0
 * @description TODO
 * @date 2022/9/29 11:36
 */
public class AesUtil {

    // AES key要求是16位的
    private static final String KEY = "aresCloud1234567";

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content) {
        try {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            byte[] byteContent = content.getBytes("utf-8");

            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(KEY));

            // 加密
            byte[] result = cipher.doFinal(byteContent);

            //转为16进制
            return Hex.encodeHexString(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(KEY));

            //执行操作
            byte[] result = cipher.doFinal(Hex.decodeHex(content.toCharArray()));

            return new String(result, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        return new SecretKeySpec(password.getBytes(), KEY_ALGORITHM);
    }
}
