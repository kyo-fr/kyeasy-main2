package org.ares.cloud.base.utils;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


public class MapKitJwtGenerator {
    private static final long EXPIRATION_IN_SECONDS = 3600; // 1小时

    /**
     * 生成 MapKit JS 使用的 JWT
     */
    public String generateJwt(String teamId, String keyId, String privateKeyP8)
            throws JoseException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        // 1. 解析 P8 内容
        String base64Key = privateKeyP8
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // 2. 构建 claims
        JwtClaims claims = new JwtClaims();
        claims.setIssuer(teamId);                     // iss
        claims.setAudience("maps-api.apple.com");     // aud
        claims.setIssuedAtToNow();                    // iat
        claims.setExpirationTimeMinutesInTheFuture(EXPIRATION_IN_SECONDS / 60); // exp

        // 3. 创建 JWS
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue("ES256");
        jws.setKey(privateKey);
        jws.getHeaders().setFullHeaderAsJsonString("{\"alg\":\"ES256\",\"kid\":\"" + keyId + "\"}");

        // 4. 返回 JWT
        return jws.getCompactSerialization();
    }

    public static void main(String[] args) {
        try {
            String privateKeyP8 = Files.readString(Paths.get("/Users/hg/workspace/java/ares-cloud/ares-cloud-internal/ares-cloud-base-server/src/main/resources/AuthKey_DNRWXXA4L2.p8"));
            String teamId = "3K48PA624M";
            String keyId = "DNRWXXA4L2";

            MapKitJwtGenerator generator = new MapKitJwtGenerator();
            String jwt = generator.generateJwt(teamId, keyId, privateKeyP8);

            System.out.println("Generated JWT for MapKit JS:\n" + jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
