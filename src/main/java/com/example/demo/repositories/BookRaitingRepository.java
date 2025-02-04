package com.example.demo.repositories;


import com.example.demo.model.BookRaiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRaitingRepository extends JpaRepository<BookRaiting, Long> {

    public List<BookRaiting> findByUser(String user);

}
