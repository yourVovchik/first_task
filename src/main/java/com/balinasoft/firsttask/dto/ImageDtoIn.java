package com.balinasoft.firsttask.dto;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDtoIn {
    @NotNull
    @NotBlank
    //2 mb in base 64
    @ApiModelProperty(required = true, notes = "Base64 jpeg or png file",
            allowableValues = "max 1280 by height or width")
    @Length(max = (2 * 1024 * 1024) * 4)
    String base64Image;

    @Min(0)
    @ApiModelProperty(required = true, notes = "Unix Timestamp")
    long time;

    @ApiModelProperty(required = true, allowableValues = "range[-90, 90]", notes = "Latitude")
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    double lat;

    @ApiModelProperty(required = true, allowableValues = "range[-180, 180]", notes = "Longitude")
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    double lng;
}
