package org.ares.cloud.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.ares.cloud.common.utils.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author hugo  tangxkwork@163.com
 * @description 应用上下文
 * @date 2024/01/17/23:22
 **/
public class ApplicationContext {
    /**
     * 下午文实例
     */
    private static final TransmittableThreadLocal<AresContext> context = new TransmittableThreadLocal<>(){
        @Override
        protected AresContext initialValue() {
            return new AresContext();
        }
    };
    //把构造函数私有化，外部不能new
    private ApplicationContext() {
    }
    /**
     * 存放数据
     *
     * @param appContext 上下文数据
     */
    public static void set(AresContext appContext) {
        context.set(appContext);
    }

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    public static AresContext get() {
        return context.get();
    }

    /**
     * 获取用户的id
     *
     * @return 用户ID
     */
    public static String getUserId() {
        return (context.get()).userId;
    }

    public static void setUserId(String userId) {
        (context.get()).userId = userId;
    }
    /**
     * 租户id
     * @return 租户id
     */
    public static String getTenantId() {
        return (context.get()).tenantId;
    }

    public static void setTenantId(String tenantId) {
        (context.get()).tenantId = tenantId;
    }
    /**
     * 真实的客户端地址
     * @return 真实客户端地址
     */
    public static String getRealIpAddress() {
        return (context.get()).realIpAddress;
    }

    public static void setRealIpAddress(String tenantId) {
        (context.get()).realIpAddress = tenantId;
    }
    /**
     * 权限
     * @return 权限列表
     */
    public static String getPowers() {
        return (context.get()).powers;
    }

    public static void setPowers(String powers) {
        (context.get()).powers = powers;
    }

    /**
     * 客户端
     * @return 客户端id
     */
    public static String getClientId() {
        return (context.get()).clientId;
    }

    public static void setClientId(String clientId) {
        (context.get()).clientId = clientId;
    }
    /**
     * token
     * @return token
     */
    public static String getToken() {
        return (context.get()).token;
    }

    public static void setToken(String token) {
        (context.get()).token = token;
    }
    /**
     * 应用主键
     * @return 应用的id
     */
    public static String getAppId() {
        return (context.get()).appId;
    }

    public static void setAppId(String appId) {
        (context.get()).appId = appId;
    }

    /**
     * 语言
     * @return 线程逐渐
     */
    public static Locale getLocale() {
        return (context.get()).locale;
    }

    public static void setLocale(Locale locale) {
        (context.get()).locale = locale;
    }
    /**
     * 线程主键
     * @return 线程逐渐
     */
    public static String getThreadId() {
        return (context.get()).tenantId;
    }

    public static void setThreadId(String locale) {
        (context.get()).tenantId = locale;
    }
    /**
     * 链路id
     * @return 链路id
     */
    public static String getTraceId() {
        return (context.get()).traceId;
    }

    /**
     * 链路id
     * @param traceId 设置链路id
     */
    public static void setTraceId(String traceId) {
        (context.get()).traceId = traceId;
    }
    /**
     * 获取时区
     */
    public static String getZoneId() {
        return (context.get()).zoneId;
    }
    /**
     * 设置时区
     */
    public static void setZoneId(String zoneId) {
        (context.get()).zoneId = zoneId;
    }
    /**
     * 数据权限
     * @return 数据权限
     */
    public static List<Long> getDataScopeList() {
        return (context.get()).dataScopeList;
    }

    /**
     * 数据权限
     * @param dataScopeList 数据权限
     */
    public static void setDataScopeList(List<Long> dataScopeList) {
        (context.get()).dataScopeList = dataScopeList;
    }

    /**
     * 角色列表
     * @return 角色列表
     */
    public static List<String> getRoleScopeList() {
        return (context.get()).roleScopeList;
    }

    /**
     * 角色列表
     * @param roleScopeList 角色列表
     */
    public static void setRoleScopeList(List<String> roleScopeList) {
        (context.get()).roleScopeList = roleScopeList;
    }
    /**
     * 是否忽略租户
     * @return 是否忽略租户
     */
    public static Boolean getIgnoreTenant() {
        return (context.get()).ignoreTenant;
    }

    /**
     * 是否忽略租户
     * @param ignoreTenant 是否忽略租户
     */
    public static void setIgnoreTenant(Boolean ignoreTenant) {
        (context.get()).ignoreTenant = ignoreTenant;
    }

    /**
     * 获取对应的数据
     * @param key 扩展属性的key
     * @param clazz 要返回的类型
     * @return 返回对应的值
     * @param <T> 类型
     */
    public static <T> T getMateDataValue(String key,Class<T> clazz) {
       Object val = context.get().mateData.get(key);
        if (val == null) {
            return null;
        }
        if (clazz.isInstance(val)) {
            return clazz.cast(val);  // Safely cast the object
        } else {
            return null;
        }
    }

    /**
     * 设置扩展参数
     * @param key 参数的key
     * @param value 值
     */
    public static void setMateDataValue(String key, Object value) {
        (context.get()).mateData.put(key, value);
    }

    /**
     * 获取用户定义的数据
     * @return 定义数据
     */
    public static Map<String, Object> getMateDataMap() {
        return context.get().mateData;
    }
    /**
     * 总结数据
     * @return 返回mqp数据
     */
    public static Iterator<Map.Entry<String, String>> getSummary() {
        return (context.get()).getSummary();
    }

    /**
     * 获取用户身份
     * @return 用户身份
     */
    public static Integer getIdentity() {
        String identity = getStrIdentity();
        if (StringUtils.isBlank(identity)) {
            return null;
        }
        return Integer.valueOf(identity);
    }

    /**
     * 获取用户身份
     * @return 用户身份
     */
    public static String getStrIdentity() {
        Object o = context.get().mateData.get("identity");
        if (o == null) {
            return null;
        }
        return o.toString();
    }
    /**
     * 设置用户身份
     * @param identity 身份
     */
    public static void setIdentity(String identity) {
        context.get().mateData.put("identity", identity);
    }
    /**
     * 清除当前线程内引用，防止内存泄漏
     */
    public static void clear() {
        context.remove();
    }
}
