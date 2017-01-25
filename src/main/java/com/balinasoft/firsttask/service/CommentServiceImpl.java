package com.balinasoft.firsttask.service;

import com.balinasoft.firsttask.domain.Comment;
import com.balinasoft.firsttask.domain.Image;
import com.balinasoft.firsttask.dto.CommentDtoIn;
import com.balinasoft.firsttask.dto.CommentDtoOut;
import com.balinasoft.firsttask.repository.CommentRepository;
import com.balinasoft.firsttask.repository.ImageRepository;
import com.balinasoft.firsttask.system.error.ApiAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.balinasoft.firsttask.util.SecurityContextHolderWrapper.currentUserId;

@Service
public class CommentServiceImpl implements CommentService {

    private final ImageRepository imageRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ImageRepository imageRepository, CommentRepository commentRepository) {
        this.imageRepository = imageRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDtoOut> get(int imageId, int page) {
        Image image = imageRepository.findOne(imageId);
        ApiAssert.notFound(image == null);
        ApiAssert.notFound(image.getUser().getId() != currentUserId());
        List<Comment> comments = commentRepository.findByImage_Id(imageId, new PageRequest(page, 20));
        return comments.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CommentDtoOut add(int imageId, CommentDtoIn commentDtoIn) {
        Image image = imageRepository.findOne(imageId);
        ApiAssert.notFound(image == null);
        ApiAssert.notFound(image.getUser().getId() != currentUserId());
        Comment comment = new Comment();
        comment.setDate(new Date());
        comment.setText(commentDtoIn.getText());
        comment.setImage(image);
        comment = commentRepository.save(comment);
        return toDto(comment);
    }

    @Override
    public void delete(int imageId, int commentId) {
        Comment comment = commentRepository.findByIdAndImage_Id(commentId, imageId);
        ApiAssert.notFound(comment == null);
        ApiAssert.notFound(comment.getImage().getUser().getId() != currentUserId());
        commentRepository.delete(commentId);
    }

    private CommentDtoOut toDto(Comment c) {
        return new CommentDtoOut(c.getId(),
                c.getDate(),
                c.getText());
    }
}
