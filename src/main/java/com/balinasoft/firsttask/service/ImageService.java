package com.balinasoft.firsttask.service;

import com.balinasoft.firsttask.dto.ImageDtoIn;
import com.balinasoft.firsttask.dto.ImageDtoOut;

public interface ImageService {
    ImageDtoOut uploadImage(ImageDtoIn imageDtoIn, long l);
}
