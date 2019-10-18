package com.balinasoft.firsttask.dto.api2;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoTypeDtoOut {

    @ApiModelProperty(required = true)
    int id;

    @ApiModelProperty(required = true)
    String name;

}
