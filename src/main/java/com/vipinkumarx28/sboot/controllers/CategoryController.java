package com.vipinkumarx28.sboot.controllers;


import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getCategoryByIdOrName(@RequestParam(required = false) Long id, @RequestParam(required = false) String name){

        return null;
    }

    @PostMapping
    public String addNewCategory(@RequestBody Category category){


        return null;
    }

    @DeleteMapping
    public String deleteCategoryByIdOrName(@RequestParam Long Id, @RequestParam String name){
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
