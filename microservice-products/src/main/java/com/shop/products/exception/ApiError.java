package com.shop.products.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime timestamp;

    public ApiError(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
