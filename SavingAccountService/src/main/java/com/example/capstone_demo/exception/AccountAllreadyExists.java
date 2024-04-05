package com.example.capstone_demo.exception;

public class AccountAllreadyExists extends Throwable {
    public AccountAllreadyExists(String message) {
        super(message);
    }
}
