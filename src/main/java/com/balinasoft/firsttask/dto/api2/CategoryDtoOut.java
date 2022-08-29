package com.balinasoft.firsttask.dto.api2;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDtoOut {

    @ApiModelProperty(required = true)
    long id;

    @ApiModelProperty(required = true)
    String name;
}
