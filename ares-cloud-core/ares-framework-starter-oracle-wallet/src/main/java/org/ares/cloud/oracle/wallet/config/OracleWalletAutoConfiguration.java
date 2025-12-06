package org.ares.cloud.oracle.wallet.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Oracle钱包自动配置
 *
 * @author hugo
 * @version 1.0.0
 * @date 2024/03/15
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(name = "oracle.security.pki.OracleWallet")
@ConditionalOnProperty(prefix = "ares.oracle.wallet", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(OracleWalletProperties.class)
public class OracleWalletAutoConfiguration {

    private final OracleWalletProperties properties;

    private static final String[] WALLET_FILES = {
        "cwallet.sso",
        "ewallet.p12",
        "tnsnames.ora",
        "sqlnet.ora"
    };

    public OracleWalletAutoConfiguration(OracleWalletProperties properties) {
        this.properties = properties;
    }

    @Bean
    public OracleWalletInitializer oracleWalletInitializer() {
        return new OracleWalletInitializer(properties.getPath());
    }

    @Slf4j
    public static class OracleWalletInitializer {
        private final String walletPath;
        private Path walletDir;

        public OracleWalletInitializer(String walletPath) {
            this.walletPath = walletPath;
        }

        @PostConstruct
        public void initialize() throws IOException {
            log.info("Initializing Oracle wallet from path: {}", walletPath);
            walletDir = Files.createTempDirectory("oracle_wallet");
            
            for (String fileName : WALLET_FILES) {
                String resourcePath = walletPath + "/" + fileName;
                Resource resource = new ClassPathResource(resourcePath);
                if (resource.exists()) {
                    Path targetPath = walletDir.resolve(fileName);
                    try {
                        Files.copy(resource.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                        log.debug("Copied wallet file: {}", fileName);
                    } catch (IOException e) {
                        log.error("Failed to copy wallet file: " + fileName, e);
                        throw new RuntimeException("Failed to copy wallet file: " + fileName, e);
                    }
                } else {
                    log.warn("Wallet file not found: {}", resourcePath);
                }
            }

            System.setProperty("oracle.net.wallet_location", walletDir.toString());
            System.setProperty("oracle.net.tns_admin", walletDir.toString());
            log.info("Oracle wallet initialized at: {}", walletDir);
        }

        @PreDestroy
        public void cleanup() {
            if (walletDir != null) {
                try {
                    Files.walk(walletDir)
                        .sorted((a, b) -> b.compareTo(a))
                        .map(Path::toFile)
                        .forEach(File::delete);
                    log.info("Oracle wallet cleaned up successfully");
                } catch (IOException e) {
                    log.warn("Failed to cleanup Oracle wallet directory", e);
                }
            }
        }
    }
} 