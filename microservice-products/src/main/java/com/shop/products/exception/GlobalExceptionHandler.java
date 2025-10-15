package com.shop.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidation(ValidationException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    // Method to handle exceptions caused by jakarta validations EX: @NotBlank
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleJakartaValidation(MethodArgumentNotValidException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "There's a problem with one or more fields in your request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> handelResourceAlreadyExist(ResourceAlreadyExistsException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
