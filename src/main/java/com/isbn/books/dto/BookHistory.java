package com.isbn.books.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class BookHistory {

    private String fileName;
    private List<BookDto> books;
}
