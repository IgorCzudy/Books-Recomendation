package com.example.demo.service;


import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String regex) {
        return bookRepository.findRegex(regex);
    }

    public List<Book> findBooksByIds(List<Long> recommendedBooks) {
        return bookRepository.findAllById(recommendedBooks);
    }
}
