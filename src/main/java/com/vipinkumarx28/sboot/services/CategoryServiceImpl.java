package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.config.PopulateInitialCategoriesComponent;
import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.exceptions.UserDoesNotExists;
import com.vipinkumarx28.sboot.exceptions.UserNameRequiredException;
import com.vipinkumarx28.sboot.repository.CategoryRepository;
import com.vipinkumarx28.sboot.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PopulateInitialCategoriesComponent populateInitialCategoriesComponent;

    @Override
    public ResponseEntity<?> addCategory(Long userId, Category category) throws CategoryExistsException, UserNameRequiredException {
        if(Objects.isNull(userId)){
            throw new UserNameRequiredException("Please provide userId.");
        }
        //User user = userRepository.findByUserName(userName).get();
        User user = userRepository.findById(userId).get();

        if (categoryRepository.findByUserAndCategoryName(user, category.getCategoryName()).isPresent()) {
        	System.out.println("category " + category.getCategoryName() + " already exists...");
            throw new CategoryExistsException("Category already exists in the db.");
        }
        category.setUser(user);

        System.out.println("Saving category by name: "+ category.getCategoryName());
        categoryRepository.save(category);
        return new ResponseEntity<>("Category saved successfully.", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getCategory(Long userId, Long categoryId, String filterValue, String filterKey) throws CategoryNotFoundException, UserNameRequiredException {
        List<Category> categoryList = new ArrayList<>();
        try {
            if(Objects.isNull(userId)){
                throw new UserNameRequiredException("Please provide userId.");
            }

            //Long userId = userRepository.findByUserName(userId).get().getUserId();

            if (Objects.isNull(categoryId)) {// both get all
                categoryList = categoryRepository.findByUserUserId(userId); // return list of all the categories under one user.
                //categoryList = categoryRepository.findAll();
            } else {
                Category category = categoryRepository.findById(categoryId).get();
                categoryList.add(category);
            }
        }catch(NoSuchElementException e){
//            log.error("");
            throw new CategoryNotFoundException("Category not found exception.");
        } catch (UserNameRequiredException ee) {
            throw new UserNameRequiredException(ee.getMessage());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("list", categoryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteCategoryById(Long categoryId) throws CategoryNotFoundException {
        if (!categoryRepository.findById(categoryId).isPresent()) {//not present
        	System.out.println("category id: {} does not exist..."+ categoryId);
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
    public ResponseEntity<?> deleteCategoryByName(Long userId, List<String> categoryNameList) throws CategoryNotFoundException, UserNameRequiredException {
        if(Objects.isNull(userId)){
            throw new UserNameRequiredException("Please provide username.");
        }
        User user = userRepository.findById(userId).get();

        Boolean isPresent = true;
        for(String categoryName : categoryNameList) {
            Category _category = (Category) categoryRepository.findByUserAndCategoryName(user, categoryName).get();
            if(Objects.isNull(_category)){
                isPresent = false;
                break;
            }
        }
        if(!isPresent){
        	System.out.println("Some of the categories do not exist in db....");
            throw new CategoryNotFoundException("Some of the categories do not exist...");
        }
        for(String categoryName : categoryNameList) {
            Category _category = (Category) categoryRepository.findByUserAndCategoryName(user, categoryName).get();
            _category.setUser(null);
            System.out.println("Deleted id by name: {}"+ categoryName);
            categoryRepository.deleteById(_category.getCategoryId());
        }
        return new ResponseEntity<>("All categories deleted successfully.", HttpStatus.OK);
    }

    @Override
    @Transactional   // if anything goes wrong then revert back to initial stage
    public ResponseEntity<?> updateCategory(Long userId, String oldCategoryName, Category category) throws CategoryNotFoundException, UserNameRequiredException, UserDoesNotExists {

        if(Objects.isNull(userId)){
            throw new UserNameRequiredException("Please provide username.");
        }
        User user = userRepository.findById(userId).get();
        if(Objects.isNull(user)){
            throw new UserDoesNotExists("User with given username doesn't exist in the db.");
        }


        //Category _category = (Category) categoryRepository.findByCategoryName(oldCategoryName).get();
        /////////TEST THIS/////////////
        Category _category = categoryRepository.findCategoryByCategoryNameAndUserName(oldCategoryName, userId).get();
        if (Objects.isNull(_category)) {
        	System.out.println("category " + category.getCategoryName() + " does not exist...");
            throw new CategoryNotFoundException("Category by given name doesn't exist in db.");
        }
        System.out.println("Updating category by name: "+ category.getCategoryName());
        _category.setCategoryName(category.getCategoryName());
        _category.setDescription(category.getDescription());
        Category updatedCategory = categoryRepository.save(_category);
        Map<String, Object> response = new HashMap<>();
        response.put("updatedCategory", updatedCategory);
        response.put("Success message", "Category updated successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    public void initialPopulateCategories(User user) {
        populateInitialCategoriesComponent.populateCategoriesForUser(user);
    }


}
