package com.example.demo.controllers;

import com.example.demo.controllers.Reques.LoginRequest;
import com.example.demo.controllers.Reques.RegistrationRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return userService.verify(loginRequest);
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        return userService.register(registrationRequest);
    }
}
