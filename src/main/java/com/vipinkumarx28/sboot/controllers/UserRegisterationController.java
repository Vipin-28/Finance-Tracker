package com.vipinkumarx28.sboot.controllers;


import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.models.UserModel;
import com.vipinkumarx28.sboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegisterationController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel){
        return userService.registerUser(userModel);
    }
}
