package com.isbn.books.helper;

import com.isbn.books.entities.BookEntity;
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
public class XMLFileReader {

    private final List<BookEntity> books = new ArrayList<>();

    /**
     *
     * @param file uploaded file to check validity
     * @throws Exception when XML has wrong format or nodes
     */
    public List<BookEntity> processXMLFile(MultipartFile file) throws Exception {
        if (!file.getContentType().equals("application/xml")) {
            throw new Exception("Invalid File Format");
        }

        int index = 0;

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
                String isbnData = node.getAttributes().getNamedItem("ISBN").getTextContent().replace("-", "");

                if(validateISBN(isbnData).equals(isbnData)) {
                    books.add(new BookEntity(isbnData, author, title));
                } else {
                    throw new Exception("Invalid ISBN contain in data file");
                }
            }
            index++;
        }

        return books;
    }

    /**
     *
     * @param isbn value from data file
     * @return if ISBN is valid it will return valid ISBN, return empty in case invalid ISBN
     * @throws Exception when it contains invalid ISBN
     */
    private String validateISBN(String isbn) throws Exception {
        if((isbn.length() == 10) || (isbn.length() == 13)) {
            isbn = isbnValidator(isbn.toCharArray()) ? isbn : "";
        } else {
            throw new Exception("Invalid ISBN contain in data file");
        }
        return isbn;
    }

    /**
     *
     * @param isbnChars as iban chars
     * @return boolean if ISBN is valid or not
     */
    private boolean isbnValidator(char[] isbnChars) {
        int index = 0;
        int reminder;
        int isbnSegmentSum = 0;
        int isbnNumericValue;

        while(isbnChars.length > index) {
            reminder = (index % 2) == 0 ? 1 : 3;
            isbnNumericValue = Character.getNumericValue(isbnChars[index]);
            isbnSegmentSum += isbnNumericValue * reminder;
            index++;
        }
        return isbnChars.length == 13 ? isbnSegmentSum % 10 == 0 : isbnSegmentSum % 11 == 0;
    }
}
