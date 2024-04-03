package com.vipinkumarx28.sboot.controllers;


import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategoryByIdOrName(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String name) throws CategoryNotFoundException {
        return categoryService.getCategoryByIdOrName(categoryId, name);
    }

    @PostMapping
    public ResponseEntity<?> addNewCategory(@RequestBody Category category) throws CategoryExistsException {
        return categoryService.addCategory(category);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategoryByIdOrName(@RequestParam Long categoryId, @RequestParam String name){
        //return categoryService.deleteCategory(categoryId, name);
        return null;
    }

    @DeleteMapping(path = "/{categoryId}")
    public String deleteCategoryById(@PathVariable Long id){
        return null;
    }

    @PutMapping(path = "/{oldCategoryId}")
    public String updateCategory(@PathVariable Long oldCategoryId, @RequestBody Category category){
        return null;
    }
}
