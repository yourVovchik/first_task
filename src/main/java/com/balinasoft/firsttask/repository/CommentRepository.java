package com.balinasoft.firsttask.repository;

import com.balinasoft.firsttask.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByImage_Id(int imageId, Pageable pageable);

    Comment findByIdAndImage_Id(int id, int imageId);
}
