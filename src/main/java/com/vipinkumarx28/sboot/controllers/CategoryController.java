package com.vipinkumarx28.sboot.controllers;


import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.exceptions.UserDoesNotExists;
import com.vipinkumarx28.sboot.exceptions.UserNameRequiredException;
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
    public ResponseEntity<?> getCategory(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String filterKey,
            @RequestParam(required = false) String filterValue,
            @RequestHeader(name="userId", required=true) Long userId) throws CategoryNotFoundException, UserNameRequiredException {
        return categoryService.getCategory(userId, categoryId, filterKey, filterValue);
    }

    @PostMapping
    public ResponseEntity<?> addNewCategory(
            @RequestHeader Long userId,
            @RequestBody @Valid Category category) throws CategoryExistsException, UserNameRequiredException {
        return categoryService.addCategory(userId, category);
    }

    @PutMapping(path = "/{oldCategoryName}")
    public ResponseEntity<?> updateCategory(
            @RequestHeader Long userId,
            @PathVariable @NotNull @NotEmpty String oldCategoryName,
            @RequestBody @Valid Category category) throws CategoryNotFoundException, UserNameRequiredException, UserDoesNotExists {
        return categoryService.updateCategory(userId, oldCategoryName, category);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategoryByName(
            @RequestHeader Long userId,
            @RequestBody List<String> categoryList) throws CategoryNotFoundException, UserNameRequiredException {
        return categoryService.deleteCategoryByName(userId, categoryList);
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable @Positive Long categoryId) throws CategoryNotFoundException {
        return categoryService.deleteCategoryById(categoryId);
    }

}
