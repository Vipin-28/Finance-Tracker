package com.vipinkumarx28.sboot.controllers;


import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.entities.VerificationToken;
import com.vipinkumarx28.sboot.event.RegistrationCompleteEvent;
import com.vipinkumarx28.sboot.models.GeneralResponse;
import com.vipinkumarx28.sboot.models.PasswordModel;
import com.vipinkumarx28.sboot.models.UserModel;
import com.vipinkumarx28.sboot.repository.UserRepository;
import com.vipinkumarx28.sboot.services.CategoryService;
import com.vipinkumarx28.sboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class UserRegisterationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) throws Exception {
        User user = userService.registerUser(userModel); // initially the user is enabled in db
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "User saved successfully, please check your email for enable yourself.");
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    } 

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel userModel){
        return userService.loginUser(userModel); 
    } 
    
    
    @GetMapping("/verificationToken")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validatieVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User verified successfully";
        }
        return "Bad User";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token")String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification link sent to your mail.";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request){
        User user = userRepository.findUserByEmailId(passwordModel.getEmail()).get();
        String url = "";
        if(Objects.nonNull(user)){
            String token = UUID.randomUUID().toString();
            url = passwordResetTokenMail(user, applicationUrl(request), token);
            
        }
        return url;
    }

    @PostMapping("/savePassword")
    public ResponseEntity<?> savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")){
        	return new ResponseEntity<>(new GeneralResponse("Invalid Token"), HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return new ResponseEntity<>(new GeneralResponse("Password reset successfully."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new GeneralResponse("Invalid Token"), HttpStatus.BAD_REQUEST);
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl
                        + "/savePassword?token="
                        + token;

        System.out.println("Please click the link to reset your password "+ url);
        return url;
    }


    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken token) {
          String url = applicationUrl + "/verificationToken" + token;
          System.out.println("Please click the link to verify your account: "+ url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                "/"+
                request.getContextPath();
    }
}
