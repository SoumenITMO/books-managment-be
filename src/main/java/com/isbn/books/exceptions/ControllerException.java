package com.isbn.books.exceptions;

import com.isbn.books.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

    @ResponseBody
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionDto> handleSystemException(AppException appException) {

        return wrapIntoResponseEntity(appException, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleAllExceptions(Exception exception) {
        return wrapIntoResponseEntity(new ExceptionDto(ExceptionCodes.UNMAPPED_EXCEPTION_CODE, exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionDto> wrapIntoResponseEntity (AppException ex, HttpStatus status){

        return ResponseEntity
                .status(status)
                .body(new ExceptionDto(ex));
    }

    private ResponseEntity<ExceptionDto> wrapIntoResponseEntity (ExceptionDto exDto, HttpStatus status){

        return ResponseEntity
                .status(status)
                .body(exDto);
    }
}