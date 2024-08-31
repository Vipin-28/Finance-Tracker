package com.vipinkumarx28.sboot.services;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.vipinkumarx28.sboot.entities.User;
import com.vipinkumarx28.sboot.entities.VerificationToken;
import com.vipinkumarx28.sboot.models.PasswordModel;
import com.vipinkumarx28.sboot.models.UserModel;

public interface UserService {
    public User registerUser(UserModel userModel) throws Exception;

    public ResponseEntity<?> saveVerificationTokenForUser(String token, User user);

    public String validatieVerificationToken(String token);

    public VerificationToken generateNewVerificationToken(String oldToken);

    public User resetPassword(PasswordModel passwordModel);

    public String validatePasswordResetToken(String token);

    public Optional<User> getUserByPasswordResetToken(String token);

    public void changePassword(User user, String newPassword);

	public ResponseEntity<?> loginUser(UserModel userModel);
}
