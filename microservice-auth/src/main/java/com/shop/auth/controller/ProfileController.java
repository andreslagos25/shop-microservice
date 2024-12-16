package com.shop.auth.controller;

import com.shop.auth.persistance.entity.UserEntity;
import com.shop.auth.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @PatchMapping("disable/{idUser}")
    public ResponseEntity disableUser(@PathVariable String idUser){
        UserEntity user = userRepository.findById(idUser).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setAccountNoLocked(false);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
