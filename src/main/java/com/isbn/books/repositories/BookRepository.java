package com.isbn.books.repositories;

import com.isbn.books.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("book")
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query(value = "select * from books where (:author is null or author Like %:author) or (:title is null or title Like %:title) " +
            "or (:isbn is null or isbn Like %:isbn)", nativeQuery = true)
    List<BookEntity> findBooksByProvidedSearchCriteria(@Param("author")String author, @Param("title")String title,
                                                       @Param("isbn")String isbn);

    List<BookEntity> findAllByFilename(String filename);
}
