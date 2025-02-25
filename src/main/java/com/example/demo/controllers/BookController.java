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

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String regex){
        return bookService.searchBooks(regex);
    }

}
