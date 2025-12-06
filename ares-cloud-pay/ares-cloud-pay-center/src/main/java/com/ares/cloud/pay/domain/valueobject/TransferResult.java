package com.ares.cloud.pay.domain.valueobject;


/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/6/21 01:38
 */
public class TransferResult {
    private boolean success;
    private String transactionId;
    private String message;

    public static TransferResult success(String transactionId, String message) {
        TransferResult result = new TransferResult();
        result.success = true;
        result.transactionId = transactionId;
        result.message = message;
        return result;
    }

    public static TransferResult failure(String message) {
        TransferResult result = new TransferResult();
        result.success = false;
        result.message = message;
        return result;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getTransactionId() { return transactionId; }
    public String getMessage() { return message; }
}
