package com.isbn.books.controllers;

import com.isbn.books.services.FileDownloadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
@RequestMapping("/book/data")
public class FileDownloaderController {

    private final FileDownloadService fileDownloadService;

    @GetMapping("download/{filename}")
    public ResponseEntity<Void> fileDownload(HttpServletResponse response, @PathVariable("filename") String filename)
            throws Exception {

        fileDownloadService.getFileContent(response, filename);
        return ResponseEntity.noContent().build();
    }
}
