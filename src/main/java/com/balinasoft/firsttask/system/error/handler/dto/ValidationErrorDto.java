package com.balinasoft.firsttask.system.error.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ValidationErrorDto extends ErrorDto {

    private List<ValidationErrorMessage> valid;

    public ValidationErrorDto(int status, String error, List<ValidationErrorMessage> valid) {
        super(status, error);
        this.valid = valid;
    }


    @Setter
    @Getter
    @AllArgsConstructor
    public static class ValidationErrorMessage {
        String field;
        String message;
    }
}
