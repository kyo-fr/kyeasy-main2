package org.ares.cloud.listener;

import org.ares.cloud.configs.SecurityConfig;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

public class ConfigChangeListener {

    @Component
    public static class GatewayConfigChangeListener {

        private final SecurityConfig securityConfig;

        public GatewayConfigChangeListener(SecurityConfig securityConfig) {
            this.securityConfig = securityConfig;
        }

        @EventListener
        public void onRefresh(RefreshScopeRefreshedEvent event) {
            System.out.println("配置更新");
            //securityConfig.updateAuthorization();
        }
    }
}
