package com.balinasoft.firsttask.system.error.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ForbiddenException extends ApiException {

    public ForbiddenException() {
        super(FORBIDDEN);
    }

    public ForbiddenException(String error) {
        super(error, FORBIDDEN);
    }
}
