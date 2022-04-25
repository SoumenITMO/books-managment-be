package com.isbn.books.exceptions;

import com.isbn.books.dto.ExceptionDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerException {

    @ResponseBody
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionDto> handleSystemException(AppException appException) {

        return wrapIntoResponseEntity(appException, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleAllExceptions(Exception exception, Errors errors) {

        ExceptionDto exceptionDto;

        String invalidInputMessages = errors.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));

        if(errors.hasErrors()) {
            exceptionDto = new ExceptionDto(ExceptionCodes.INVALID_FIELD_DATA, invalidInputMessages);
        } else {
            exceptionDto = new ExceptionDto(ExceptionCodes.UNMAPPED_EXCEPTION_CODE, exception.getMessage());
        }

        return wrapIntoResponseEntity(exceptionDto, HttpStatus.BAD_REQUEST);
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