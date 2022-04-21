package com.isbn.books.services;

import com.isbn.books.dto.BookDto;
import com.isbn.books.entities.BookEntity;
import com.isbn.books.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     *
     * @return all books
     */
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<BookEntity> getBooksBySearchedCriteria(BookDto book) {
        return bookRepository.findAllByAuthorOrTitleOrIsbn(book.getAuthor(), book.getTitle(), book.getIsbn());
    }
}
