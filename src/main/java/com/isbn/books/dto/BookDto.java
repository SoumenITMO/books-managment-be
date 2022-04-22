package com.isbn.books.dto;

import com.isbn.books.group.validator.UpdateBook;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookDto {

    @NotNull(groups = UpdateBook.class, message = "Book id can not be null")
    private Long id;

    @NotNull(message = "Title can not be null")
    @NotEmpty(message = "Title can not be empty")
    private String title;

    @NotNull(message = "isbn can not be null")
    @NotEmpty(message = "isbn can not be empty")
    private String isbn;

    @NotNull(message = "author can not be null")
    @NotEmpty(message = "author can not be empty")
    private String author;
}
