package com.shop.auth.controller;

import com.shop.auth.controller.dto.ConfirmDTO;
import com.shop.auth.persistance.entity.UserEntity;
import com.shop.auth.persistance.repository.UserRepository;
import com.shop.auth.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserDetailServiceImpl userService;

    @PostMapping("/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableMyAccount(@RequestBody ConfirmDTO confirmDTO){
        userService.disableByUsernameWithValidation(confirmDTO.getPassword());
    }
}
