package com.example.demo.controllers;


import com.example.demo.model.BookRaiting;
import com.example.demo.model.BookRaitingComposite;
import com.example.demo.model.User;
import com.example.demo.service.BookRaitingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
public class BookRatingController {

    @Autowired
    BookRaitingService bookRaitingService;
    @Autowired
    UserService userService;


    @GetMapping("books/{bookId}/rate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getBookrate(
            @PathVariable Long bookId,
            Principal principal
    ){

        String username = principal.getName();
        Long userId = userService.getUserByName(principal.getName()).getId();

        BookRaitingComposite composite = new BookRaitingComposite(userId, bookId);
        int rating = bookRaitingService.getRatingsOfCertainCompositeId(composite);
        return ResponseEntity.ok(String.valueOf(rating));
    }


    @PostMapping("books/{bookId}/rate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> rateBook(
            @PathVariable Long bookId,
            @RequestParam int rating,
            Principal principal
    ){

        String username = principal.getName();
        Long userId = userService.getUserByName(principal.getName()).getId();

        BookRaitingComposite composite = new BookRaitingComposite(userId, bookId);
        BookRaiting rate = new BookRaiting(composite, username, rating);
        bookRaitingService.createBookRaiting(rate);

        return ResponseEntity.ok("Book rated successfully!");
    }
}
