package com.isbn.books.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class XMLFileReader {

    public void processXMLFile(MultipartFile file) throws Exception {
        if(!file.getContentType().equals("application/xml")) {
            throw new Exception("Invalid File Format");
        }

        InputStream fileInputStream = file.getInputStream();
        BufferedReader rawXMLData = new BufferedReader(new InputStreamReader(fileInputStream));

        rawXMLData.lines().forEach(System.out::println);
    }
}
