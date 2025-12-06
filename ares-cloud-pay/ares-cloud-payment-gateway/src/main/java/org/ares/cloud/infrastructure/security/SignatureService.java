package org.ares.cloud.infrastructure.security;

import java.util.Map;

public interface SignatureService {
    boolean verifySign(Map<String, Object> params, String signKey, String sign);
    String generateSign(Map<String, Object> params, String signKey);
} 