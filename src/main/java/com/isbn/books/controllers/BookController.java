package com.isbn.books.controllers;

import com.isbn.books.dto.BookDto;
import com.isbn.books.group.validator.UpdateBook;
import com.isbn.books.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("search/{author}/{title}/{isbn}")
    public ResponseEntity<List<BookDto>> getBooksBySearchedCriteria(
            @PathVariable(value = "author", required = false) String author,
            @PathVariable(value = "title", required = false) String title,
            @PathVariable(value = "isbn", required = false) String isbn) {

        return ResponseEntity.ok(bookService.getBooksBySearchedCriteria(author, title, isbn));
    }

    @GetMapping("all")
    public ResponseEntity<List<BookDto>> getAllBooks() {

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@Valid @RequestBody BookDto book) {

        bookService.insertNewBook(book);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> editBook(@Validated(UpdateBook.class) @RequestBody BookDto book) throws Exception {

        bookService.updateBookData(book);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{bookid}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookid") Long bookId) {

        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }
}
