package com.example.homework.controller.advice;

import com.example.homework.exception.AccountsServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({AccountsServiceException.class})
    public ResponseEntity<?> handleAccountsServiceException(AccountsServiceException exception){
        log.error(exception.getMessage());
        return ResponseEntity.status(exception.getErrorType().getResponseStatus())
                .body(exception.getMessage());

    }
}
