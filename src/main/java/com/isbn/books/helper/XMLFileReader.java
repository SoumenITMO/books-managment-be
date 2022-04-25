package com.isbn.books.helper;

import com.isbn.books.entities.BookEntity;
import com.isbn.books.exceptions.AppException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class XMLFileReader {

    private final IsbnValidator isbnValidator;

    /**
     *
     * @param file uploaded file to check validity
     * @throws Exception when XML has wrong format or nodes
     */
    public List<BookEntity> processXMLFile(MultipartFile file) throws Exception {

        if (!file.getContentType().equals("application/xml")) {
            throw new Exception("Invalid File");
        }

        int index = 0;
        List<BookEntity> books = new ArrayList<>();
        String regex = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file.getInputStream());

        NodeList list = document.getElementsByTagName("Book");

        while(list.getLength() > index) {

            Node node = list.item(index);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                String title = element.getElementsByTagName("Title").item(0).getTextContent();
                String author = element.getElementsByTagName("Author").item(0).getTextContent();
                String isbnWithDelimeter = node.getAttributes().getNamedItem("ISBN").getTextContent();
                String isbn = isbnWithDelimeter.replace("-", "");

                if(!isbnWithDelimeter.matches(regex)) {
                    throw new AppException("File contain invalid ISBN format");
                }

                if(isbnValidator.validateISBN(isbn).equals(isbn)) {
                    books.add(new BookEntity(isbnWithDelimeter, author, title));
                } else {
                    throw new Exception("Invalid ISBN contain in data file");
                }
            }
            index++;
        }

        return books;
    }
}
