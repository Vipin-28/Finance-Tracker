package com.vipinkumarx28.sboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private String userName;
    private String emailId;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
}
