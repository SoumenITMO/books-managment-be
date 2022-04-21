package com.isbn.books.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookDto {

    @NotNull(message = "Title can not be null")
    private String title;

    @NotNull(message = "isbn can not be null")
    private String isbn;

    @NotNull(message = "author can not be null")
    private String author;
}
