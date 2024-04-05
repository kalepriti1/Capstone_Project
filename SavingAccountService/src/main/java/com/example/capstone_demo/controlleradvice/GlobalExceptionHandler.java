package com.example.capstone_demo.controlleradvice;

import com.example.capstone_demo.exception.AccountAllreadyExists;
import com.example.capstone_demo.exception.AccountNotFound;
import com.example.capstone_demo.exception.InsufficientBalanceException;
import com.example.capstone_demo.exception.InvalidBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountAllreadyExists.class)
    public ResponseEntity<ErrorInfo> handleInvalidPasswordException(AccountAllreadyExists e){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<ErrorInfo> handleIdNotFoundException(AccountNotFound e){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorInfo> handleIdExistsException(InsufficientBalanceException e){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidBalanceException.class)
    public ResponseEntity<ErrorInfo> Invalid(InvalidBalanceException e){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(e.getMessage());
        errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }
}
