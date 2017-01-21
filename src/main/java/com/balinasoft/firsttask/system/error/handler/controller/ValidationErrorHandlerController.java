package com.balinasoft.firsttask.system.error.handler.controller;


import com.balinasoft.firsttask.system.error.handler.dto.ValidationErrorDto;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ValidationErrorHandlerController {

    private static final List<String> codesOrder = Arrays.asList(
            "NotBlank", "NotNull", "Length", "Pattern");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDto> methodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ValidationErrorDto validationError = new ValidationErrorDto(
                httpStatus.value(),
                "validation-error",
                convertErrors(e.getBindingResult().getAllErrors())
        );

        return new ResponseEntity<>(validationError, httpStatus);
    }

    /**
     * Get first error for each error focusing on code.
     * construct ArrayList of {@link ValidationErrorDto.ValidationErrorMessage}.
     *
     * @param allErrors List of {@link ObjectError}
     */
    private List<ValidationErrorDto.ValidationErrorMessage> convertErrors(List<ObjectError> allErrors) {
        ArrayList<ValidationErrorDto.ValidationErrorMessage> messages = new ArrayList<>(allErrors.size());

        //add error to map, if not contains for given fiels, or replace if order smaller then exist
        Map<String, FieldError> fieldErrorMap = new HashMap<>(allErrors.size(), 1);
        for (ObjectError allError : allErrors) {
            if (allError instanceof FieldError) {
                FieldError fieldError = (FieldError) allError;
                if (fieldErrorMap.containsKey(fieldError.getField())) {
                    FieldError addedError = fieldErrorMap.get(fieldError.getField());
                    if (indexOfCode(addedError.getCode()) < indexOfCode(fieldError.getCode())) {
                        continue;
                    }
                }
                fieldErrorMap.put(fieldError.getField(), fieldError);
            }
        }

        for (FieldError fieldError : fieldErrorMap.values()) {
            messages.add(new ValidationErrorDto.ValidationErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return messages;
    }


    private int indexOfCode(String code) {
        int index = codesOrder.indexOf(code);
        if (index >= 0) {
            return index;
        }
        return Integer.MAX_VALUE;
    }
}
