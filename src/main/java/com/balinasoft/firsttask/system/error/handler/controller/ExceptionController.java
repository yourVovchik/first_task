package com.balinasoft.firsttask.system.error.handler.controller;

import com.balinasoft.firsttask.system.error.exception.ApiException;
import com.balinasoft.firsttask.system.error.handler.dto.ErrorDto;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handleRestException(ApiException e,
                                                        HttpServletRequest request) {
        ErrorDto errorDto = new ErrorDto(
                e.getHttpStatus().value(),
                e.getError());
        return new ResponseEntity<>(errorDto, e.getHttpStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException e,
                                                                  HttpServletRequest request) {
        String error = "entity-not-found";
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.NOT_FOUND.value(),
                error);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorDto> handleMultipartException(MultipartException e,
                                                             HttpServletRequest request) {
        String error = "file-upload-error";
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error);
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
