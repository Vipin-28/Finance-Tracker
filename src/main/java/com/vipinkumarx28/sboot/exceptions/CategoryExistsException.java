package com.vipinkumarx28.sboot.exceptions;

public class CategoryExistsException extends Exception {
    public CategoryExistsException(String message) {
        super(message);
    }

    public CategoryExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
