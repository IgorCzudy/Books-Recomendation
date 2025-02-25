package com.example.demo.service;

import com.example.demo.controllers.Reques.LoginRequest;
import com.example.demo.controllers.Reques.RegistrationRequest;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${jwt.secret}")
    private String secretKey;
    private final long expirationTime = 86_400_000;


    public User getUserByName(String name) {
        return userRepository.findUserByUserName(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User " + name + " not found in a database"
                ));
    }

    public ResponseEntity<String> verify(LoginRequest loginRequest) {

        User dbUser = userRepository.findUserByUserName(loginRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found, you have a token from previous run"));

        if (passwordEncoder.matches(loginRequest.getPassword(), dbUser.getPassword())) {
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

    public ResponseEntity<String> register(RegistrationRequest registrationRequest) {

        if (userRepository.findUserByUserName(registrationRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Email is already in use!");
        }

        if (!registrationRequest.getPassword().equals(registrationRequest.getRepeatedPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        userRepository.save(new User(registrationRequest.getUsername(), hashedPassword));
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }
}
