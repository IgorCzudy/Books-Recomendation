package com.example.demo.controllers;


import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.service.BookRaitingService;
import com.example.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookRaitingService bookRaitingService;

    public BookController(BookService bookService, BookRaitingService bookRaitingService) {
        this.bookService = bookService;
        this.bookRaitingService = bookRaitingService;
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @PostMapping("/{bookId}/rate")
    @PreAuthorize("isAuthenticated()") // Ensures only logged-in users can access this
    public ResponseEntity<String> rateBook(
            @PathVariable Long bookId,
            @RequestParam int rating,
            Principal principal
            ){
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You need to be logged in to rate books.");
        }

        System.out.println("User: " + principal.getName()); // Prints logged-in user
        System.out.println("Book ID: " + bookId);
        System.out.println("Rating: " + rating);

        bookRaitingService.CreateBookRaiting(rating, principal.getName(), bookId);
        System.out.println("Added to DB");

        return ResponseEntity.ok("Rating received");

    }

}
