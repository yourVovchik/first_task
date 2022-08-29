package com.balinasoft.firsttask.service;

import com.balinasoft.firsttask.dto.ImageDtoIn;
import com.balinasoft.firsttask.dto.ImageDtoOut;

import java.util.List;

public interface ImageService {
    ImageDtoOut uploadImage(ImageDtoIn imageDtoIn);

    void deleteImage(int id);

    List<ImageDtoOut> getImages(int page);

    List<ImageDtoOut> getByCategoryIds(List<Long> categoryIds, int page);
}
