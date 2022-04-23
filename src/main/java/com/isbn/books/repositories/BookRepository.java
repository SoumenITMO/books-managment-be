package com.isbn.books.repositories;

import com.isbn.books.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("book")
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAuthorLikeOrTitleLikeOrIsbnLike(String author, String title, String isbn);

    List<BookEntity> findAllByFilename(String filename);
}
