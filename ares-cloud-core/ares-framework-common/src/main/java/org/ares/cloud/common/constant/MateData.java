package org.ares.cloud.common.constant;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/3 22:28
 */
public class MateData {
    /**
     * 前缀
     */
    public static final String MATE_PREFIX = "X-Mate-";
    /**
     * 用户id的key
     */
    public static final String USER_ID = MATE_PREFIX+"USER_ID";
    /**
     * powers
     */
    public static final String POWERS = MATE_PREFIX+"POWERS";
    /**
     * 应用主键
     */
    public static final String APP_ID = MATE_PREFIX+"APP_ID";
    /**
     * 客户端id
     */
    public static final String CLIENT_ID = MATE_PREFIX+"CLIENT_ID";
    /**
     * 签名
     */
    public static final String TOKEN = MATE_PREFIX+"TOKEN";
    /**
     * 租户的id
     */
    public static final String TENANT_Id = MATE_PREFIX+"TENANT_Id";

    /**
     * 身份
     */
    public static final String IDENTITY = MATE_PREFIX+"IDENTITY";

    /**
     * 真实的客户端地址
     */
    public static final String REAL_IP_ADDRESS = MATE_PREFIX+"REAL_IP_ADDRESS";
    /**
     * 链路的id
     */
    public static final String TRACE_Id = MATE_PREFIX+"TRACE_Id";
    /**
     * 是否忽略租户
     */
    public static final String IGNORE_TENANT_ID = MATE_PREFIX+"IGNORE_TENANT_ID";
    /**
     * 角色
     */
    public static final String ROLE = MATE_PREFIX+"ROLE";
    /**
     * 头部多语音
     */
    public static final String Accept_Language = "Accept-Language";
}

