package com.vipinkumarx28.sboot.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.entities.Expense;
import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.exceptions.ExpenseExistsException;
import com.vipinkumarx28.sboot.exceptions.ExpenseNotFoundException;
import com.vipinkumarx28.sboot.exceptions.UserDoesNotExists;
import com.vipinkumarx28.sboot.models.GeneralResponse;
import com.vipinkumarx28.sboot.repository.CategoryRepository;
import com.vipinkumarx28.sboot.repository.ExpenseRepository;
import com.vipinkumarx28.sboot.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository; //for checking the category exists or not

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getExpense(String userName, String categoryName, String expenseName) throws Exception {
        User user = userRepository.findByUserName(userName).get(); // always provided
        if (Objects.isNull(user)) {
            throw new UserDoesNotExists("User with given userName doesn't exist");
        }

        Category category = null;
        if (Objects.nonNull(categoryName)) {
            category = categoryRepository.findByUserAndCategoryName(user, categoryName).get();
        }
        List<Expense> expenseList = null;
        if (Objects.isNull(categoryName) && Objects.isNull(expenseName)) {// only user
            expenseList = expenseRepository.findByUserId(user.getUserId()).get();
        } else if (Objects.isNull(expenseName)) {// user + category
            expenseList = expenseRepository.findAllByUserAndCategory(user, category).get();
        } else {// user + expenseName
            expenseList = expenseRepository.findAllByUserAndExpenseName(user, expenseName).get();
        }

        return new ResponseEntity<>(new GeneralResponse(expenseList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId).get();
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addNewExpense(String userName, String categoryName, Expense expense) throws
            ExpenseExistsException {
        try {
            User user = userRepository.findByUserName(userName).get();
            if (Objects.isNull(user)) {
                throw new UserDoesNotExists("Category provided with expense doesn't exist.");
            }

            Category category = (Category) categoryRepository.findByCategoryName(categoryName).get();
            if (Objects.isNull(category)) {
                throw new CategoryNotFoundException("Category provided with expense doesn't exist.");
            }
            expense.setUser(user);
            expense.setCategory(category);
            expense.setCreationDate(LocalDate.now());
            expenseRepository.save(expense);
        } catch (CategoryNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
        	System.out.println("An error occurred while processing the request: "+ e);
            throw new RuntimeException("An error occurred while processing the request", e);
        }
        return new ResponseEntity<>("Expense saved successfully...", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteExpenseById(Long expenseId) throws ExpenseNotFoundException {
        Optional<Expense> _expense = expenseRepository.findById(expenseId);
        if (!_expense.isPresent()) {
        	System.out.println("Expense with give id: {} doesn't exist in db."+ expenseId);
            throw new ExpenseNotFoundException("Expense with give id not found in db.");
        }
        System.out.println("Deleting expense with id: {}"+ expenseId);
        expenseRepository.deleteById(expenseId);
        return new ResponseEntity<>("Expense deleted successfully.", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateExpenseById(Long oldExpenseId, Expense expense) throws ExpenseNotFoundException {
        Optional<Expense> _expense = expenseRepository.findById(oldExpenseId);
        if (!_expense.isPresent()) {
        	System.out.println("Expense with give id: " + oldExpenseId +"doesn't exist in db.");
            throw new ExpenseNotFoundException("Expense with give id not found in db.");
        }
        System.out.println("Updating expense with id: {}"+ oldExpenseId);
        _expense.get().setExpenseName(expense.getExpenseName());
        _expense.get().setAmount(expense.getAmount());
//        _expense.get().setCategory(expense.getCategory());
        _expense.get().setComments(expense.getComments());
        _expense.get().setCreationDate(expense.getCreationDate());
        try {
            Expense updatedExpense = expenseRepository.save(_expense.get());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Invalid date format, use YYYY-MM-DD format", e);
        }
        return new ResponseEntity<>("Expense updated successfully.", HttpStatus.OK);
    }
}
