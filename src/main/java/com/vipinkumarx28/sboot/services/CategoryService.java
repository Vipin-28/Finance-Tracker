package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.exceptions.UserDoesNotExists;
import com.vipinkumarx28.sboot.exceptions.UserNameRequiredException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    public ResponseEntity<?> addCategory(Long userId, Category category) throws CategoryExistsException, UserNameRequiredException;

    public ResponseEntity<?> getCategory(Long userId, Long categoryId, String filterKey, String filterValue) throws CategoryNotFoundException, UserNameRequiredException;

    public ResponseEntity<?> deleteCategoryById(Long categoryId) throws CategoryNotFoundException;

    public ResponseEntity<?> deleteCategoryByName(Long userId, List<String> categoryNameList) throws CategoryNotFoundException, UserNameRequiredException;

    public ResponseEntity<?> updateCategory(Long userId, String oldCategoryName, Category category) throws CategoryNotFoundException, UserNameRequiredException, UserDoesNotExists;

    public void initialPopulateCategories(User user);
}
