package com.vipinkumarx28.sboot.exceptions;

public class UserNameRequiredException extends Exception {
    public UserNameRequiredException(String message) {
        super(message);
    }

    public UserNameRequiredException(String message, Throwable cause) {
        super(message, cause);
    }
}