package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    public ResponseEntity<?> addCategory(Category category) throws CategoryExistsException;

    public ResponseEntity<?> getCategoryByIdOrName(Long categoryId, String name) throws CategoryNotFoundException;

    public ResponseEntity<?> deleteCategoryById(Long categoryId) throws CategoryNotFoundException;

    public ResponseEntity<?> deleteCategoryByName(List<String> categoryNameList) throws CategoryNotFoundException;

    public ResponseEntity<?> updateCategory(String oldCategoryName, Category category) throws CategoryNotFoundException;
}
