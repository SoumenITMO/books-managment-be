package com.isbn.books.dto;

import com.isbn.books.exceptions.AppException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

    private int code;
    private String message;

    public ExceptionDto(AppException e) {

        setCode(e.getCode());
        setMessage(e.getMessage());
    }
}
