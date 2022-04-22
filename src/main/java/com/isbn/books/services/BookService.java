package com.isbn.books.services;

import com.isbn.books.dto.BookDto;
import com.isbn.books.entities.BookEntity;
import com.isbn.books.mappers.BookEntityMapper;
import com.isbn.books.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookEntityMapper bookEntityMapper;

    /**
     *
     * @return all books
     */
    public List<BookDto> getAllBooks() {
        return bookEntityMapper.toBookDto(bookRepository.findAll());
    }

    /**
     *
     * @param author
     * @param title
     * @param isbn
     * @return list of books matched by searched criteria
     */
    public List<BookDto> getBooksBySearchedCriteria(String author, String title, String isbn) {
        return bookEntityMapper.toBookDto(bookRepository.findAllByAuthorOrTitleOrIsbn(author, title, isbn));
    }

    /**
     *
     * @param book
     */
    public void insertNewBook(BookDto book) {
        bookRepository.save(new BookEntity(0L, book.getIsbn(), book.getAuthor(), book.getTitle()));
    }

    /**
     *
     * @param book
     * @return updated book data
     * @throws Exception when book not found
     */
    public BookEntity updateBookData(BookDto book) throws Exception {
        BookEntity getBookById = bookRepository.findById(book.getId()).orElseThrow(() -> new Exception("Book not found"));

        getBookById.setId(book.getId());
        getBookById.setAuthor(book.getAuthor());
        getBookById.setTitle(book.getTitle());
        getBookById.setIsbn(book.getIsbn());

        bookRepository.save(getBookById);

        return getBookById;
    }

    /**
     *
     * @param bookId delete the book by id
     */
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
