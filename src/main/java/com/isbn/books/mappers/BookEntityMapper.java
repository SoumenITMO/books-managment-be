package com.isbn.books.mappers;

import com.isbn.books.dto.BookDto;
import com.isbn.books.entities.BookEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookEntityMapper {

    List<BookDto> toBookDto(List<BookEntity> books);
}
