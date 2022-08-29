package com.balinasoft.firsttask.repository;


import com.balinasoft.firsttask.domain.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query("from Image i where i.user.id = :userId order by i.date desc")
    List<Image> findByUser(@Param("userId") int userId, Pageable pageable);

    @Query("from Image i where i.id :ids")
    List<Image> findByCategoryIds(@Param("ids") List<Long> categoryId,Pageable pageable);
}
