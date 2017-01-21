package com.balinasoft.firsttask.system.error.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private String error;

    private HttpStatus httpStatus;

    public ApiException(HttpStatus httpStatus) {
        this.error = httpStatus.getReasonPhrase();
        this.httpStatus = httpStatus;
    }

    public ApiException(String error, HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
