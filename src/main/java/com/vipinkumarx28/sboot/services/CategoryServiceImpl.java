package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public ResponseEntity<?> deleteCategoryById(Long categoryId) throws CategoryNotFoundException {
        if (!categoryRepository.findById(categoryId).isPresent()) {//not present
            log.error("category id: {} does not exist...", categoryId);
            throw new CategoryNotFoundException("Category does not exist...");
        }
        categoryRepository.deleteById(categoryId);
        return new ResponseEntity<>("Category deleted successfully.", HttpStatus.OK);
    }

    /*
    * loop over the list of categoryNameList
    * to find out if all the categories are present in db or not
    * if anyone category is not present then don't delete any of the category.
    */
    @Override
    public ResponseEntity<?> deleteCategoryByName(List<String> categoryNameList) throws CategoryNotFoundException {
        Boolean isPresent = true;
        for(String categoryName : categoryNameList) {
            Category _category = (Category) categoryRepository.findByName(categoryName).get();
            if(Objects.isNull(_category)){
                isPresent = false;
                break;
            }
        }
        if(!isPresent){
            log.error("Some of the categories do not exist in db....");
            throw new CategoryNotFoundException("Some of the categories do not exist...");
        }
        for(String categoryName : categoryNameList) {
            Category _category = (Category) categoryRepository.findByName(categoryName).get();
            log.info("Deleted id by name: {}", categoryName);
            categoryRepository.deleteById(_category.getId());
        }
        return new ResponseEntity<>("All categories deleted successfully.", HttpStatus.OK);
    }

    @Override
    @Transactional   // if anything goes wrong then revert back to initial stage
    public ResponseEntity<?> updateCategory(String oldCategoryName, Category category) throws CategoryNotFoundException {
        Category _category = (Category) categoryRepository.findByName(oldCategoryName).get();
        if (Objects.isNull(_category)) {
            log.error("category " + category.getName() + " does not exist...");
            throw new CategoryNotFoundException("Category by given name doesn't exist in db.");
        }
        log.info("Updating category by name: {}", category.getName());
        _category.setName(category.getName());
        _category.setDescription(category.getDescription());
        Category updatedCategory = categoryRepository.save(_category);
        Map<String, Object> response = new HashMap<>();
        response.put("updatedCategory", updatedCategory);
        response.put("Success message", "Category updated successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
