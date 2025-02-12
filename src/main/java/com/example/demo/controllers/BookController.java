package com.example.demo.controllers;

import com.example.demo.model.Book;
import com.example.demo.service.BookRaitingService;
import com.example.demo.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
