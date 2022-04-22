package com.isbn.books.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    public BookEntity(String isbn, String author, String title) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }
}
