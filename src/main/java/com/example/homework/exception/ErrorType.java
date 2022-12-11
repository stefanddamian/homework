package com.example.homework.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorType {
    CREATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    RATE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR),
    RATE_CLIENT_ERROR(HttpStatus.SERVICE_UNAVAILABLE),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND),
    INVALID_CURRENCY(HttpStatus.INTERNAL_SERVER_ERROR);

    @Getter
    private final HttpStatus responseStatus;

    ErrorType(HttpStatus responseStatus){
        this.responseStatus = responseStatus;
    }

}
