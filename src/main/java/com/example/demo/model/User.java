package com.example.demo.model;

import jakarta.persistence.*;

@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String passwor;

    public User(Long id, String email, String passwor) {
        this.id = id;
        this.email = email;
        this.passwor = passwor;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", passwor='" + passwor + '\'' +
                '}';
    }
}
