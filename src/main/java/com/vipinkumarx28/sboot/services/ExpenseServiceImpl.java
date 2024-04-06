package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.entities.Expense;
import com.vipinkumarx28.sboot.exceptions.ExpenseExistsException;
import com.vipinkumarx28.sboot.exceptions.ExpenseNotFoundException;
import com.vipinkumarx28.sboot.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

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
    public ResponseEntity<?> addNewExpense(Expense expense) throws ExpenseExistsException {
        Optional<Expense> _expense = expenseRepository.findExpenseByName(expense.getName());
        if (_expense.isPresent()) {
            log.error("Expense with give details already exists in db.");
            throw new ExpenseExistsException("Expense already exists");
        }
        try {
            expenseRepository.save(expense);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Invalid date format, use YYYY-MM-DD format", e);
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
        _expense.get().setCategoryId(expense.getCategoryId());
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
