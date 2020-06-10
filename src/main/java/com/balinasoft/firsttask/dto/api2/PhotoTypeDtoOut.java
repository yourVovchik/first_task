package com.balinasoft.firsttask.dto.api2;

import com.balinasoft.firsttask.system.json.ImageUrlJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @JsonSerialize(using = ImageUrlJsonSerializer.class)
    String image;

}
