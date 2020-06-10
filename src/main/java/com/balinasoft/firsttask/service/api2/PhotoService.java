package com.balinasoft.firsttask.service.api2;

import com.balinasoft.firsttask.domain.api2.PhotoType;
import com.balinasoft.firsttask.dto.api2.PhotoDtoOut;
import com.balinasoft.firsttask.dto.api2.PhotoTypeDtoOut;
import com.balinasoft.firsttask.repository.ImageRepository;
import com.balinasoft.firsttask.repository.photo.PhotoTypeRepository;
import com.balinasoft.firsttask.system.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final ImageRepository imageRepository;

    private final PhotoTypeRepository photoTypeRepository;

    @Transactional(readOnly = true)
    public Page<PhotoTypeDtoOut> getTypes(int page) {
        PageRequest pageRequest = new PageRequest(page, 20, new Sort("id"));
        Page<PhotoType> types = photoTypeRepository.findAll(pageRequest);
        return types.map(this::toDto);
    }

    public PhotoDtoOut savePhoto(MultipartFile photo, int typeId) {
        PhotoType photoType = photoTypeRepository.findOne(typeId);
        if (photoType == null) {
            throw new NotFoundException("Type not found");
        }

        return new PhotoDtoOut(UUID.randomUUID().toString());
    }

    private PhotoTypeDtoOut toDto(PhotoType photoType) {
        return new PhotoTypeDtoOut(photoType.getId(), photoType.getName(), photoType.getImage());
    }

}
