package org.ares.cloud.base.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
import org.ares.cloud.base.config.AppleMapsProperties;
import org.ares.cloud.base.service.IAppleMapTokenService;
import org.ares.cloud.base.utils.MapKitJwtGenerator;
import org.ares.cloud.common.exception.BusinessException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/8/9 12:48
 */
@Service
public class AppleMapTokenServiceImpl implements IAppleMapTokenService {

    @Resource
    private AppleMapsProperties properties;

    @Override
    public String generateToken() {
        ECPrivateKey privateKey = null;
        try {
//            privateKey = this.loadPrivateKey(properties.getPrivateKeyPath());
//
//            Instant now = Instant.now();
//            Instant exp = now.plusSeconds(properties.getExpireSeconds()); // 有效期
//
//            return Jwts.builder()
//                    .header()
//                    .keyId(properties.getKeyId()) // kid
//                    .type("JWT") // typ
//                    .and()
//                    .issuer(properties.getTeamId()) // iss
//                    .issuedAt(Date.from(now))       // iat
//                    .expiration(Date.from(exp))    // exp
//                    .claim("origin", properties.getOrigin()) // 自定义字段
//                    .signWith(privateKey, Jwts.SIG.ES256)    // ES256 签名
//                    .compact();
            String privateKeyP8 = Files.readString(Paths.get("/Users/hg/workspace/java/ares-cloud/ares-cloud-internal/ares-cloud-base-server/src/main/resources/AuthKey_DNRWXXA4L2.p8"));
            String teamId = "3K48PA624M";
            String keyId = "DNRWXXA4L2";

            MapKitJwtGenerator generator = new MapKitJwtGenerator();
            String jwt = generator.generateJwt(teamId, keyId, privateKeyP8);
            return jwt;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 加载私钥
     *
     * @param fileName 私钥文件路径
     * @return 私钥对象
     * @throws IOException
     * @throws GeneralSecurityException
     */

    private ECPrivateKey loadPrivateKey(String fileName) throws IOException, GeneralSecurityException {
        ClassPathResource resource = new ClassPathResource(fileName);

        try (InputStream inputStream = resource.getInputStream()) {
            String keyContent = new String(inputStream.readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(keyContent);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("EC");
            return (ECPrivateKey) kf.generatePrivate(spec);
        }
    }
}
