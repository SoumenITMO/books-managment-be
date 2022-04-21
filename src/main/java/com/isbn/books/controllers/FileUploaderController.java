package com.isbn.books.controllers;

import com.isbn.books.helper.XMLFileReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class FileUploaderController {

    private final XMLFileReader fileUploadingService;

    @ResponseBody
    @PostMapping("/uploadFile")
    public void fileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        fileUploadingService.processXMLFile(file);
    }
}
