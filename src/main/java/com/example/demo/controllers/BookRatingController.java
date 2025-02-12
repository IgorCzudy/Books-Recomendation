package com.example.demo.controllers;


import com.example.demo.service.BookRaitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class BookRatingController {

    @Autowired
    BookRaitingService bookRaitingService;

    @PostMapping("books/{bookId}/rate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> rateBook(
            @PathVariable Long bookId,
            @RequestParam int rating,
            Principal principal
    ){

        System.out.println("User: " + principal.getName()); // Prints logged-in user
        System.out.println("Book ID: " + bookId);
        System.out.println("Rating: " + rating);

        bookRaitingService.CreateBookRaiting(rating, principal.getName(), bookId);
        System.out.println("Added to DB");
        return ResponseEntity.ok("Rating received");

    }
}
