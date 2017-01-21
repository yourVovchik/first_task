package com.balinasoft.firsttask.service.security;


import com.balinasoft.firsttask.dto.SignUserDtoIn;
import com.balinasoft.firsttask.dto.SignUserOutDto;

public interface SecurityService {
    SignUserOutDto signup(SignUserDtoIn userDto);

    SignUserOutDto signin(SignUserDtoIn userDto);
}
