package com.natwest.walletservice.exception;

public class InsufficientWalletBalanceException extends Exception {
    public InsufficientWalletBalanceException(String msg){
        super(msg);
    }
}
