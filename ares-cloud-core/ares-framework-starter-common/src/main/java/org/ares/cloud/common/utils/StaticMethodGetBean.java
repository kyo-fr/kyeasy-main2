package org.ares.cloud.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: 静态方法获取bean
 * @date 2024/9/29 15:42
 */
@Component
public class StaticMethodGetBean implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        StaticMethodGetBean.applicationContext = applicationContext;
    }

    /**
     * 获取bean
     * @param clazz 类型
     * @return 对应类型的本
     */
    public static <T> T  getBean(Class<T> clazz) {
        return applicationContext != null?applicationContext.getBean(clazz):null;
    }
}
