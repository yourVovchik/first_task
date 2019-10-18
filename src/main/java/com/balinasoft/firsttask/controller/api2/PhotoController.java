package com.balinasoft.firsttask.controller.api2;

import com.balinasoft.firsttask.dto.api2.PhotoDtoOut;
import com.balinasoft.firsttask.dto.api2.PhotoTypeDtoOut;
import com.balinasoft.firsttask.service.api2.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v2/photo")
@Api(tags = "Photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @RequestMapping(value = "type", method = GET)
    @ApiOperation(value = "Get photo types")
    public Page<PhotoTypeDtoOut> getTypes(@RequestParam(defaultValue = "0", required = false) int page) {
        return photoService.getTypes(page);
    }

    @RequestMapping(value = "", method = POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Upload photo")
    public PhotoDtoOut savePhoto(@RequestParam MultipartFile photo, @RequestParam int typeId, @RequestParam String name) {
        return photoService.savePhoto(photo, typeId);
    }

}
