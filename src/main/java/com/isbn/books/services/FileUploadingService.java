package com.isbn.books.services;

import com.isbn.books.entities.BookEntity;
import com.isbn.books.helper.XMLFileReader;
import com.isbn.books.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@AllArgsConstructor
@Service("fileUploader")
public class FileUploadingService {

    private final XMLFileReader xmlFileReader;
    private final BookRepository bookRepository;

    public void saveXMLDataFile(MultipartFile file) throws Exception {
        List<BookEntity> books = xmlFileReader.processXMLFile(file);
        bookRepository.saveAll(books);
    }
}
