package com.balinasoft.firsttask.system.error.exception;

import org.springframework.http.HttpStatus;

public class SecurityUserException extends ApiException {
    public SecurityUserException(String error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
