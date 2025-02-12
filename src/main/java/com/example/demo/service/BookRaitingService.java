package com.example.demo.service;


import com.example.demo.model.BookRaiting;
import com.example.demo.repositories.BookRaitingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookRaitingService {

    @Autowired
    private BookRaitingRepository bookRaitingRepository;

    public BookRaiting CreateBookRaiting(int rate, String user, Long bookId){
        BookRaiting bookRaiting = new BookRaiting(rate, user, bookId);
        bookRaitingRepository.save(bookRaiting);
        return bookRaiting;
    }

    public List<BookRaiting> getRatingsOfCertainUserId(String user){
        return bookRaitingRepository.findByUser(user);
    }
}
