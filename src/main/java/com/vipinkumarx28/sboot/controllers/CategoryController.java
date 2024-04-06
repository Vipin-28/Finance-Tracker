package com.vipinkumarx28.sboot.controllers;


import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@Slf4j
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategoryByIdOrName(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String name) throws CategoryNotFoundException {
        return categoryService.getCategoryByIdOrName(categoryId, name);
    }

    @PostMapping
    public ResponseEntity<?> addNewCategory(@RequestBody @Valid Category category) throws CategoryExistsException {
        return categoryService.addCategory(category);
    }

    @PutMapping(path = "/{oldCategoryName}")
    public ResponseEntity<?> updateCategory(@PathVariable @NotNull @NotEmpty String oldCategoryName, @RequestBody @Valid Category category) throws CategoryNotFoundException {
        return categoryService.updateCategory(oldCategoryName, category);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategoryByName(@RequestBody List<String> categoryList) throws CategoryNotFoundException {
        return categoryService.deleteCategoryByName(categoryList);
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable @Positive Long categoryId) throws CategoryNotFoundException {
        return categoryService.deleteCategoryById(categoryId);
    }

}
