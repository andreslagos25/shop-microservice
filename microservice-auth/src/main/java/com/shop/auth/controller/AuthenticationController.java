package com.shop.auth.controller;

import com.shop.auth.controller.dto.AuthCreateUserRequest;
import com.shop.auth.controller.dto.AuthLoginRequest;
import com.shop.auth.controller.dto.AuthResponse;
import com.shop.auth.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

    @GetMapping("/search-client/{idUser}")
    public ResponseEntity<?> findClientByIdUser(@PathVariable String idUser){
        return ResponseEntity.ok(userDetailService.findClientByIdUser(idUser));
    }
}
