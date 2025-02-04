package com.example.demo.service;

import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.servlet.function.ServerResponse.status;

@Service
//@AllArgsConstructor
public class UserService { //implements UserDetailsService

}
//
//    @Autowired
//    private UserRepository userRepository;
//

//    @Transactional
//    public User registerUser(User user){
//        Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
//        if (existingUser.isPresent()){
//            throw new UserAlreadyExistsException("User with this email already exists.");
//        }
//        return userRepository.save(user);
//    }
//
//    @Transactional
//    public User loginUser(String email, String password) {
//        User user = userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//
//        if (! password.equals(user.getPassword())) {
//            throw new InvalidCredentialsException("Invalid password");
//        }
//        return user;
//    }

//    @Override
//    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
//        Optional<User> existingUser = userRepository.findUserByEmail(mail);
//        if (existingUser.isPresent()){
//            throw new UsernameNotFoundException(mail);
//        }
//        var userObj = existingUser.get();
//        return User.builder()
//                .username(userObj.getUsername())
//                .password(userObj.getPassword())
//                .build();
//
//    }
//}
