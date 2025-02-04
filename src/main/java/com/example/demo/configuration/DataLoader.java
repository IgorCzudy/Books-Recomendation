package com.example.demo.configuration;

import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(BookRepository bookRepository) {
        return args -> {
            if (bookRepository.count() == 0) { // Only insert if empty
                bookRepository.save(new Book("Adam Mickiewicz", "Pan Tadeusz", "/images/panTadeusz.jpg"));
                bookRepository.save(new Book("Tolken", "Hobbit", "/images/hobbit.jpg"));
                bookRepository.save(new Book("George Orwell", "1984", "/images/1984.jpeg"));
            }
        };
    }
}
