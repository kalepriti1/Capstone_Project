package com.natwest.walletservice.exception;

public class BelowWithdrawalThresholdException extends Exception {
    public BelowWithdrawalThresholdException(String msg) {
        super(msg);
    }
}
