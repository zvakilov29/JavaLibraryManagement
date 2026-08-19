package com.zvaki.librarymanagement.controller;

import com.zvaki.librarymanagement.exception.BookNotFoundException;
import com.zvaki.librarymanagement.exception.DuplicateIsbnException;
import com.zvaki.librarymanagement.exception.InvalidBookStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBookNotFound(BookNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateIsbn(DuplicateIsbnException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(InvalidBookStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleInvalidBookState(InvalidBookStateException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException exception) {
        return exception.getMessage();
    }

}
