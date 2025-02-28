package com.example.demo.controllers;

import com.example.demo.model.Book;
import com.example.demo.model.BookRaiting;
import com.example.demo.service.BookRaitingService;
import com.example.demo.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@RestController
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @Autowired
    BookRaitingService bookRaitingService;


    @GetMapping("/api/recommendation")
    @PreAuthorize("isAuthenticated()")
    public List<Book> getRecomendation(Principal principal) throws IOException {
        String username = principal.getName();
        List<BookRaiting> ratedBooks = bookRaitingService.getRatingsOfCertainUserId(username);

        LinkedList<Long> highRatedBookIds = new LinkedList<>();
        for (BookRaiting ratedBook :ratedBooks){
            if (ratedBook.getRate() > 3){
                highRatedBookIds.add(ratedBook.getBookRaitingComposite().getBookId());
            }
        }
        return recommendationService.getRecomendation(highRatedBookIds);
    }

}
