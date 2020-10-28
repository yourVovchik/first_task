package com.balinasoft.firsttask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUserDtoIn {
    @NotBlank
    @Size(min = 4, max = 32)
    @Pattern(regexp = "[a-z0-9_\\-.@]+")
    String login;

    @NotBlank
    @Size(min = 8, max = 500)
    String password;
}
