package com.example.homework.exception;

import lombok.Getter;

public class AccountsServiceException extends RuntimeException {
    @Getter
    private final ErrorType errorType;

    public AccountsServiceException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public AccountsServiceException(ErrorType errorType, String message, Throwable throwable) {
        super(message, throwable);
        this.errorType = errorType;
    }
}
