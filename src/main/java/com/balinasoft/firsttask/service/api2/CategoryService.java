package com.balinasoft.firsttask.service.api2;

import com.balinasoft.firsttask.dto.api2.CategoryDtoIn;
import com.balinasoft.firsttask.dto.api2.CategoryDtoOut;
import java.util.List;

public interface CategoryService {

    CategoryDtoOut get(long id);
    List<CategoryDtoOut> getAll();
    void delete(long id);
    CategoryDtoOut save(CategoryDtoIn categoryDtoIn);

    CategoryDtoOut getByName(String name);

}
