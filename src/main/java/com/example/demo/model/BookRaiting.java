package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRaiting {
    @EmbeddedId
    BookRaitingComposite bookRaitingComposite;
    @Column(name = "\"user\"")
    private String user;
    private int rate;
}
