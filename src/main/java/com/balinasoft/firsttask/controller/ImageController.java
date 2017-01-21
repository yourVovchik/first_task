package com.balinasoft.firsttask.controller;

import com.balinasoft.firsttask.dto.ImageDtoIn;
import com.balinasoft.firsttask.dto.ImageDtoOut;
import com.balinasoft.firsttask.dto.ResponseDto;
import com.balinasoft.firsttask.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.balinasoft.firsttask.system.StaticWrapper.wrap;
import static com.balinasoft.firsttask.util.SecurityContextHolderWrapper.currentUserId;

@RestController
@RequestMapping("/api/v1/user/{userId}")
@Api(tags = "Images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "image", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Upload image", response = ImageDtoOut.class)
    @ApiResponses({
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "file-upload-error")
    })
    public ResponseDto uploadImage(@RequestBody @Valid ImageDtoIn imageDtoIn) {
        return wrap(imageService.uploadImage(imageDtoIn, currentUserId()));
    }
}
