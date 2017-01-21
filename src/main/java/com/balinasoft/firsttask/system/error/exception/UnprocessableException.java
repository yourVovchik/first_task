package com.balinasoft.firsttask.system.error.exception;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class UnprocessableException extends ApiException {

    public UnprocessableException() {
        super(UNPROCESSABLE_ENTITY);
    }

    public UnprocessableException(String error) {
        super(error, UNPROCESSABLE_ENTITY);
    }
}
