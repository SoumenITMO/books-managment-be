package com.isbn.books.services;

import com.isbn.books.dto.BookDto;
import com.isbn.books.dto.BookHistory;
import com.isbn.books.entities.BookEntity;
import com.isbn.books.entities.FileHistory;
import com.isbn.books.helper.IsbnValidator;
import com.isbn.books.mappers.BookEntityMapper;
import com.isbn.books.repositories.BookRepository;
import com.isbn.books.repositories.FileHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final IsbnValidator isbnValidator;
    private final BookRepository bookRepository;
    private final BookEntityMapper bookEntityMapper;
    private final FileHistoryRepository fileHistoryRepository;

    /**
     *
     * @return all books
     */
    public List<BookHistory> getAllBooks() {

        List<BookHistory> bookHistories = new ArrayList<>();
        List<FileHistory> fileHistories = fileHistoryRepository.findAll().stream().distinct().collect(Collectors.toList());

        fileHistories.forEach(fileName -> {
            bookHistories.add(new BookHistory(fileName.getFileName(),
                    bookEntityMapper.toBookDto(bookRepository.findAllByFilename(fileName.getFileName()))));
        });
        return bookHistories;
    }

    /**
     *
     * @param author
     * @param title
     * @param isbn
     * @return list of books matched by searched criteria
     */
    public List<BookDto> getBooksBySearchedCriteria(String author, String title, String isbn) {

        return bookEntityMapper.toBookDto(bookRepository.findBooksByProvidedSearchCriteria(author, title, isbn));
    }

    /**
     *
     * @param book
     */
    public void insertNewBook(BookDto book) throws Exception {
        if(isbnValidator.validateISBN(book.getIsbn()).equals("")) {
            throw new Exception("Invalid ISBN");
        }

        bookRepository.save(new BookEntity(0L, book.getIsbn(), book.getAuthor(), book.getTitle()));
    }

    /**
     *
     * @param book
     * @return updated book data
     * @throws Exception when book not found
     */
    public BookEntity updateBookData(BookDto book) throws Exception {
        BookEntity getBookById = bookRepository.findById(book.getId()).orElseThrow(() -> new Exception("Can not update, Book not found"));

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
    public void deleteBookById(Long bookId) throws Exception {

        bookRepository.findById(bookId).orElseThrow(() -> new Exception("Can not delete, Book not found."));
        bookRepository.deleteById(bookId);
    }
}
