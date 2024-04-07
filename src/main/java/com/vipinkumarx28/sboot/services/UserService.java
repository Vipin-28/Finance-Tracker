package com.vipinkumarx28.sboot.services;

import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.models.UserModel;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> registerUser(UserModel userModel);
}
