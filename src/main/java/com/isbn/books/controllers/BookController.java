package com.isbn.books.controllers;

import com.isbn.books.dto.BookDto;
import com.isbn.books.entities.BookEntity;
import com.isbn.books.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @PostMapping("search-books")
    public ResponseEntity<List<BookEntity>> getBooksBySearchedCriteria(@RequestBody BookDto book) {

        return ResponseEntity.ok(bookService.getBooksBySearchedCriteria(book));
    }

    @GetMapping("all")
    public ResponseEntity<List<BookEntity>> getAllBooks() {

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook() {

        return ResponseEntity.ok(new BookDto());
    }

    @PutMapping
    public ResponseEntity<BookDto> editBook() {

        return ResponseEntity.ok(new BookDto());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook() {

        return ResponseEntity.noContent().build();
    }
}
