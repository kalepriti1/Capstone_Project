package com.example.capstone_demo.controlleradvice;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorInfo {
    private String errorMessage;
    private HttpStatus HttpStatus;
    private LocalDateTime localDateTime;


    public ErrorInfo() {

    }


    public ErrorInfo(String errorMessage, org.springframework.http.HttpStatus httpStatus, LocalDateTime localDateTime) {
        this.errorMessage = errorMessage;
        HttpStatus = httpStatus;
        this.localDateTime = localDateTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public org.springframework.http.HttpStatus getHttpStatus() {
        return HttpStatus;
    }

    public void setHttpStatus(org.springframework.http.HttpStatus httpStatus) {
        HttpStatus = httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
