package com.vipinkumarx28.sboot.controllers;

import com.vipinkumarx28.sboot.entities.Expense;
import com.vipinkumarx28.sboot.exceptions.ExpenseExistsException;
import com.vipinkumarx28.sboot.exceptions.ExpenseNotFoundException;
import com.vipinkumarx28.sboot.services.ExpenseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    @GetMapping("/about")
    public String about(){
        return "Hello, welcome to personal expense tracker version 1.0.0 and stay tuned for updates :)";
    }

    @GetMapping
    public ResponseEntity<?> getExpenseByIdOrName(@RequestParam(required = false) Long expenseId, @RequestParam(required = false) String name){
        return expenseService.getExpenseByIdOrName(expenseId, name);
    }

    @PostMapping
    public ResponseEntity<?> addExpense(@RequestBody Expense expense) throws ExpenseExistsException {
        return expenseService.addNewExpense(expense);
    }

    @DeleteMapping(path = "/{expenseId}")
    public ResponseEntity<?> deleteExpenseById(@PathVariable Long expenseId) throws ExpenseNotFoundException {
        return expenseService.deleteExpenseById(expenseId);
    }

    @PutMapping(path = "/{expenseId}")
    public ResponseEntity<?> updateExpenseById(@PathVariable Long expenseId, @RequestBody Expense expense) throws ExpenseNotFoundException {
        return expenseService.updateExpenseById(expenseId, expense);
    }
}
