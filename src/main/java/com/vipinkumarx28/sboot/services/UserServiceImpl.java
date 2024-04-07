package com.vipinkumarx28.sboot.services;


import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.models.UserModel;
import com.vipinkumarx28.sboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> registerUser(UserModel userModel) {
        User user = new User();
        user.setUserName(userModel.getUserName());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setEmailId(user.getEmailId());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully.", HttpStatus.CREATED);
    }
}
