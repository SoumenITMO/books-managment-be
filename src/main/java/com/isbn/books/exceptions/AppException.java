package com.isbn.books.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException {

    private int code;

    public AppException(String message){
        super(message);
    }

    public AppException(Exception e){
        super(e);
    }
}
