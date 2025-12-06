//package org.ares.cloud.infrastructure.payment.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.ares.cloud.common.model.Result;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
///**
// * 支付异常处理器
// */
//@Slf4j
//@RestControllerAdvice
//public class PaymentExceptionHandler {
//
//    /**
//     * 处理支付异常
//     */
//    @ExceptionHandler(PaymentException.class)
//    public Result<String> handlePaymentException(PaymentException e) {
//        log.error("Payment error: {}", e.getMessage(), e);
//       // return Result.error(e.getErrorCode(), e.getMessage());
//    }
//
//    /**
//     * 处理签名异常
//     */
//    @ExceptionHandler(SignatureException.class)
//    public Result<String> handleSignatureException(SignatureException e) {
//        log.error("Signature error: {}", e.getMessage(), e);
//        //return Result.error(PaymentErrorCode.SIGN_INVALID.getCode(), e.getMessage());
//    }
//
//    /**
//     * 处理商户异常
//     */
//    @ExceptionHandler(MerchantException.class)
//    public Result<Void> handleMerchantException(MerchantException e) {
//        log.error("Merchant error: {}", e.getMessage(), e);
//        return Result.error(PaymentErrorCode.MERCHANT_NOT_FOUND.getCode(), e.getMessage());
//    }
//
//    /**
//     * 处理风控异常
//     */
//    @ExceptionHandler(RiskControlException.class)
//    public Result<Void> handleRiskControlException(RiskControlException e) {
//        log.error("Risk control error: {}", e.getMessage(), e);
//        return Result.error(PaymentErrorCode.RISK_REJECTED.getCode(), e.getMessage());
//    }
//}