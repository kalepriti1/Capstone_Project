package com.natwest.walletservice.controllerAdvice;

import com.natwest.walletservice.exception.BelowWithdrawalThresholdException;
import com.natwest.walletservice.exception.InsufficientWalletBalanceException;
import com.natwest.walletservice.exception.WalletDoesntExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BelowWithdrawalThresholdException.class)
    public ResponseEntity<ErrorInfo> handleExceptionForWithdrawalThreshold
            (BelowWithdrawalThresholdException e) {

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientWalletBalanceException.class)
    public ResponseEntity<ErrorInfo> handleExceptionForInsufficientWalletBalance
            (InsufficientWalletBalanceException e) {

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletDoesntExistsException.class)
    public ResponseEntity<ErrorInfo> handleExceptionForWalletDoesntExists
            (WalletDoesntExistsException e) {

        ErrorInfo errorInfo = new ErrorInfo();

        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
