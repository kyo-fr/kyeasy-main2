package org.ares.cloud.infrastructure.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ares.cloud.infrastructure.security.SignatureService;
import org.ares.cloud.common.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.HashMap;

@Aspect
@Component
@RequiredArgsConstructor
public class PaymentSignatureAspect {
//    private final SignatureService signatureService;
//    private final MerchantPaymentChannelRepository merchantPaymentChannelRepository;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(PaymentSignatureAspect.class);

    @Pointcut("@annotation(org.ares.cloud.infrastructure.annotation.VerifySignature)")
    public void signatureVerifyPointcut() {}

    @Before("signatureVerifyPointcut()")
    public void verifySignature(JoinPoint joinPoint) {
        try {
            // 获取请求参数
            Object[] args = joinPoint.getArgs();
            if (args.length == 0) {
                throw new BusinessException("Missing request body");
            }

            // 将请求对象转换为Map
            Map<String, Object> requestMap = objectMapper.convertValue(args[0], Map.class);
            if (requestMap == null) {
                throw new BusinessException("Invalid request format");
            }

            String merchantId = (String) requestMap.get("merchantId");
            if (merchantId == null) {
                throw new BusinessException("Missing or invalid merchantId");
            }

//            // 获取商户配置
//            var merchantChannel = merchantPaymentChannelRepository
//                .findByMerchantId(merchantId)
//                .orElseThrow(() -> new BusinessException("Invalid merchant"));
//
//            // 验证签名
//            String sign = (String) requestMap.get("sign");
//            if (sign == null) {
//                throw new BusinessException("Missing or invalid signature");
//            }
//
//            // 创建副本并移除sign字段后验证签名
//            Map<String, Object> requestMapCopy = new HashMap<>(requestMap);
//            requestMapCopy.remove("sign");
//
//            if (!signatureService.verifySign(requestMapCopy, merchantChannel.getSignKey(), sign)) {
//                throw new BusinessException("Invalid signature");
//            }
        } catch (Exception e) {
            logger.error("Error verifying signature: {}", e.getMessage(), e);
            throw new BusinessException("Failed to verify signature");
        }
    }
}
