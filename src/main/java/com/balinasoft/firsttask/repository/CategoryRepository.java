package com.balinasoft.firsttask.repository;

import com.balinasoft.firsttask.domain.api2.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(String name);

}
