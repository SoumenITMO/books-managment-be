package com.isbn.books.services;

import com.isbn.books.entities.BookEntity;
import com.isbn.books.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FileDownloadService {

    private List<BookEntity> books;
    private final BookRepository bookRepository;

    /**
     *
     * @param httpServletResponse
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws TransformerException
     */
    public void getFileContent(HttpServletResponse httpServletResponse, String fileName) throws Exception {

        books = bookRepository.findAllByFilename(fileName).size() > 0 ? bookRepository.findAllByFilename(fileName) :
                bookRepository.findAllByFilenameNull();
        if(books.size() == 0) {
            throw new Exception("No data found accoding to this file");
        }
        generateXMLFile(books, httpServletResponse, fileName);
    }

    /**
     *
     * @param httpServletResponse
     * @param fileName
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws IOException
     */
    private void generateXMLFile(List<BookEntity> books, HttpServletResponse httpServletResponse, String fileName)
            throws ParserConfigurationException, TransformerException, IOException {

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(generateXMLContent(document, books));

        httpServletResponse.setContentType("text/csv");
        httpServletResponse.setHeader("Content-disposition", fileName);

        StreamResult streamResult = new StreamResult(httpServletResponse.getOutputStream());
        transformer.transform(domSource, streamResult);
    }

    /**
     *
     * @param document
     * @param books
     * @return
     */
    private Document generateXMLContent(Document document, List<BookEntity> books) {

        Element root = document.createElement("Books");
        document.appendChild(root);

        books.forEach(getBook -> {
            Element book = document.createElement("Book");
            root.appendChild(book);

            Attr attr = document.createAttribute("ISBN");
            attr.setValue(getBook.getIsbn());
            book.setAttributeNode(attr);

            Element title = document.createElement("Title");
            Element author = document.createElement("Author");

            title.appendChild(document.createTextNode(getBook.getTitle()));
            book.appendChild(title);
            author.appendChild(document.createTextNode(getBook.getAuthor()));
            book.appendChild(author);
        });

        return document;
    }
}
