package org.ares.cloud.api.fallback;

import org.ares.cloud.api.MyServiceClient;
import org.springframework.stereotype.Component;

@Component
public class MyServiceClientFallback implements MyServiceClient {
    @Override
    public String seyHello(String param) {
        // 降级逻辑
        return "Fallback response due to: Service is unavailable";
    }
}
