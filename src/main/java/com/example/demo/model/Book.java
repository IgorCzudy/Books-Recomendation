package com.example.demo.model;


import jakarta.persistence.*;

@Entity(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false, unique = true)
    private String bookName;
    @Column(nullable = false, unique = true)
    private String imgURL;

    @Override
    public String toString() {
        return "Book{" +
                "Id=" + Id +
                ", author='" + author + '\'' +
                ", bookName='" + bookName + '\'' +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }

    public Book(String author, String bookName, String imgURL) {
        this.author = author;
        this.bookName = bookName;
        this.imgURL = imgURL;
    }

    public Book(){};

    public void setId(Long id) {
        Id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Long getId() {
        return Id;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    public String getImgURL() {
        return imgURL;
    }
}
