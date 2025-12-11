package com.forest.forest_backend.controller;


import com.forest.forest_backend.entity.Category;
import com.forest.forest_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository){
            this.categoryRepository=categoryRepository;
    }
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();

    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryRepository.save(category);
    }
}
