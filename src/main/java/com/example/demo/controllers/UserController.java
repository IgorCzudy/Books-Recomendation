package com.example.demo.controllers;


import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;
    private final long expirationTime = 86_400_000;

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        User dbUser = userRepository.findUserByEmail(loginRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if (dbUser.getPassword().equals(loginRequest.getPassword())) {
            String token = Jwts.builder()
                    .setSubject(loginRequest.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                    .compact();
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.findUserByEmail(registrationRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Email is already in use!");
        }

        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatedPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        userRepository.save(new User(registrationRequest.getUsername(), registrationRequest.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }
}



