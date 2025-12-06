package org.ares.cloud.common.context;

import java.util.*;

/**
 * @author hugo  tangxkwork@163.com
 * @description 系统上下文
 * @date 2024/01/17/23:19
 **/
public class AresContext {
    //userId
    String userId;
    //powers
    String powers;
    //应用主键
    String appId;
    //客户端id
    String clientId;
    //签名
    String token;
    //租户的id
    String tenantId;
    //真实的客户端地址
    String realIpAddress;
    //链路的id
    String traceId;
    //是否忽略租户
    boolean ignoreTenant;
    //时区
    String zoneId;
    //扩展属性
    Map<String, Object> mateData = new HashMap<>();
    /**
     * 数据权限范围
     * null：表示全部数据权限
     */
    List<Long> dataScopeList;
    /**
     * 角色列表
     */
    List<String> roleScopeList;
    /**
     * 多语音
     */
    Locale locale;
    /**
     * 获取整体参数
     * @return 上下文数据
     */
    public Iterator<Map.Entry<String, String>> getSummary() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(this.userId));
        map.put("token", this.token);
        map.put("powers", this.powers);
        map.put("appId", this.appId);
        map.put("clientId", this.clientId);
        map.put("tenantId", String.valueOf(this.tenantId));
        map.put("realIpAddress", this.realIpAddress);
        map.put("traceId", this.traceId);
        return map.entrySet().iterator();
    }
}
