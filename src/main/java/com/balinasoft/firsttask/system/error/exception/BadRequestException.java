package com.balinasoft.firsttask.system.error.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends ApiException {

    public BadRequestException() {
        super(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST);
    }

    public BadRequestException(String error) {
        super(error, BAD_REQUEST);
    }
}
