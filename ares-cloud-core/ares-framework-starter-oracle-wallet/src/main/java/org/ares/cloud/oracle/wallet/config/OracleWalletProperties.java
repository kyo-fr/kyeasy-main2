package org.ares.cloud.oracle.wallet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Oracle钱包配置属性
 */
@Data
@ConfigurationProperties(prefix = "ares.oracle.wallet")
public class OracleWalletProperties {
    
    /**
     * 是否启用钱包
     */
    private boolean enabled = true;
    
    /**
     * 钱包文件路径
     */
    private String path = "oracle/wallet";
} 