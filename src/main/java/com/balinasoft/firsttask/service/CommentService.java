package com.balinasoft.firsttask.service;

import com.balinasoft.firsttask.dto.CommentDtoIn;
import com.balinasoft.firsttask.dto.CommentDtoOut;

import java.util.List;

public interface CommentService {
    List<CommentDtoOut> get(int imageId, int page);

    CommentDtoOut add(int imageId, CommentDtoIn commentDtoIn);

    void delete(int imageId, int commentId);
}
