package com.balinasoft.firsttask.controller.api1;

import com.balinasoft.firsttask.dto.ImageDtoOut;
import com.balinasoft.firsttask.dto.ResponseDto;
import com.balinasoft.firsttask.dto.api2.CategoryDtoIn;
import com.balinasoft.firsttask.dto.api2.CategoryDtoOut;
import com.balinasoft.firsttask.service.api2.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.balinasoft.firsttask.system.StaticWrapper.wrap;

@RestController
@RequestMapping("/api/category")
@Api(tags = "Comments")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all", response = CategoryDtoOut.class, responseContainer = "List")
    public ResponseDto getAll(){
        return wrap(categoryService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get by id", response = CategoryDtoOut.class)
    public ResponseDto getById(@PathVariable long id){
        return wrap(categoryService.get(id));
    }

    @PostMapping("/add")
    @Secured("ROLE_USER")
    @ApiOperation(value = "Add", response = CategoryDtoOut.class)
    public ResponseDto save(@RequestBody CategoryDtoIn categoryDtoIn){
        return wrap(categoryService.save(categoryDtoIn));
    }

    @PostMapping("/delete/{id}")
    @Secured("ROLE_USER")
    @ApiOperation(value = "Delete")
    public ResponseDto delete(long id){
        categoryService.delete(id);
        return wrap();
    }
}
