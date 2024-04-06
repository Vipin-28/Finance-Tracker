package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.entities.Expense;
import com.vipinkumarx28.sboot.exceptions.ExpenseExistsException;
import com.vipinkumarx28.sboot.exceptions.ExpenseNotFoundException;
import org.springframework.http.ResponseEntity;

public interface ExpenseService {
    public ResponseEntity<?> getExpenseByIdOrName(Long expenseId, String name);

    public ResponseEntity<?> addNewExpense(Expense expense) throws ExpenseExistsException;

    public ResponseEntity<?> deleteExpenseById(Long expenseId) throws ExpenseNotFoundException;

    public ResponseEntity<?> updateExpenseById(Long expenseId, Expense expense) throws ExpenseNotFoundException;
}
