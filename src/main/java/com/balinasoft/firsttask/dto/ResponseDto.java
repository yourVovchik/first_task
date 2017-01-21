package com.balinasoft.firsttask.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {
    int status = 200;

    T data;

    public ResponseDto() {
    }

    public ResponseDto(T value) {
        data = value;
    }
}
