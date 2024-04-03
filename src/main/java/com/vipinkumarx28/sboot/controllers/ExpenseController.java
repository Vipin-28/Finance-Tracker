package com.vipinkumarx28.sboot.controllers;

import com.vipinkumarx28.sboot.entities.Expense;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {


    @GetMapping("/about")
    public String about(){
        return "Hello, welcome to personal expense tracker version 1.0";
    }

    @GetMapping
    public String getExpenseByIdOrName(@RequestParam(required = false) Long expenseId, @RequestParam(required = false) String name){
        return null;
    }

    @PostMapping
    public String addExpense(@RequestBody Expense expense){
        return null;
    }

    @DeleteMapping(path = "/{expenseId}")
    public String deleteExpenseById(@PathVariable Long id){
        return null;
    }

    @PutMapping(path = "/{expenseId}")
    public ResponseEntity<?> updateExpenseById(@PathVariable Long expenseId, @RequestBody Expense expense){
        return null;
    }
}
