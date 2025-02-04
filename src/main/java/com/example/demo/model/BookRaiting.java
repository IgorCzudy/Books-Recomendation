package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class BookRaiting {

//    @EmbeddedId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
//    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "\"user\"")
    private String user;
    private int rate;
    private Long bookId;

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    @Override
    public String toString() {
        return "BookRaiting{" +
                "Id=" + Id +
                ", user='" + user + '\'' +
                ", rate=" + rate +
                '}';
    }

    public BookRaiting() {}
    public BookRaiting(int rate, String user, Long bookId) {
        this.rate = rate;
        this.user = user;
        this.bookId = bookId;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Long getId() {
        return Id;
    }

    public String getUser() {
        return user;
    }

    public int getRate() {
        return rate;
    }
}
