package com.vipinkumarx28.sboot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {


    @GetMapping("/about")
    public String about(){
        return "Hello, welcome to personal expense tracker version 1.0";
    }

    
}
