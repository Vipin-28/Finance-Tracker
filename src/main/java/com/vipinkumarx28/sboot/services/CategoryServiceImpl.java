package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> addCategory(Category category) throws CategoryExistsException {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            log.error("category " + category.getName() + " already exists...");
            throw new CategoryExistsException("Category already exists in the db.");
        }
        log.info("Saving category by name: {}", category.getName());
        categoryRepository.save(category);
        return new ResponseEntity<>("Category saved successfully.", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteCategoryById(Long categoryId) throws CategoryNotFoundException {
        if (!categoryRepository.findById(categoryId).isPresent()) {//not present
            log.error("category id: {} does not exist...", categoryId);
            throw new CategoryNotFoundException("Category does not exist...");
        }
        categoryRepository.deleteById(categoryId);
        return new ResponseEntity<>("Category saved successfully.", HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<?> deleteCategoryByName(String categoryName) throws CategoryNotFoundException {
        if(!categoryRepository.findByName(categoryName).isPresent()){
            log.error("category name: {} does not exist...", categoryName);
            throw new CategoryNotFoundException("Category does not exist...");
        }
        Category _category = (Category) categoryRepository.findByName(categoryName).get();
        categoryRepository.deleteById(_category.getId());
        return new ResponseEntity<>("Category deleted successfully.", HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<?> getCategoryByIdOrName(Long categoryId, String name) throws CategoryNotFoundException {
        List<Category> categoryList = new ArrayList<>();
        try {
            if (Objects.isNull(categoryId) && Objects.isNull(name)) {// both get all
                categoryList = categoryRepository.findAll();
            } else if (Objects.isNull(categoryId)) {
                Category category = (Category) categoryRepository.findByName(name).get();
                categoryList.add(category);
            } else {
                Category category = categoryRepository.findById(categoryId).get();
                categoryList.add(category);
            }
        }catch(NoSuchElementException e){
            log.error("");
            throw new CategoryNotFoundException("Category not found exception.");
        }
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }




}
