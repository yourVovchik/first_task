package com.balinasoft.firsttask.dto.api2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDtoIn {
    @NotNull
    @NotBlank
    @Length(max = 30)
    private String name;
}
