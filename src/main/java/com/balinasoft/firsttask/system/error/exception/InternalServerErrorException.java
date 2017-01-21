package com.balinasoft.firsttask.system.error.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class InternalServerErrorException extends ApiException {

    public InternalServerErrorException() {
        super(INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String error) {
        super(error, INTERNAL_SERVER_ERROR);
    }
}
