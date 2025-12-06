package org.ares.cloud.store;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/9 00:23
 */
public interface TokenStorage {
    /**
     * 存储token
     * @param token token
     * @param userId 用户id
     * @param refToken 刷新token
     * @return 是否存储成功
     */
    boolean saveTokens(String userId, String token, String refToken);

    /**
     * 校验token是否存在
     * @param token token
     * @return 是否成功
     */
    boolean chickTokenExist(String token);

    /**
     * 校验ref token是否存在
     * @param refToken token
     * @return 是否成功
     */
    boolean chickRefTokenExist(String refToken);
    /**
     * 删除token
     * @param token token
     * @return 是否成功
     */
    boolean delToken(String token);
    /**
     * 根据用户删除
     * @param userId 用户id
     * @return 是否成功
     */
    boolean delUser(String userId);

    /**
     * 保存签名
     * @param sign 签名
     * @return 是否成功
     */
    boolean saveSign(String sign);
    /**
     * 保存签名
     * @param key 签名key
     * @param sign 签名
     * @return 是否成功
     */
    boolean saveSign(String key,String sign);
    /**
     * 校验签名是否存砸
     * @param sign 签名
     * @return 是否存在
     */
    boolean chickSignExist(String sign);
    /**
     * 校验签名是否存砸
     * @param key 签名key
     * @param sign 签名
     * @return 是否存在
     */
    boolean chickSignExist(String key,String sign);

    /**
     * 删除签名
     * @param sign 签名
     * @return 删除结果
     */
    boolean delSign(String sign);
}
