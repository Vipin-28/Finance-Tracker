package com.vipinkumarx28.sboot.services;

import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import com.vipinkumarx28.sboot.entities.Category;
import com.vipinkumarx28.sboot.entities.Expense;
import com.vipinkumarx28.sboot.exceptions.CategoryExistsException;
import com.vipinkumarx28.sboot.exceptions.CategoryNotFoundException;
import com.vipinkumarx28.sboot.exceptions.ExpenseExistsException;
import com.vipinkumarx28.sboot.exceptions.ExpenseNotFoundException;
import com.vipinkumarx28.sboot.repository.CategoryRepository;
import com.vipinkumarx28.sboot.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository; //for checking the category exists or not

    @Override
    public ResponseEntity<?> getExpenseByIdOrName(Long expenseId, String name) {

        if (Objects.isNull(expenseId) && Objects.isNull(name)) {
            List<Expense> expenseList = expenseRepository.findAll();
            return new ResponseEntity<>(expenseList, HttpStatus.OK);
        } else if (Objects.nonNull(expenseId)) {
            Expense expense = expenseRepository.findById(expenseId).get();
            if (Objects.nonNull(expense))
                return new ResponseEntity<>(expense, HttpStatus.OK);
        } else {
            Expense expense = expenseRepository.findExpenseByName(name).get();
            if (Objects.nonNull(expense))
                return new ResponseEntity<>(expense, HttpStatus.OK);
        }
        return new ResponseEntity<>("Expense not found in db.", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> addNewExpense(String categoryName, Expense expense) throws ExpenseExistsException {
        log.info(String.valueOf(expense));
        Optional<Expense> _expense = expenseRepository.findExpenseByName(expense.getName());
        Expense savedExpense = null;
        if (_expense.isPresent()) {
            log.error("Expense with give details already exists in db.");
            throw new ExpenseExistsException("Expense already exists");
        }
        try {
            Category _category = (Category) categoryRepository.findByName(categoryName).get();
            if(Objects.isNull(_category)){
                throw new CategoryNotFoundException("Category provided with expense doesn't exist.");
            }
//            expense.setCategory(_category);
            expense.setCreationDate(LocalDate.now());
            System.out.println("-------------------");
            System.out.println("final expense is: "+ String.valueOf(expense));
            System.out.println("-------------------");
            savedExpense = expenseRepository.save(expense);
            _category.getExpenseIds().add(savedExpense.getExpenseId());
            categoryRepository.save(_category);
        } catch (CategoryNotFoundException e) {
            throw new RuntimeException(e);
        }catch (Exception e) {
            log.error("An error occurred while processing the request: ", e);
            log.error("hi : {}", savedExpense);
            throw new RuntimeException("An error occurred while processing the request", e);
        }
        return new ResponseEntity<>("Expense saved successfully...", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteExpenseById(Long expenseId) throws ExpenseNotFoundException {
        Optional<Expense> _expense = expenseRepository.findById(expenseId);
        if (!_expense.isPresent()) {
            log.error("Expense with give id: {} doesn't exist in db.", expenseId);
            throw new ExpenseNotFoundException("Expense with give id not found in db.");
        }
        log.info("Deleting expense with id: {}", expenseId);
        expenseRepository.deleteById(expenseId);
        return new ResponseEntity<>("Expense deleted successfully.", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateExpenseById(Long oldExpenseId, Expense expense) throws ExpenseNotFoundException {
        Optional<Expense> _expense = expenseRepository.findById(oldExpenseId);
        if (!_expense.isPresent()) {
            log.error("Expense with give id: {} doesn't exist in db.", oldExpenseId);
            throw new ExpenseNotFoundException("Expense with give id not found in db.");
        }
        log.info("Updating expense with id: {}", oldExpenseId);
        _expense.get().setName(expense.getName());
        _expense.get().setAmount(expense.getAmount());
//        _expense.get().setCategory(expense.getCategory());
        _expense.get().setComments(expense.getComments());
        _expense.get().setCreationDate(expense.getCreationDate());
        try {
            Expense updatedExpense = expenseRepository.save(_expense.get());
        }catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Invalid date format, use YYYY-MM-DD format", e);
        }
        return new ResponseEntity<>("Expense updated successfully.", HttpStatus.OK);
    }
}
