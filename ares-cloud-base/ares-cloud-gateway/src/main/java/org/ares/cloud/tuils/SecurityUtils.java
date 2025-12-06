package org.ares.cloud.tuils;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: 认证工具
 * @date 2024/10/15 12:15
 */
public class SecurityUtils {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 检查地址是否开放
     * @param paths 开放地地址列表
     * @param currentPath 当前地址
     * @return 是否开放
     */
    public static  boolean isPathOpen (List<String> paths,String currentPath){
        if(paths == null){
            return false;
        }
        return paths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, currentPath));
    }
}
