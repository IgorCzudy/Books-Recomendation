package com.example.demo.repositories;


import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM books b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :regex, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :regex, '%'))",
        nativeQuery = true)
    List<Book> findRegex(String regex);
}
