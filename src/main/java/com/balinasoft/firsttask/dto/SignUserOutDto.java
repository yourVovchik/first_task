package com.balinasoft.firsttask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUserOutDto {
    int userId;

    String login;

    String token;
}
