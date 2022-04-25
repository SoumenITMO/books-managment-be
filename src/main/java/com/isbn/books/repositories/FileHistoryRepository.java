package com.isbn.books.repositories;

import com.isbn.books.entities.FileHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileHistoryRepository extends JpaRepository<FileHistory, Long> { }
