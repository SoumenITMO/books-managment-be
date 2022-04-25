package com.isbn.books.controllers;

import com.isbn.books.services.FileUploadingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class FileUploaderController {

    private final FileUploadingService fileUploadingService;

    @PostMapping("/uploadFile")
    public ResponseEntity<Void> fileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        fileUploadingService.saveXMLDataFile(file);
        return ResponseEntity.noContent().build();
    }
}
