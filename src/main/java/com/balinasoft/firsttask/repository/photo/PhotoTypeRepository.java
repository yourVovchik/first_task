package com.balinasoft.firsttask.repository.photo;

import com.balinasoft.firsttask.domain.api2.PhotoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoTypeRepository extends JpaRepository<PhotoType, Integer> {

}
