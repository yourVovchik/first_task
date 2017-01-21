package com.balinasoft.firsttask.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDtoOut {
    @ApiParam(required = true)
    Integer id;

    @ApiParam(required = true)
    String url;
}
