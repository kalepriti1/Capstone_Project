package com.natwest.walletservice.exception;

public class WalletDoesntExistsException extends Exception{
    public WalletDoesntExistsException(String msg){
        super(msg);
    }
}
