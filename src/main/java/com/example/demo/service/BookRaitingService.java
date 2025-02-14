package com.example.demo.service;


import com.example.demo.model.BookRaiting;
import com.example.demo.model.BookRaitingComposite;
import com.example.demo.repositories.BookRaitingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookRaitingService {

    @Autowired
    private BookRaitingRepository bookRaitingRepository;

    public BookRaiting createBookRaiting(BookRaiting bookRaiting){
        bookRaitingRepository.save(bookRaiting);
        return bookRaiting;
    }


    public int getRatingsOfCertainCompositeId(BookRaitingComposite composite){
        return bookRaitingRepository.findById(composite)
                .map(BookRaiting::getRate)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Rating not found for the provided composite key"
                ));
    }

    public List<BookRaiting> getRatingsOfCertainUserId(String user){
        return bookRaitingRepository.findByUser(user);
    }
}
