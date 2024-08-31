package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.entities.Expense;
import com.vipinkumarx28.sboot.exceptions.ExpenseExistsException;
import com.vipinkumarx28.sboot.exceptions.ExpenseNotFoundException;
import com.vipinkumarx28.sboot.exceptions.UserDoesNotExists;
import org.springframework.http.ResponseEntity;

public interface ExpenseService {
    public ResponseEntity<?> getExpense(String userName, String categoryName, String name) throws Exception;

    public ResponseEntity<?> getExpenseById(Long expenseId);

    public ResponseEntity<?> addNewExpense(String userName, String categoryName, Expense expense) throws ExpenseExistsException;

    public ResponseEntity<?> deleteExpenseById(Long expenseId) throws ExpenseNotFoundException;

    public ResponseEntity<?> updateExpenseById(Long expenseId, Expense expense) throws ExpenseNotFoundException;
}
