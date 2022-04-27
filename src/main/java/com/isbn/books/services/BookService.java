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
        List<BookDto> getBooksWithoutFilename = bookEntityMapper.toBookDtos(bookRepository.findAllByFilenameNull());
        List<FileHistory> fileHistories = fileHistoryRepository.findAll().stream().distinct().collect(Collectors.toList());

        fileHistories.forEach(fileName -> {
            bookHistories.add(new BookHistory(fileName.getFileName(),
                    bookEntityMapper.toBookDtos(bookRepository.findAllByFilename(fileName.getFileName()))));
        });

        if(getBooksWithoutFilename.size() > 0) {
            bookHistories.add(new BookHistory(null, bookEntityMapper.toBookDtos(bookRepository.findAllByFilenameNull())));
        }
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

        return bookEntityMapper.toBookDtos(bookRepository.findBooksByProvidedSearchCriteria(author, title, isbn));
    }

    /**
     *
     * @param book
     */
    public void insertNewBook(BookDto book) throws Exception {

        if(isbnValidator.validateISBN(book.getIsbn()).equals("")) {
            throw new Exception("Invalid ISBN");
        }

        bookRepository.save(new BookEntity(book.getIsbn(), book.getTitle(), book.getAuthor(), null));
    }

    /**
     *
     * @param book
     * @return updated book data
     * @throws Exception when book not found
     */
    public BookEntity updateBookData(BookDto book) throws Exception {

        if(isbnValidator.validateISBN(book.getIsbn()).equals("")) {
            throw new Exception("Invalid ISBN");
        }

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

    public BookDto getBookById(Long bookId) throws Exception {

        BookEntity getBookDataById = bookRepository.findById(bookId).orElseThrow(() -> new Exception("Book could not found"));
        return bookEntityMapper.toBookDto(getBookDataById);
    }
}
