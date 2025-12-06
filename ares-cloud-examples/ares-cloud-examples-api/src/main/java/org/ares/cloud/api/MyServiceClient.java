package org.ares.cloud.api;

import org.ares.cloud.api.fallback.MyServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "my-service-a",fallback = MyServiceClientFallback.class)
public interface MyServiceClient {

    @GetMapping("/api/endpoint")
    String seyHello(@RequestParam("param") String param);
}
