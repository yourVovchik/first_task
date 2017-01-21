package com.balinasoft.firsttask.system.error.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}
