package org.ares.cloud.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "ares.gateway")
public class AresProperties {
    private List<String> publicPaths;
    public List<String> getPublicPaths() {
        return publicPaths;
    }
    public void setPublicPaths(List<String> publicPaths) {
        this.publicPaths = publicPaths;
    }
}
