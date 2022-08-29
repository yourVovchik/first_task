package com.balinasoft.firsttask.service.api2;

import com.balinasoft.firsttask.domain.api2.Category;
import com.balinasoft.firsttask.dto.api2.CategoryDtoIn;
import com.balinasoft.firsttask.dto.api2.CategoryDtoOut;
import com.balinasoft.firsttask.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryDtoOut save (CategoryDtoIn categoryDtoIn){
        return toOut(categoryRepository.save(fromIn(categoryDtoIn)));
    }

    @Override
    public CategoryDtoOut getByName(String name) {
        return toOut(categoryRepository.findByName(name));
    }

    public void delete(long id){
        categoryRepository.delete(id);
    }

    @Override
    public CategoryDtoOut get(long id) {
        return toOut(categoryRepository.getOne(id));
    }

    public List<CategoryDtoOut> getAll(){
        return categoryRepository.findAll().stream().map(this::toOut).collect(Collectors.toList());
    }


    private Category fromIn(CategoryDtoIn categoryDtoIn){
        Category category = new Category();
        category.setName(categoryDtoIn.getName());
        return category;
    }

    private CategoryDtoOut toOut(Category category){
        return new CategoryDtoOut(category.getId(),category.getName());
    }
}
