package com.vipinkumarx28.sboot.services;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vipinkumarx28.sboot.entities.PasswordResetToken;
import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.entities.VerificationToken;
import com.vipinkumarx28.sboot.exceptions.DuplicateUserException;
import com.vipinkumarx28.sboot.models.GeneralResponse;
import com.vipinkumarx28.sboot.models.PasswordModel;
import com.vipinkumarx28.sboot.models.UserModel;
import com.vipinkumarx28.sboot.repository.PasswordResetTokenRepository;
import com.vipinkumarx28.sboot.repository.UserRepository;
import com.vipinkumarx28.sboot.repository.VerificationTokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Override
    public User registerUser(UserModel userModel) throws Exception {
        User user = new User();
        checkDulpicates(userModel);
        user.setUserName(userModel.getUserName());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPhoneNumber(userModel.getPhoneNumber());
        user.setEmailId(userModel.getEmailId());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        try {
            userRepository.save(user);
        }catch(Exception e){
        	System.out.println("------------ {} ------------"+ user);
        	System.out.println("An exception occurred while saving data in the db: "+ e);
            throw new Exception("Error occurred while saving user.");
        }
        return user;
    }
    
    @Override
    public ResponseEntity<?> loginUser(UserModel userModel) {
        // Check if email ID is missing
        if (Objects.isNull(userModel.getEmailId())) {
            return new ResponseEntity<>(new GeneralResponse("Email Id is missing in the payload."), HttpStatus.BAD_REQUEST);
        }
        
        // Check if password is missing
        if (Objects.isNull(userModel.getPassword())) {
            return new ResponseEntity<>(new GeneralResponse("Password is missing in the payload."), HttpStatus.BAD_REQUEST);
        }
        
        // Find user by email
        Optional<User> optionalUser = userRepository.findUserByEmailId(userModel.getEmailId());
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(new GeneralResponse("User not found."), HttpStatus.BAD_REQUEST);
        }

        User userSaved = optionalUser.get();

        // Check if the password is correct
        if (!passwordEncoder.matches(userModel.getPassword(), userSaved.getPassword())) {
            return new ResponseEntity<>(new GeneralResponse("Incorrect Password."), HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "User logged in successfully.");
        response.put("user", optionalUser.get());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    private void checkDulpicates(UserModel userModel) {
    	Optional<User> existingUserByEmail = userRepository.findByEmailId(userModel.getEmailId());
        if (existingUserByEmail.isPresent()) {
            throw new DuplicateUserException("Email is already in use.");
        }

        Optional<User> existingUserByUserName = userRepository.findByUserName(userModel.getUserName());
        if (existingUserByUserName.isPresent()) {
            throw new DuplicateUserException("Username is already in use.");
        }

        Optional<User> existingUserByPhone = userRepository.findByPhoneNumber(userModel.getPhoneNumber());
        if (existingUserByPhone.isPresent()) {
            throw new DuplicateUserException("Phone number is already in use.");
        }

	}
    
    private String applicationUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                "/"+
                request.getContextPath();
    }

	@Override
    public ResponseEntity<?> saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
        return new ResponseEntity<>(new GeneralResponse("SUCCESS"), HttpStatus.CREATED);
    }

    @Override
    public String validatieVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null)return "invalid";

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() < 0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        categoryService.initialPopulateCategories(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User resetPassword(PasswordModel passwordModel) {
        User user = userRepository.findUserByEmailId(passwordModel.getEmail()).get();
        if(Objects.nonNull(user)){
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, token);
            return user;
        }
        return null;
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null)return "invalid";

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime() < 0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }


}
