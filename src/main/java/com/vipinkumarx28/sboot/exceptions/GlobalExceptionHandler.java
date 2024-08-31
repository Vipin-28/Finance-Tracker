package com.vipinkumarx28.sboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vipinkumarx28.sboot.models.GeneralResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<>(new GeneralResponse("An error occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CategoryExistsException.class)
    public ResponseEntity<?> handleException(CategoryExistsException e){
        return new  ResponseEntity<>(new GeneralResponse("An error occurred: " + e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleException(CategoryNotFoundException e){
        return new ResponseEntity<>(new GeneralResponse("An error occurred: " + e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ExpenseExistsException.class)
    public ResponseEntity<?> handleException(ExpenseExistsException e){
        return new  ResponseEntity<>(new GeneralResponse("An error occurred: " + e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<?> handleException(ExpenseNotFoundException e){
        return new ResponseEntity<>(new GeneralResponse("An error occurred: " + e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNameRequiredException.class)
    public ResponseEntity<?> handleException(UserNameRequiredException e){
        return new ResponseEntity<>(new GeneralResponse("An error occurred: " + e.getMessage()), HttpStatus.EXPECTATION_FAILED);
    }
    
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<?> handleDuplicateUserException(DuplicateUserException e) {
    	 return new ResponseEntity<>(new GeneralResponse("An error occurred: " + e.getMessage()), HttpStatus.EXPECTATION_FAILED);
    }

}
