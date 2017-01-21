package com.balinasoft.firsttask.system.error.handler.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorDto {
    int status;
    String error;

    public ErrorDto(int status, String error) {
        this.status = status;
        this.error = error;
    }
}
